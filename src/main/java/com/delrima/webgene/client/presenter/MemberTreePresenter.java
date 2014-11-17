package com.delrima.webgene.client.presenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.delrima.webgene.arch.client.utils.GWTFrameworkLogger;
import com.delrima.webgene.client.action.MemberLookupAction;
import com.delrima.webgene.client.action.RetrieveMemberTreeAction;
import com.delrima.webgene.client.action.RetrieveSingleMemberAction;
import com.delrima.webgene.client.common.Command;
import com.delrima.webgene.client.common.HierarchicalDataCreator;
import com.delrima.webgene.client.dto.Member;
import com.delrima.webgene.client.event.RetrieveMemberLookupResultEvent;
import com.delrima.webgene.client.event.UpdateActionCompleteEvent;
import com.delrima.webgene.client.model.HasAncestors;
import com.delrima.webgene.client.model.HasDescendants;
import com.delrima.webgene.client.model.IsTreeMember;
import com.delrima.webgene.client.model.MemberWithImmediateRelations;
import com.delrima.webgene.client.model.TreeOperationModel;
import com.delrima.webgene.client.result.MemberLookupResult;
import com.delrima.webgene.client.result.RetrieveMemberTreeResult;
import com.delrima.webgene.client.rpc.ActionHandlerServiceAsync;
import com.delrima.webgene.client.view.MemberEditView;
import com.delrima.webgene.client.view.MemberTreeView;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;

/**
 * @author Bhavesh
 */
public class MemberTreePresenter implements MemberTreeView.Presenter {

	private static Logger sLogger = GWTFrameworkLogger.getCustomLogger(MemberTreePresenter.class);

	private final TreeOperationModel treeOperationModel = new TreeOperationModel();

	/**
	 * Family Tree Display
	 */
	private final MemberTreeView view;

	/**
	 * Execute RPCs via the dispatcher
	 */
	private final ActionHandlerServiceAsync dispatchAsync;

	private final HandlerManager eventBus;

	private final MemberEditView.Presenter memberEditPresenter;

	/**
	 * @param dispatchAsync
	 * @param display
	 * @param memberEditDisplay
	 * @param eventBus
	 */
	@Inject
	public MemberTreePresenter(ActionHandlerServiceAsync dispatchAsync, HandlerManager eventBus, MemberTreeView display, MemberEditPresenter memberEditPresenter) {
		super();
		this.dispatchAsync = dispatchAsync;
		this.eventBus = eventBus;
		this.view = display;
		this.memberEditPresenter = memberEditPresenter;
		display.setPresenter(this);
		retrieveMemberTreeData();
	}

	/**
	 * @param container
	 */
	public void go(HasWidgets container) {
		view.addToContainer(container);
		bind();
	}

	private void bind() {
		eventBus.addHandler(UpdateActionCompleteEvent.getType(), this);
		eventBus.addHandler(RetrieveMemberLookupResultEvent.getType(), this);
	}

	public void onDisplayModeSelected() {
		this.treeOperationModel.setMode(view.getSelectedDisplayMode());
		retrieveMemberTreeInformation();
	}

	public void onViewTypeSelected() {
		this.treeOperationModel.setViewType(view.getSelectedViewType());
		retrieveMemberTreeInformation();
	}

	@Override
	public void onRootMemberSuggestionSelected(IsTreeMember member) {
		treeOperationModel.setRootMember(member);
		treeOperationModel.setMode(view.getSelectedDisplayMode());
		treeOperationModel.setViewType(view.getSelectedViewType());
		retrieveMemberTreeInformation();
	}

	@Override
	public void onUpdateActionComplete(UpdateActionCompleteEvent event) {
		if (event.getResult() != null && event.getResult().getEventName() == UpdateActionCompleteEvent.NAME.ADD_MEMBER) {
			onRootMemberSuggestionSelected(event.getResult().getMember().getMember());
		} else if (event.getResult().getEventName() == UpdateActionCompleteEvent.NAME.DELETE_MEMBER) {
			this.view.clearTree();
		} else {
			MemberWithImmediateRelations member = event.getResult().getMember();
			retrieveMemberTreeData(getIds(member.getChildren(), member.getMember(), member.getMother(), member.getFather()));
		}
	}

	private Long[] getIds(Set<Member> children, Member... members) {
		List<Long> ids = new ArrayList<Long>();
		for (Member m : members) {
			if (m != null) {
				ids.add(m.getId());
			}
		}
		for (Member m : children) {
			if (m != null) {
				ids.add(m.getId());
			}
		}
		Long[] idArray = new Long[ids.size()];
		ids.toArray(idArray);
		return idArray;
	}

	/**
	 * Re-create the family tree
	 * 
	 * @param container
	 *            -
	 */
	void retrieveMemberTreeInformation() {
		retrieveMemberTreeData((Long[]) null);
	}

	void retrieveMemberTreeData(final Long... updatedIds) {
		view.clearErrorMessage();
		final boolean initialLoad = this.treeOperationModel.getDisplayModel() == null;
		if (!initialLoad && (updatedIds == null || updatedIds.length == 0)) {
			updateTreeDisplay(treeOperationModel.getDisplayModel(), RetrieveMemberTreeResult.EMPTY_RESULT());
			return;
		}
		dispatchAsync.execute(new RetrieveMemberTreeAction(updatedIds), new AsyncCallback<RetrieveMemberTreeResult>() {

			public void onFailure(Throwable arg0) {
				view.displayErrorMessage("An unexpected error occured while loading the tree.");
				sLogger.log(Level.SEVERE, "An exception has occured when loading family tree.", arg0);
			}

			public void onSuccess(RetrieveMemberTreeResult result) {
				// if no results came, then set the looked-up member as the root
				// member
				try {
					if (initialLoad) {
						treeOperationModel.setDisplayModel(result);
					} else {
						updateTreeDisplay(treeOperationModel.getDisplayModel(), result);
					}
				} catch (Exception e) {
					this.onFailure(e);
					GWT.log("Exception Occurred: ", e);
				}
			}
		});
	}

	private void updateTreeDisplay(RetrieveMemberTreeResult existingResult, RetrieveMemberTreeResult newResult) {
		final HierarchicalDataCreator dataCreator = new HierarchicalDataCreator(existingResult.getMembers(), newResult.getMembers());
		treeOperationModel.getDisplayModel().setMembers(new HashSet<IsTreeMember>(dataCreator.getMembers().values()));
		switch (treeOperationModel.getMode()) {
		case ANCESTOR: {
			HasAncestors resultTree = dataCreator.retrieveAncestor(treeOperationModel.getRootMember().getId());
			sLogger.fine("Result retrieved successfully. Member = " + resultTree.getMember());
			view.renderFamilyTree(resultTree, treeOperationModel.getViewType());
			break;
		}
		case DESCENDANT:
			HasDescendants resultTree = dataCreator.retrieveDescendants(treeOperationModel.getRootMember().getId());
			sLogger.fine("Result retrieved successfully. Member = " + resultTree.getMember());
			view.renderFamilyTree(resultTree, treeOperationModel.getViewType());
			break;
		}
	}

	@Override
	public void onTreeMemberSelected(IsTreeMember member) {
		treeOperationModel.setSelectedMember(member);
	}

	@Override
	public void onAddChild() {
		retrieveMemberAndDoCommand(new Command<MemberWithImmediateRelations>() {

			@Override
			public void execute(MemberWithImmediateRelations response) {
				memberEditPresenter.showAddChild(response);
				view.showMemberEditLayer();
			}
		});

	}

	@Override
	public void onEditMember() {
		retrieveMemberAndDoCommand(new Command<MemberWithImmediateRelations>() {

			@Override
			public void execute(MemberWithImmediateRelations response) {
				memberEditPresenter.showEditMember(response);
				view.showMemberEditLayer();
			}
		});
	}

	@Override
	public void onAddMember() {
		memberEditPresenter.showAddMember();
		view.showMemberEditLayer();
	}

	@Override
	public void onAddParent() {
		retrieveMemberAndDoCommand(new Command<MemberWithImmediateRelations>() {

			@Override
			public void execute(MemberWithImmediateRelations response) {
				memberEditPresenter.showAddParent(response);
				view.showMemberEditLayer();
			}
		});

	}

	@Override
	public void onSelectMember() {
		retrieveMemberAndDoCommand(new Command<MemberWithImmediateRelations>() {

			@Override
			public void execute(MemberWithImmediateRelations response) {
				view.updateRootMember(response.getMember().getFirstname() + " " + response.getMember().getLastname());
				onRootMemberSuggestionSelected(response.getMember());
			}
		});

	}

	private void retrieveMemberAndDoCommand(final Command<MemberWithImmediateRelations> command) {
		dispatchAsync.execute(new RetrieveSingleMemberAction(treeOperationModel.getSelectedMember().getId()), new AsyncCallback<MemberWithImmediateRelations>() {

			@Override
			public void onFailure(Throwable arg0) {
				sLogger.log(Level.SEVERE, "Failed @ retrieveMemberAndDoCommand()");
				view.displayErrorMessage("An unexpected error occured while looking up the member.");
			}

			@Override
			public void onSuccess(MemberWithImmediateRelations result) {
				view.clearErrorMessage();
				command.execute(result);
			}
		});
	}

	@Override
	public void onDeleteMember() {
		view.showMemberEditLayer();
		memberEditPresenter.deleteMember(treeOperationModel.getSelectedMember());
	}

	@Override
	public void onMemberLookupResultRetrieveRequest(final RetrieveMemberLookupResultEvent event) {
		view.clearErrorMessage();
		if (event.getQuery().length() >= LOOKUP_MINIMUM_CHAR_COUNT) {
			if (this.treeOperationModel.getDisplayModel() != null) {
				MemberLookupResult result = new MemberLookupResult();
				final String query = event.getQuery().toLowerCase();
				for (IsTreeMember m : this.treeOperationModel.getDisplayModel().getMembers()) {
					if (m.getFirstname().toLowerCase().contains(query) || m.getLastname().toLowerCase().contains(query)) {
						result.getMembers().add(m);
					}
				}
				this.doOnMemberLookupResultRetrieveRequest(result, event);
			} else {
				dispatchAsync.execute(new MemberLookupAction(event.getQuery()), new AsyncCallback<MemberLookupResult>() {

					@Override
					public void onFailure(Throwable arg0) {
						sLogger.log(Level.SEVERE, "Failed @ onMemberLookupResultRetrieveRequest()");
						view.displayErrorMessage("An unexpected error occured while looking up the member.");
					}

					@Override
					public void onSuccess(MemberLookupResult result) {
						doOnMemberLookupResultRetrieveRequest(result, event);
					}
				});
			}
		}
	}

	public void doOnMemberLookupResultRetrieveRequest(MemberLookupResult result, final RetrieveMemberLookupResultEvent event) {
		event.getCommand().execute(result);
	}
}

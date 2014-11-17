package com.delrima.webgene.client.presenter;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.delrima.webgene.arch.client.utils.GWTFrameworkLogger;
import com.delrima.webgene.client.action.MemberLookupAction;
import com.delrima.webgene.client.action.UpdateMemberAction;
import com.delrima.webgene.client.common.WebGeneUtils;
import com.delrima.webgene.client.dto.Member;
import com.delrima.webgene.client.event.RetrieveMemberLookupResultEvent;
import com.delrima.webgene.client.event.UpdateActionCompleteEvent;
import com.delrima.webgene.client.event.UpdateActionCompleteEvent.NAME;
import com.delrima.webgene.client.model.IsTreeMember;
import com.delrima.webgene.client.model.MemberEditOperationModel;
import com.delrima.webgene.client.model.MemberWithImmediateRelations;
import com.delrima.webgene.client.result.MemberLookupResult;
import com.delrima.webgene.client.result.UpdateMemberResult;
import com.delrima.webgene.client.rpc.ActionHandlerServiceAsync;
import com.delrima.webgene.client.view.MemberEditView;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

/**
 * @author Bhavesh
 */
public class MemberEditPresenter implements MemberEditView.Presenter {

	private static Logger sLogger = GWTFrameworkLogger.getCustomLogger(MemberEditPresenter.class);

	private final ActionHandlerServiceAsync dispatchAsync;
	private final HandlerManager eventBus;

	private final MemberEditView view;
	private final MemberEditOperationModel memberEditOperationModel;

	@Inject
	public MemberEditPresenter(MemberEditView display, ActionHandlerServiceAsync dispatchAsync, HandlerManager eventBus) {
		this.view = display;
		this.dispatchAsync = dispatchAsync;
		this.eventBus = eventBus;
		this.memberEditOperationModel = new MemberEditOperationModel();
		this.view.setPresenter(this);
		this.eventBus.addHandler(RetrieveMemberLookupResultEvent.getType(), this);
	}

	/**
	 * @param action
	 *            -
	 */
	public void persistMemberChange() {
		// Get last clicked member
		Member lastClickedMember = this.memberEditOperationModel.getSelectedMember() == null ? null : this.memberEditOperationModel.getSelectedMember().getMember();
		// Get edited member
		MemberWithImmediateRelations memberEditDisplayModel = view.extractModelFromDisplay();
		// Get last event
		UpdateActionCompleteEvent.NAME memberEditEventName = this.memberEditOperationModel.getSelectedMemberChangeEventName();
		// Create Action
		final UpdateMemberAction action = new UpdateMemberAction(lastClickedMember, memberEditDisplayModel.getMember(), memberEditEventName);
		// Execute Action
		dispatchAsync.execute(action, new AsyncCallback<UpdateMemberResult>() {

			public void onFailure(Throwable arg0) {
				sLogger.log(Level.SEVERE, "MemberChange RPC execution failed.", arg0);
				view.displayErrorMessage("An error occurred while processing your '" + action.getEventName().getDescription() + "' request.");
			}

			public void onSuccess(UpdateMemberResult result) {
				sLogger.fine("MemberChange RPC executed successfully.");
				// refresh family tree after update
				eventBus.fireEvent(new UpdateActionCompleteEvent(result));
			}
		});
	}

	@Override
	public void showAddChild(MemberWithImmediateRelations parentMember) {
		this.memberEditOperationModel.setSelectedMemberChangeEventName(NAME.ADD_CHILD);
		memberEditOperationModel.setSelectedMember(parentMember);
		view.clearErrorMessage();
		view.clearMemberEditData();

		boolean isParentMale = WebGeneUtils.isMemberMale(parentMember.getMember());
		Member father, mother;
		father = (isParentMale ? parentMember.getMember() : parentMember.getSpouse());
		mother = (isParentMale ? parentMember.getSpouse() : parentMember.getMember());
		Member child = new Member();
		child.setLastname(parentMember.getMember().getLastname());
		view.updateDisplayWithModel(new MemberWithImmediateRelations(child, father, mother, null, null));
	}

	@Override
	public void showAddMember() {
		view.clearErrorMessage();
		memberEditOperationModel.setSelectedMember(null);
		this.memberEditOperationModel.setSelectedMemberChangeEventName(NAME.ADD_MEMBER);
		view.clearMemberEditData();
	}

	@Override
	public void showAddParent(MemberWithImmediateRelations childMember) {
		view.clearErrorMessage();
		memberEditOperationModel.setSelectedMember(childMember);
		this.memberEditOperationModel.setSelectedMemberChangeEventName(NAME.ADD_PARENT);
		view.clearMemberEditData();
		Member parent = new Member();
		parent.setLastname(childMember.getMember().getLastname());
		view.updateDisplayWithModel(new MemberWithImmediateRelations(parent, null, null, null, null));
	}

	@Override
	public void showEditMember(MemberWithImmediateRelations selectedMember) {
		view.clearErrorMessage();
		memberEditOperationModel.setSelectedMember(selectedMember);
		this.memberEditOperationModel.setSelectedMemberChangeEventName(NAME.EDIT_MEMBER);
		view.updateDisplayWithModel(selectedMember);
	}

	@Override
	public void deleteMember(IsTreeMember selectedMember) {
		view.clearErrorMessage();
		Member deleteMember = new Member();
		deleteMember.setId(selectedMember.getId());
		memberEditOperationModel.setSelectedMember(new MemberWithImmediateRelations(deleteMember, null, null, null, null));
		this.memberEditOperationModel.setSelectedMemberChangeEventName(NAME.DELETE_MEMBER);
		view.updateDisplayWithModel(new MemberWithImmediateRelations(deleteMember, null, null, null, null));
		persistMemberChange();
	}

	@Override
	public void onMemberLookupResultRetrieveRequest(final RetrieveMemberLookupResultEvent event) {
		if (event.getQuery().length() > LOOKUP_MINIMUM_CHAR_COUNT) {
			dispatchAsync.execute(new MemberLookupAction(event.getQuery()), new AsyncCallback<MemberLookupResult>() {

				@Override
				public void onFailure(Throwable arg0) {
				}

				@Override
				public void onSuccess(MemberLookupResult result) {
					event.getCommand().execute(result);
				}
			});
		}
	}

}

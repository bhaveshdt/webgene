package com.delrima.webgene.server.services;

import java.util.Set;

import javax.inject.Inject;

import org.springframework.dao.DataAccessException;

import com.delrima.webgene.client.action.RetrieveSingleMemberAction;
import com.delrima.webgene.client.action.SingleActionHandler;
import com.delrima.webgene.client.action.UpdateMemberAction;
import com.delrima.webgene.client.common.ActionException;
import com.delrima.webgene.client.common.WebGeneUtils;
import com.delrima.webgene.client.dao.MemberTreeDataProvider;
import com.delrima.webgene.client.dto.Member;
import com.delrima.webgene.client.model.MemberWithImmediateRelations;
import com.delrima.webgene.client.result.UpdateMemberResult;

/**
 * @author Bhavesh
 */
public class UpdateMemberActionHandler extends AbstractWebgeneActionHandler implements SingleActionHandler<UpdateMemberAction, UpdateMemberResult> {

	private final RetrieveSingleMemberActionHandler retrieveSingleMemberActionHandler;

	/**
	 * @param actionHandlerRegistry
	 */
	@Inject
	public UpdateMemberActionHandler(MemberTreeDataProvider dataProvider, RetrieveSingleMemberActionHandler retrieveSingleMemberActionHandler) {
		super(dataProvider);
		this.retrieveSingleMemberActionHandler = retrieveSingleMemberActionHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.delrima.webgene.client.action.ActionHandler#execute(com.delrima.webgene.client.common.Action)
	 */
	public UpdateMemberResult execute(UpdateMemberAction action) throws ActionException {
		System.out.println("UpdateMemberHandler.execute()");

		UpdateMemberResult result = null;
		try {
			switch (action.getEventName()) {
			case ADD_MEMBER:
			case ADD_CHILD:
			case EDIT_MEMBER:
			case ADD_PARENT:
			case DELETE_MEMBER: {
				result = updateMember(action);
				break;
			}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return result;
	}

	private UpdateMemberResult updateMember(UpdateMemberAction action) throws ActionException {
		Member member = action.getMember();

		// save updates to database
		switch (action.getEventName()) {
		case ADD_CHILD:
		case ADD_MEMBER:
			this.getDataProvider().addMember(member);
			break;
		case EDIT_MEMBER:
			this.getDataProvider().updateMember(member);
			// update children if spouse has been updated
			updateChildrenForNewSpouse(action.getRelativeMember(), member);
			break;
		case ADD_PARENT:
			// add member to database
			Member parent = this.getDataProvider().addMember(member);
			// update child with mother and father
			Member child = this.getDataProvider().retrieveMemberById(action.getRelativeMember().getId());
			boolean isParentMale = WebGeneUtils.isMemberMale(parent);
			if (isParentMale) {
				child.setFatherid(parent.getId());
				child.setMotherid(parent.getSpouseid());
			} else {
				child.setMotherid(parent.getId());
				child.setFatherid(parent.getSpouseid());
			}
			this.getDataProvider().updateMember(child);
			break;
		case DELETE_MEMBER:
			this.getDataProvider().deleteMember(member.getId());
			break;
		}

		// retrieve from database
		MemberWithImmediateRelations updatedMember = this.retrieveSingleMemberActionHandler.execute(new RetrieveSingleMemberAction(member.getId()));

		// return result
		return new UpdateMemberResult(updatedMember, action.getEventName());
	}

	private void updateChildrenForNewSpouse(Member beforeUpdateMember, Member afterUpdateMember) {
		// ignore if unknown member, (id will be null for unknown spouse)
		if (afterUpdateMember.getSpouseid() == null) {
			return;
		}

		Member beforeUpdateSpouseMember = this.getDataProvider().retrieveMemberById(beforeUpdateMember.getSpouseid());
		Member afterUpdateSpouseMember = this.getDataProvider().retrieveMemberById(afterUpdateMember.getSpouseid());

		// if spouse has changed, update the member's spouse's children
		if (beforeUpdateSpouseMember == null || beforeUpdateSpouseMember.getId() != afterUpdateSpouseMember.getId()) {
			// get database version of the member
			Member updatedMember = this.getDataProvider().retrieveMemberById(beforeUpdateMember.getId());
			boolean isUpdatedMemberMale = WebGeneUtils.isMemberMale(updatedMember);

			// get children of the member updated
			Set<Member> childrenOfUpdatedMember = this.getDataProvider().retrieveChildren(updatedMember.getId());

			// update the children to reflect the parent change
			if (childrenOfUpdatedMember != null) {
				if (isUpdatedMemberMale) {
					for (final Member child : childrenOfUpdatedMember) {
						child.setMotherid(afterUpdateSpouseMember.getId());
						this.getDataProvider().updateMember(child);
					}
				} else {
					for (final Member child : childrenOfUpdatedMember) {
						child.setFatherid(afterUpdateSpouseMember.getId());
						this.getDataProvider().updateMember(child);
					}
				}
			}
		}
	}

	public Class<UpdateMemberAction> getActionType() {
		return UpdateMemberAction.class;
	}

}
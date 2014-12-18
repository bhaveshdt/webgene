package com.delrima.webgene.server.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

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
@Component
public class UpdateMemberActionHandler extends AbstractWebgeneActionHandler implements SingleActionHandler<UpdateMemberAction, UpdateMemberResult> {

	private final RetrieveSingleMemberActionHandler retrieveSingleMemberActionHandler;

	/**
	 * @param actionHandlerRegistry
	 */
	@Autowired
	public UpdateMemberActionHandler(MemberTreeDataProvider dataProvider, RetrieveSingleMemberActionHandler retrieveSingleMemberActionHandler) {
		super(dataProvider);
		this.retrieveSingleMemberActionHandler = retrieveSingleMemberActionHandler;
	}

	public Member addParent(long childId, Member member) {
		final Member parent = getDataProvider().addMember(member);
		// update child with mother and father
		final Member child = getDataProvider().retrieveMemberById(childId);
		final boolean isParentMale = WebGeneUtils.isMemberMale(parent);
		if (isParentMale) {
			child.setFatherid(parent.getId());
			child.setMotherid(parent.getSpouseid());
		} else {
			child.setMotherid(parent.getId());
			child.setFatherid(parent.getSpouseid());
		}
		getDataProvider().updateMember(child);
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.delrima.webgene.client.action.ActionHandler#execute(com.delrima.webgene.client.common.Action)
	 */
	@Override
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
		} catch (final DataAccessException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Class<UpdateMemberAction> getActionType() {
		return UpdateMemberAction.class;
	}

	public void updateMember(Member member) {
		final Member beforeUpdateMember = getDataProvider().retrieveMemberById(member.getId());
		getDataProvider().updateMember(member);
		final Member afterUpdateMember = member;
		// ignore if unknown member, (id will be null for unknown spouse)
		if (afterUpdateMember.getSpouseid() == null) {
			return;
		}

		final Member beforeUpdateSpouseMember = getDataProvider().retrieveMemberById(beforeUpdateMember.getSpouseid());
		final Member afterUpdateSpouseMember = getDataProvider().retrieveMemberById(afterUpdateMember.getSpouseid());

		// if spouse has changed, update the member's spouse's children
		if (beforeUpdateSpouseMember == null || beforeUpdateSpouseMember.getId() != afterUpdateSpouseMember.getId()) {
			// get database version of the member
			final Member updatedMember = getDataProvider().retrieveMemberById(beforeUpdateMember.getId());
			final boolean isUpdatedMemberMale = WebGeneUtils.isMemberMale(updatedMember);

			// get children of the member updated
			final Set<Member> childrenOfUpdatedMember = getDataProvider().retrieveChildren(updatedMember.getId());

			// update the children to reflect the parent change
			if (childrenOfUpdatedMember != null) {
				if (isUpdatedMemberMale) {
					for (final Member child : childrenOfUpdatedMember) {
						child.setMotherid(afterUpdateSpouseMember.getId());
						getDataProvider().updateMember(child);
					}
				} else {
					for (final Member child : childrenOfUpdatedMember) {
						child.setFatherid(afterUpdateSpouseMember.getId());
						getDataProvider().updateMember(child);
					}
				}
			}
		}
	}

	private UpdateMemberResult updateMember(UpdateMemberAction action) throws ActionException {
		Member member = action.getMember();

		// save updates to database
		switch (action.getEventName()) {
		case ADD_CHILD:
		case ADD_MEMBER:
			member = getDataProvider().addMember(member);
			break;
		case EDIT_MEMBER:
			updateMember(member);
			break;
		case ADD_PARENT:
			// add member to database
			member = addParent(action.getRelativeMember().getId(), member);
			break;
		case DELETE_MEMBER:
			getDataProvider().deleteMember(member.getId());
			break;
		}

		// retrieve from database
		final MemberWithImmediateRelations updatedMember = retrieveSingleMemberActionHandler.execute(new RetrieveSingleMemberAction(member.getId()));

		// return result
		return new UpdateMemberResult(updatedMember, action.getEventName());
	}

}
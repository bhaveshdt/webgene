package com.delrima.webgene.server.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.delrima.webgene.client.action.RetrieveSingleMemberAction;
import com.delrima.webgene.client.action.SingleActionHandler;
import com.delrima.webgene.client.common.ActionException;
import com.delrima.webgene.client.dao.MemberTreeDataProvider;
import com.delrima.webgene.client.dto.Member;
import com.delrima.webgene.client.model.MemberWithImmediateRelations;

/**
 * @author Bhavesh
 *
 */
public class RetrieveSingleMemberActionHandler extends AbstractWebgeneActionHandler implements SingleActionHandler<RetrieveSingleMemberAction, MemberWithImmediateRelations> {

	@Autowired
	public RetrieveSingleMemberActionHandler(MemberTreeDataProvider dataProvider) {
		super(dataProvider);
	}

	@Override
	public MemberWithImmediateRelations execute(RetrieveSingleMemberAction action) throws ActionException {

		MemberWithImmediateRelations result = null;
		try {
			final Member member = getDataProvider().retrieveMemberById(action.getMemberId());
			final Member father = getDataProvider().retrieveMemberById(member.getFatherid());
			final Member mother = getDataProvider().retrieveMemberById(member.getMotherid());
			final Member spouse = getDataProvider().retrieveMemberById(member.getSpouseid());
			final Set<Member> children = getDataProvider().retrieveChildren(action.getMemberId());
			result = new MemberWithImmediateRelations(member, father, mother, spouse, children);
		} catch (final DataAccessException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Class<RetrieveSingleMemberAction> getActionType() {
		return RetrieveSingleMemberAction.class;
	}

}
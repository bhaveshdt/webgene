package com.delrima.webgene.server.services;

import java.util.Set;

import javax.inject.Inject;

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

	@Inject
	public RetrieveSingleMemberActionHandler(MemberTreeDataProvider dataProvider) {
		super(dataProvider);
	}

	public MemberWithImmediateRelations execute(RetrieveSingleMemberAction action) throws ActionException {

		MemberWithImmediateRelations result = null;
		try {
			Member member = getDataProvider().retrieveMemberById(action.getMemberId());
			Member father = getDataProvider().retrieveMemberById(member.getFatherid());
			Member mother = getDataProvider().retrieveMemberById(member.getMotherid());
			Member spouse = getDataProvider().retrieveMemberById(member.getSpouseid());
			Set<Member> children = this.getDataProvider().retrieveChildren(action.getMemberId());
			result = new MemberWithImmediateRelations(member, father, mother, spouse, children);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return result;
	}

	public Class<RetrieveSingleMemberAction> getActionType() {
		return RetrieveSingleMemberAction.class;
	}

}
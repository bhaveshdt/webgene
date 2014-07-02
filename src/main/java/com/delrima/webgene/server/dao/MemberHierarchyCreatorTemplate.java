package com.delrima.webgene.server.dao;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import com.delrima.webgene.client.dao.MemberRelationDAO;
import com.delrima.webgene.client.model.IsTreeMember;

public final class MemberHierarchyCreatorTemplate {

	private final MemberRelationDAO<? extends IsTreeMember> dao;

	@Inject
	public MemberHierarchyCreatorTemplate(final MemberRelationDAO<? extends IsTreeMember> dao) {
		this.dao = dao;
	}

	public Set<IsTreeMember> retrieveDescendants(Long memberId) {
		Set<IsTreeMember> members = new HashSet<IsTreeMember>();
		doRetrieveDescendants(memberId, members);
		return members;
	}

	private void doRetrieveDescendants(Long memberId, Set<IsTreeMember> members) {
		if (memberId == null) {
			return;
		}

		members.add(dao.retrieveMemberById(memberId));
		Set<? extends IsTreeMember> children = dao.retrieveChildren(memberId);

		for (IsTreeMember m : children) {
			doRetrieveDescendants(m.getId(), members);
		}
	}

	public Set<IsTreeMember> retrieveAncestor(Long memberId) {
		Set<IsTreeMember> members = new HashSet<IsTreeMember>();
		doRetrieveAncestor(memberId, members);
		return members;
	}

	private void doRetrieveAncestor(Long memberId, Set<IsTreeMember> members) {
		if (memberId == null) {
			return;
		}

		IsTreeMember member = dao.retrieveMemberById(memberId);
		members.add(member);

		doRetrieveAncestor(member.getFatherid(), members);
		doRetrieveAncestor(member.getMotherid(), members);

	}
}

package com.delrima.webgene.client.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import com.delrima.webgene.client.model.HasAncestors;
import com.delrima.webgene.client.model.HasDescendants;
import com.delrima.webgene.client.model.IsTreeMember;

public final class HierarchicalDataCreator {

	private final Map<Long, IsTreeMember> members = new HashMap<Long, IsTreeMember>();
	private final Map<Long, Set<IsTreeMember>> children = new HashMap<Long, Set<IsTreeMember>>();

	@Inject
	public HierarchicalDataCreator(final Set<IsTreeMember> inputMembers, final Set<IsTreeMember> updatedMembers) {
		updateMembers(inputMembers);
		updateMembers(updatedMembers);
		updateChildren();
	}

	void updateMembers(Set<IsTreeMember> inputMembers) {
		for (final IsTreeMember m : inputMembers) {
			this.members.put(m.getId(), m);
			if (m.getFatherid() != null) {
				children.put(m.getFatherid(), new HashSet<IsTreeMember>());
			}
			if (m.getMotherid() != null) {
				children.put(m.getMotherid(), new HashSet<IsTreeMember>());
			}

		}

	}

	void updateChildren() {
		for (final IsTreeMember m : members.values()) {
			if (m.getFatherid() != null) {
				children.get(m.getFatherid()).add(m);
			}
			if (m.getMotherid() != null) {
				children.get(m.getMotherid()).add(m);
			}
		}
	}

	@SuppressWarnings("serial")
	public HasAncestors retrieveAncestor(Long memberId) {
		if (memberId == null) {
			return null;
		}
		final IsTreeMember member = members.get(memberId);
		final HasAncestors father = retrieveAncestor(member.getFatherid());
		final HasAncestors mother = retrieveAncestor(member.getMotherid());
		return new HasAncestors() {

			@Override
			public IsTreeMember getMember() {
				return member;
			}

			@Override
			public HasAncestors getMother() {
				return mother;
			}

			@Override
			public HasAncestors getFather() {
				return father;
			}
		};
	}

	@SuppressWarnings("serial")
	public HasDescendants retrieveDescendants(Long memberId) {
		if (memberId == null) {
			return null;
		}
		final IsTreeMember member = members.get(memberId);
		Set<IsTreeMember> children = this.children.get(memberId);

		final List<HasDescendants> descendants = new ArrayList<HasDescendants>();
		if (children != null) {
			for (IsTreeMember m : children) {
				HasDescendants d = retrieveDescendants(m.getId());
				descendants.add(d);
			}
		}
		return new HasDescendants() {

			@Override
			public IsTreeMember getMember() {
				return member;
			}

			@Override
			public List<HasDescendants> getChildren() {
				return descendants;
			}
		};
	}

	public Map<Long, IsTreeMember> getMembers() {
		return members;
	}
}

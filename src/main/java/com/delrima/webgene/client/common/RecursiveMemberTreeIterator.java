package com.delrima.webgene.client.common;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.delrima.webgene.client.comparator.MemberAgeComparator;
import com.delrima.webgene.client.model.HasAncestors;
import com.delrima.webgene.client.model.HasDescendants;
import com.delrima.webgene.client.model.HasTreeMember;
import com.delrima.webgene.client.model.IsTreeMember;

public class RecursiveMemberTreeIterator<T> implements MemberTreeIteratorTemplate<T> {

	private final VisitorAction<T> ancestorAction;
	private final VisitorAction<T> descendantAction;
	private final Comparator<HasTreeMember> childrenComparator = new MemberAgeComparator();

	public RecursiveMemberTreeIterator(VisitorAction<T> action) {
		this(action, action);
	}

	public RecursiveMemberTreeIterator(VisitorAction<T> ancestorAction, VisitorAction<T> descendantAction) {
		this.ancestorAction = ancestorAction;
		this.descendantAction = descendantAction;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param child
	 * @return
	 */
	public final T visitAncestors(HasAncestors child) {
		T memberItem = ancestorAction.onMember(child.getMember());

		// add father
		final HasAncestors father = child.getFather();
		if (father != null && child.getMember().getId() != father.getMember().getId()) {
			ancestorAction.onRelation(memberItem, visitAncestors(child.getFather()));
		}
		// add mother
		final HasAncestors mother = child.getMother();
		if (mother != null && child.getMember().getId() != mother.getMember().getId()) {
			ancestorAction.onRelation(memberItem, visitAncestors(mother));
		}
		return memberItem;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param parent
	 * @return
	 */
	public final T visitDescendants(HasDescendants parent) {
		if (parent == null) {
			return null;
		}

		// member tree item
		final T memberItem = descendantAction.onMember(parent.getMember());

		// recursively visit children (father)
		final boolean descendantsExist = onDescendantRelation(parent.getMember(), memberItem, parent.getChildren());
		// recursively visit children (mother)
		if (!descendantsExist) {
			onDescendantRelation(parent.getMember(), memberItem, parent.getChildren());
		}

		return memberItem;
	}

	boolean onDescendantRelation(IsTreeMember parent, T memberItem, List<HasDescendants> children) {
		boolean descendantsExist = false;

		if (children == null || children.size() <= 0) {
			return descendantsExist;
		}

		Collections.sort(children, childrenComparator);

		for (HasDescendants child : children) {
			if (child == null || child.getMember().getId() != parent.getId()) {
				T item = visitDescendants(child);
				if (item != null) {
					descendantAction.onRelation(memberItem, item);
					descendantsExist = true;
				}
			}
		}
		return descendantsExist;
	}
}

package com.delrima.webgene.client.common;

import com.delrima.webgene.client.model.HasAncestors;
import com.delrima.webgene.client.model.HasDescendants;
import com.delrima.webgene.client.model.IsTreeMember;

public interface MemberTreeIteratorTemplate<T> {

	/**
	 * This action is used to do some work when the member object tree is being visited
	 * 
	 * @param <T>
	 */
	public interface VisitorAction<T> {

		/**
		 * This action will be executed when a relation is found. Relations are parent/child, child/parent
		 * 
		 * @param attachTo
		 *            - depending on which way the tree is being traversed, it can be parent or child
		 * @param attach
		 *            - depending on which way the tree is being traversed, it can be parent or child
		 */
		void onRelation(T attachTo, T attach);

		/**
		 * This action will be executed when a member is visited in the member object tree
		 * 
		 * @param member
		 *            - root member that will be traversed for ancestors/descendants
		 * @return - returns the generic item (to be created by implementing class) associated with the root member
		 */
		T onMember(IsTreeMember member);
	}

	/**
	 * Visits the member object tree for ancestors
	 * 
	 * @param rootMember
	 * @param mode
	 * @return - the root generic item being created
	 */
	T visitAncestors(HasAncestors member);

	/**
	 * Visits the member object tree for descendants
	 * 
	 * @param rootMember
	 * @param mode
	 * @return - the root generic item being created
	 */
	T visitDescendants(HasDescendants member);

}

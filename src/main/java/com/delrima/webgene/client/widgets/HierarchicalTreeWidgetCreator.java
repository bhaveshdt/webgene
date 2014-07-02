package com.delrima.webgene.client.widgets;

import com.delrima.webgene.client.common.Command;
import com.delrima.webgene.client.common.MemberTreeIteratorTemplate.VisitorAction;
import com.delrima.webgene.client.common.RecursiveMemberTreeIterator;
import com.delrima.webgene.client.dto.Member;
import com.delrima.webgene.client.model.HasAncestors;
import com.delrima.webgene.client.model.HasDescendants;
import com.delrima.webgene.client.model.IsTreeMember;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class HierarchicalTreeWidgetCreator extends AbstractTreeCreator {

	private final Panel panel = new VerticalPanel();
	private final Tree familyTree = new Tree();
	
	public HierarchicalTreeWidgetCreator() {
		panel.add(familyTree);
	}
	/**
	 * <p>
	 * Recursively adds all children to tree widget
	 * </p>
	 * 
	 * @param memberMap
	 *          - easy access to all members
	 * @param parentChildRelationBean
	 *          - tree of member objects to be converted to tree of member widgets
	 * @return
	 */

	public void create(final HasAncestors rootMember) {

		familyTree.setVisible(true);
		familyTree.clear();
		VisitorAction<TreeItem> action = new VisitorAction<TreeItem>() {
			@Override
			public void onRelation(TreeItem attachTo, TreeItem attach) {
				attachTo.addItem(attach);
				attachTo.setState(true);
			}

			@Override
			public TreeItem onMember(IsTreeMember member) {
				return createTreeItem(member);
			}
		};
		familyTree.addItem(new RecursiveMemberTreeIterator<TreeItem>(action).visitAncestors(rootMember));

	}
	
	public void create(final HasDescendants rootMember) {

		familyTree.setVisible(true);
		familyTree.clear();
		VisitorAction<TreeItem> action = new VisitorAction<TreeItem>() {
			@Override
			public void onRelation(TreeItem attachTo, TreeItem attach) {
				attachTo.addItem(attach);
				attachTo.setState(true);
			}

			@Override
			public TreeItem onMember(IsTreeMember member) {
				return createTreeItem(member);
			}
		};
		familyTree.addItem(new RecursiveMemberTreeIterator<TreeItem>(action).visitDescendants(rootMember));

	}

	/**
	 * <p>
	 * Create widget for a single member
	 * </p>
	 * 
	 * @param m
	 * @return
	 */
	public TreeItem createTreeItem(IsTreeMember m) {
		// create new item
		Panel mainPanel = createItem(m);

		TreeItem item = new TreeItem(mainPanel);
		item.setUserObject(m);
		return item;
	}

	public Widget getWidget() {
		return panel;
	}

	@Override
	public void addSelectionHandler(final Command<IsTreeMember> command) {
		familyTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				selectedElement = event.getSelectedItem().getWidget().getElement();
				Member member = (Member) event.getSelectedItem().getUserObject();
				command.execute(member);
			}

		});
	}

	@Override
	public TreeViewType getType() {
		return TreeViewType.TREE;
	}
}

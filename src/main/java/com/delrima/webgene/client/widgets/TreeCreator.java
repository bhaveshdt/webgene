package com.delrima.webgene.client.widgets;

import com.delrima.webgene.client.common.Command;
import com.delrima.webgene.client.model.HasAncestors;
import com.delrima.webgene.client.model.HasDescendants;
import com.delrima.webgene.client.model.IsTreeMember;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

public interface TreeCreator {

	void create(final HasAncestors rootMember);

	void create(final HasDescendants rootMember);

	void addSelectionHandler(Command<IsTreeMember> command);

	TreeViewType getType();

	Widget getWidget();

	Element getSelectedElement();
}

package com.delrima.webgene.client.widgets;

import com.delrima.webgene.arch.client.utils.WidgetUtils;
import com.delrima.webgene.client.model.IsTreeMember;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

public abstract class AbstractTreeCreator implements TreeCreator {

    protected Element selectedElement;

    /**
     * <p>
     * Create widget for a single member
     * </p>
     * 
     * @param m
     * @return
     */
    protected Panel createItem(IsTreeMember m) {
        // create new item
        FlowPanel mainPanel = new FlowPanel();
        mainPanel.setStyleName("memberview " + "gender_" + m.getGender());
        mainPanel.add(WidgetUtils.getFlowPanelWithWidgets("webgene-memberView", WidgetUtils.createLabel(m.getFirstname() + " " + m.getLastname())));
        return mainPanel;
    }

    public Element getSelectedElement() {
        return selectedElement;
    }

}

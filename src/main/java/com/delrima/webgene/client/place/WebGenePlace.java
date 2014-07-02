package com.delrima.webgene.client.place;

import com.delrima.webgene.arch.client.base.AbstractView;
import com.delrima.webgene.client.inject.WebgeneWidgetGinjector;
import com.delrima.webgene.client.presenter.MemberTreePresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author Bhavesh
 * 
 */
public class WebGenePlace extends AbstractView {

    private final WebgeneWidgetGinjector injector = GWT.create(WebgeneWidgetGinjector.class);

    @Override
    public Panel createWidget() {
        Panel mainPanel = new FlowPanel();

        final MemberTreePresenter appPresenter = injector.getFamilyTreePresenter();
        appPresenter.go(mainPanel);

        return mainPanel;
    }

    public void process() {

    }

}

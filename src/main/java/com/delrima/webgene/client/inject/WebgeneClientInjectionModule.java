package com.delrima.webgene.client.inject;

import com.delrima.webgene.arch.client.DefaultEventBus;
import com.delrima.webgene.client.presenter.MemberEditPresenter;
import com.delrima.webgene.client.presenter.MemberTreePresenter;
import com.delrima.webgene.client.rpc.ActionHandlerServiceAsync;
import com.delrima.webgene.client.view.MemberEditView;
import com.delrima.webgene.client.view.MemberTreeView;
import com.delrima.webgene.client.widgets.ContactEditWidget;
import com.delrima.webgene.client.widgets.MemberEditViewImpl;
import com.delrima.webgene.client.widgets.MemberEditViewImpl.MemberLookupSuggestOracle;
import com.delrima.webgene.client.widgets.MemberTreeViewImpl;
import com.delrima.webgene.client.widgets.TreeInterfaceCreatorService;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

/**
 * @author Bhavesh
 */
public class WebgeneClientInjectionModule extends AbstractGinModule {

    protected void configure() {
        // Multibinder<TreeCreator> actionBinder = Multibinder.newSetBinder(binder(), TreeCreator.class);
        // actionBinder.addBinding().to(OrgChartTreeCreator.class);
        // actionBinder.addBinding().to(HierarchicalTreeCreator.class);

        // Presenter
        bind(MemberTreePresenter.class).in(Singleton.class);
        bind(MemberEditPresenter.class).in(Singleton.class);

        // View
        bind(MemberTreeView.class).to(MemberTreeViewImpl.class).in(Singleton.class);
        bind(MemberEditView.class).to(MemberEditViewImpl.class).in(Singleton.class);
        bind(ContactEditWidget.class).in(Singleton.class);
        bind(TreeInterfaceCreatorService.class).in(Singleton.class);

        // Lookup Oracle
        bind(MemberLookupSuggestOracle.class).in(Singleton.class);

        // Event Bus
        bind(HandlerManager.class).to(DefaultEventBus.class).in(Singleton.class);

        bind(ActionHandlerServiceAsync.class).in(Singleton.class);
    }
}
package com.delrima.webgene.client.inject;

import com.delrima.webgene.client.presenter.MemberTreePresenter;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(WebgeneClientInjectionModule.class)
public interface WebgeneWidgetGinjector extends Ginjector {

    MemberTreePresenter getFamilyTreePresenter();
}
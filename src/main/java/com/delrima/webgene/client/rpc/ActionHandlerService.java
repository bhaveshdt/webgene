package com.delrima.webgene.client.rpc;

import com.delrima.webgene.client.action.ActionHandler;
import com.delrima.webgene.client.common.Action;
import com.delrima.webgene.client.common.Result;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("action.rpc")
public interface ActionHandlerService<R extends Result> extends RemoteService, ActionHandler<Action<R>, R> {
}
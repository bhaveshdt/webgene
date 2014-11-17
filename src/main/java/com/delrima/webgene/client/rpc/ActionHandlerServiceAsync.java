package com.delrima.webgene.client.rpc;

import com.delrima.webgene.client.common.Action;
import com.delrima.webgene.client.common.Result;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ActionHandlerServiceAsync {

	<R extends Result> void execute(Action<R> action, AsyncCallback<R> callback);
}

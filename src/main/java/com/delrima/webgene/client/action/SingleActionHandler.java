package com.delrima.webgene.client.action;

import com.delrima.webgene.client.common.Action;
import com.delrima.webgene.client.common.Result;

public interface SingleActionHandler<A extends Action<R>, R extends Result> extends ActionHandler<A, R> {

    Class<A> getActionType();
}

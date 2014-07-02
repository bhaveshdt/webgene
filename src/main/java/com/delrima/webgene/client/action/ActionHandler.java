package com.delrima.webgene.client.action;

import com.delrima.webgene.client.common.Action;
import com.delrima.webgene.client.common.ActionException;
import com.delrima.webgene.client.common.Result;

public interface ActionHandler<A extends Action<R>, R extends Result> {

    R execute(A action) throws ActionException;
}

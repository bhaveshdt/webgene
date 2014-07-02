package com.delrima.webgene.server.services;

import java.util.Map;

import javax.inject.Inject;

import com.delrima.webgene.client.action.SingleActionHandler;
import com.delrima.webgene.client.common.Action;
import com.delrima.webgene.client.common.ActionException;
import com.delrima.webgene.client.common.Result;
import com.delrima.webgene.client.rpc.ActionHandlerService;

public class ActionHandlerServiceImpl<R extends Result> implements ActionHandlerService<R> {

    private Map<Class<Action<R>>, SingleActionHandler<Action<R>, R>> actionHandlers;

    @Inject
    public ActionHandlerServiceImpl(Map<Class<Action<R>>, SingleActionHandler<Action<R>, R>> actionHandlers) {
        super();
        this.actionHandlers = actionHandlers;
    }

    @Override
    public R execute(Action<R> action) throws ActionException {
        final SingleActionHandler<Action<R>, R> handler = actionHandlers.get(action.getClass());

        if (handler == null) {
            throw new ActionException("Action Handler Not Found.");
        }

        return handler.execute(action);
    }

}
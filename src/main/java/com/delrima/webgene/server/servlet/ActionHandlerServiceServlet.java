package com.delrima.webgene.server.servlet;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.delrima.webgene.client.common.Action;
import com.delrima.webgene.client.common.ActionException;
import com.delrima.webgene.client.common.Result;
import com.delrima.webgene.client.rpc.ActionHandlerService;
import com.delrima.webgene.server.services.ActionHandlerServiceImpl;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ActionHandlerServiceServlet extends RemoteServiceServlet implements ActionHandlerService<Result> {

    private ActionHandlerService<Result> dispatchService;

    @SuppressWarnings("unchecked")
    @Override
    public void init() throws ServletException {
        // initialize super class
        super.init();

        // retrieve dispatch service dependency
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        this.dispatchService = context.getBean(ActionHandlerServiceImpl.class);
    }

    @Override
    public Result execute(Action<Result> action) throws ActionException {
        return dispatchService.execute(action);
    }

}
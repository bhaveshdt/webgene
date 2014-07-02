package com.delrima.webgene.arch.client;

import com.google.gwt.event.shared.HandlerManager;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DefaultEventBus extends HandlerManager {

    @Inject
    public DefaultEventBus() {
        super(null);
    }

}

package com.delrima.webgene.client.event.handler;

import com.delrima.webgene.client.event.RetrieveMemberLookupResultEvent;
import com.google.gwt.event.shared.EventHandler;

/**
 * @author bhaveshthakker
 * 
 */
public interface RetrieveMemberLookupResultEventHandler extends EventHandler {

    /**
     * <p>
     * </p>
     * 
     * @param event
     */
    void onMemberLookupResultRetrieveRequest(RetrieveMemberLookupResultEvent event);
}

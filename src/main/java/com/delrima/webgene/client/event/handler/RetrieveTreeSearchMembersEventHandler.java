package com.delrima.webgene.client.event.handler;

import com.delrima.webgene.client.event.RetrieveTreeSearchMembersEvent;
import com.google.gwt.event.shared.EventHandler;

/**
 * @author bhaveshthakker
 * 
 */
public interface RetrieveTreeSearchMembersEventHandler extends EventHandler {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param event
	 */
	void onTreeSearchMemberRetrieveRequest(RetrieveTreeSearchMembersEvent event);
}

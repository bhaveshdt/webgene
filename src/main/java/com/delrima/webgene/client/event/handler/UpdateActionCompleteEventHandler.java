package com.delrima.webgene.client.event.handler;

import com.delrima.webgene.client.event.UpdateActionCompleteEvent;
import com.google.gwt.event.shared.EventHandler;

/**
 * <code><B>MemberChangeHandler</code></b>
 * <p>
 * Handler used to execute code associated with server messages being present on the html page via the JSON variable "serverMessages"
 * 
 * This handler must be used only for messages that are received with a page refresh.
 * </p>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since Jul 22, 2009
 */
public interface UpdateActionCompleteEventHandler extends EventHandler {

	/**
	 * <p>
	 * Code to be executed when server messages are present
	 * </p>
	 * 
	 * @param event
	 */
	void onUpdateActionComplete(UpdateActionCompleteEvent event);
}

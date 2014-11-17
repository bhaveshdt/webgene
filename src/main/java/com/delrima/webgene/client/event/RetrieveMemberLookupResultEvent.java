package com.delrima.webgene.client.event;

import com.delrima.webgene.client.common.Command;
import com.delrima.webgene.client.event.handler.RetrieveMemberLookupResultEventHandler;
import com.delrima.webgene.client.result.MemberLookupResult;
import com.google.gwt.event.shared.GwtEvent;

/**
 * <code><B>MemberChangeEvent</code></b>
 * <p>
 * Event associated with server messages data being present in the JSON variable "serverMessages"
 * </p>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since Jul 22, 2009
 * 
 */
public class RetrieveMemberLookupResultEvent extends GwtEvent<RetrieveMemberLookupResultEventHandler> {

	/**
	 * Event type for server message events. Represents the meta-data associated with this event.
	 */
	private static final GwtEvent.Type<RetrieveMemberLookupResultEventHandler> TYPE = new GwtEvent.Type<RetrieveMemberLookupResultEventHandler>();

	/**
	 * Gets the event type associated with server messages events.
	 * 
	 * @return the handler type
	 */
	public static GwtEvent.Type<RetrieveMemberLookupResultEventHandler> getType() {
		return TYPE;
	}

	/**
	 * If true, server-side processing is ignored. Indicates this event is being triggered for client-side processing only
	 */
	private final String query;
	/**
	 * Execute this command after the results are retrieved
	 */
	private final Command<MemberLookupResult> command;

	public RetrieveMemberLookupResultEvent(String query, Command<MemberLookupResult> command) {
		super();
		this.query = query;
		this.command = command;
	}

	/**
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(RetrieveMemberLookupResultEventHandler handler) {
		handler.onMemberLookupResultRetrieveRequest(this);
	}

	/**
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<RetrieveMemberLookupResultEventHandler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	public Command<MemberLookupResult> getCommand() {
		return command;
	}

}

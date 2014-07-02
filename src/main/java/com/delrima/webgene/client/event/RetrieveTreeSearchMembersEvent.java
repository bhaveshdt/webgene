package com.delrima.webgene.client.event;

import java.util.List;

import com.delrima.webgene.client.common.Command;
import com.delrima.webgene.client.dto.Member;
import com.delrima.webgene.client.event.handler.RetrieveTreeSearchMembersEventHandler;
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
public class RetrieveTreeSearchMembersEvent extends GwtEvent<RetrieveTreeSearchMembersEventHandler> {

    /**
     * Event type for server message events. Represents the meta-data associated with this event.
     */
    private static final GwtEvent.Type<RetrieveTreeSearchMembersEventHandler> TYPE = new GwtEvent.Type<RetrieveTreeSearchMembersEventHandler>();

    /**
     * Gets the event type associated with server messages events.
     * 
     * @return the handler type
     */
    public static GwtEvent.Type<RetrieveTreeSearchMembersEventHandler> getType() {
        return TYPE;
    }

    /**
     * If true, server-side processing is ignored. Indicates this event is being triggered for client-side processing only
     */
    private final String query;
    /**
     * Execute this command after the results are retrieved
     */
    private final Command<List<Member>> command;

    public RetrieveTreeSearchMembersEvent(String query, Command<List<Member>> command) {
        super();
        this.query = query;
        this.command = command;
    }

    /**
     * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
     */
    @Override
    protected void dispatch(RetrieveTreeSearchMembersEventHandler handler) {
        handler.onTreeSearchMemberRetrieveRequest(this);
    }

    /**
     * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
     */
    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<RetrieveTreeSearchMembersEventHandler> getAssociatedType() {
        return TYPE;
    }

    /**
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    public Command<List<Member>> getCommand() {
        return command;
    }

}

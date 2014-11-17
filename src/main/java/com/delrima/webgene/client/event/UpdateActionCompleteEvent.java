package com.delrima.webgene.client.event;

import com.delrima.webgene.client.event.handler.UpdateActionCompleteEventHandler;
import com.delrima.webgene.client.result.UpdateMemberResult;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * <code><B>MemberChangeEvent</code></b>
 * <p>
 * Event associated with server messages data being present in the JSON variable "serverMessages"
 * </p>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since Jul 22, 2009
 */
public class UpdateActionCompleteEvent extends GwtEvent<UpdateActionCompleteEventHandler> {

	/**
	 * @author Bhavesh
	 */
	public enum NAME implements IsSerializable {
		ADD_MEMBER("Add Member"), ADD_CHILD("Add Child"), ADD_PARENT("Add Parent"), EDIT_MEMBER("Edit Member"), DELETE_MEMBER("Delete Member"), SELECT_MEMBER("Select Member");

		private final String description;

		private NAME(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}

	private final UpdateMemberResult result;

	public UpdateActionCompleteEvent(UpdateMemberResult result) {
		super();
		this.result = result;
	}

	/**
	 * Event type for server message events. Represents the meta-data associated with this event.
	 */
	private static final GwtEvent.Type<UpdateActionCompleteEventHandler> TYPE = new GwtEvent.Type<UpdateActionCompleteEventHandler>();

	/**
	 * Gets the event type associated with server messages events.
	 * 
	 * @return the handler type
	 */
	public static GwtEvent.Type<UpdateActionCompleteEventHandler> getType() {
		return TYPE;
	}

	/**
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(UpdateActionCompleteEventHandler handler) {
		handler.onUpdateActionComplete(this);
	}

	/**
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UpdateActionCompleteEventHandler> getAssociatedType() {
		return TYPE;
	}

	public UpdateMemberResult getResult() {
		return result;
	}

}

package com.delrima.webgene.client.result;

import com.delrima.webgene.client.common.Result;
import com.delrima.webgene.client.event.UpdateActionCompleteEvent.NAME;
import com.delrima.webgene.client.model.MemberWithImmediateRelations;
import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class UpdateMemberResult implements Result, IsSerializable {

	private MemberWithImmediateRelations member;
	private NAME eventName;

	public UpdateMemberResult() {
		this(null, null);
	}

	public UpdateMemberResult(MemberWithImmediateRelations member, NAME eventName) {
		super();
		this.member = member;
		this.eventName = eventName;
	}

	public MemberWithImmediateRelations getMember() {
		return member;
	}

	public NAME getEventName() {
		return eventName;
	}

}

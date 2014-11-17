package com.delrima.webgene.client.action;

import com.delrima.webgene.client.common.Action;
import com.delrima.webgene.client.result.RetrieveMemberTreeResult;
import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class RetrieveMemberTreeAction implements Action<RetrieveMemberTreeResult>, IsSerializable {

	private Long[] memberIds;

	public RetrieveMemberTreeAction() {
		this(1l);
	}

	public RetrieveMemberTreeAction(Long... memberId) {
		super();
		this.memberIds = memberId;
	}

	public Long[] getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(Long[] memberId) {
		this.memberIds = memberId;
	}

}

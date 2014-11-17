package com.delrima.webgene.client.action;

import com.delrima.webgene.client.common.Action;
import com.delrima.webgene.client.result.RetrieveDescendantsTreeResult;
import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class RetrieveDescendantMembersAction implements Action<RetrieveDescendantsTreeResult>, IsSerializable {

	private Long parentMemberId;

	public RetrieveDescendantMembersAction() {
		this(1l);
	}

	public RetrieveDescendantMembersAction(Long parentMemberId) {
		super();
		this.parentMemberId = parentMemberId;
	}

	public Long getParentMemberId() {
		return parentMemberId;
	}

	public void setParentMemberId(Long parentMemberId) {
		this.parentMemberId = parentMemberId;
	}

}

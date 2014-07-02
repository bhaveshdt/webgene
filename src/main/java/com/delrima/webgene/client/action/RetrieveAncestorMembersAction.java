package com.delrima.webgene.client.action;

import com.delrima.webgene.client.common.Action;
import com.delrima.webgene.client.result.RetrieveAncestorsTreeResult;
import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class RetrieveAncestorMembersAction implements Action<RetrieveAncestorsTreeResult>, IsSerializable {

    private Long parentMemberId;

    public RetrieveAncestorMembersAction() {
        this(1l);
    }

    public RetrieveAncestorMembersAction(Long parentMemberId) {
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

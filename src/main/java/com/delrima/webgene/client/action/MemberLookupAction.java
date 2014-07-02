package com.delrima.webgene.client.action;

import com.delrima.webgene.client.common.Action;
import com.delrima.webgene.client.result.MemberLookupResult;
import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class MemberLookupAction implements Action<MemberLookupResult>, IsSerializable {

    private String memberLookupQuery;

    @Deprecated
    public MemberLookupAction() {
        this("");
    }

    public MemberLookupAction(String memberLookupQuery) {
        super();
        this.memberLookupQuery = memberLookupQuery;
    }

    /**
     * @return the memberLookupQuery
     */
    public String getMemberLookupQuery() {
        return memberLookupQuery;
    }

}

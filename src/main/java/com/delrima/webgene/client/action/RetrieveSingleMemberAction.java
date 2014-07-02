package com.delrima.webgene.client.action;

import com.delrima.webgene.client.common.Action;
import com.delrima.webgene.client.model.MemberWithImmediateRelations;
import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class RetrieveSingleMemberAction implements Action<MemberWithImmediateRelations>, IsSerializable {

    private Long memberId;

    @Deprecated
    public RetrieveSingleMemberAction() {
        this(0l);
    }

    public RetrieveSingleMemberAction(Long memberId) {
        super();
        this.memberId = memberId;
    }

    /**
     * @return the memberLookupQuery
     */
    public Long getMemberId() {
        return memberId;
    }

}

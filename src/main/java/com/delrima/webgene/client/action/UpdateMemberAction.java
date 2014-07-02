package com.delrima.webgene.client.action;

import com.delrima.webgene.client.common.Action;
import com.delrima.webgene.client.common.Result;
import com.delrima.webgene.client.dto.Member;
import com.delrima.webgene.client.event.UpdateActionCompleteEvent.NAME;
import com.delrima.webgene.client.result.UpdateMemberResult;
import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class UpdateMemberAction implements Action<UpdateMemberResult>, Result, IsSerializable {

    private Member relativeMember;
    private Member member;
    private NAME eventName;

    public UpdateMemberAction() {
    }

    public UpdateMemberAction(Member relativeMember, Member member, NAME eventName) {
        super();
        this.relativeMember = relativeMember;
        this.member = member;
        this.eventName = eventName;
    }

    public Member getRelativeMember() {
        return relativeMember;
    }

    public Member getMember() {
        return member;
    }

    public NAME getEventName() {
        return eventName;
    }

}

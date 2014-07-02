package com.delrima.webgene.client.model;

import com.delrima.webgene.client.event.UpdateActionCompleteEvent;

public class MemberEditOperationModel {

    private MemberWithImmediateRelations selectedMember;
    private UpdateActionCompleteEvent.NAME selectedMemberChangeEventName;

    public UpdateActionCompleteEvent.NAME getSelectedMemberChangeEventName() {
        return selectedMemberChangeEventName;
    }

    public void setSelectedMemberChangeEventName(UpdateActionCompleteEvent.NAME selectedMemberChangeEventName) {
        this.selectedMemberChangeEventName = selectedMemberChangeEventName;
    }

    public MemberWithImmediateRelations getSelectedMember() {
        return selectedMember;
    }

    public void setSelectedMember(MemberWithImmediateRelations selectedMember) {
        this.selectedMember = selectedMember;
    }
}
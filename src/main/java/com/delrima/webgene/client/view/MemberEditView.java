package com.delrima.webgene.client.view;

import com.delrima.webgene.arch.client.base.ModelBinder;
import com.delrima.webgene.client.event.handler.RetrieveMemberLookupResultEventHandler;
import com.delrima.webgene.client.model.IsTreeMember;
import com.delrima.webgene.client.model.MemberWithImmediateRelations;

public interface MemberEditView extends ModelBinder<MemberWithImmediateRelations> {

    interface Presenter extends RetrieveMemberLookupResultEventHandler {

        public static final Integer LOOKUP_MINIMUM_CHAR_COUNT = 2;

        void persistMemberChange();

        void showAddMember();

        void showAddChild(MemberWithImmediateRelations parentMember);

        void showAddParent(MemberWithImmediateRelations childMember);

        void showEditMember(MemberWithImmediateRelations selectedMember);

        void deleteMember(IsTreeMember selectedMember);
    }

    void setPresenter(Presenter presenter);

    void clearMemberEditData();

    void displayErrorMessage(String errorMessage);

    void clearErrorMessage();

}

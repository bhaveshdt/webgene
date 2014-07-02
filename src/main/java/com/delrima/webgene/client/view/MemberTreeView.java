package com.delrima.webgene.client.view;

import com.delrima.webgene.client.enums.MemberDisplayMode;
import com.delrima.webgene.client.event.handler.RetrieveMemberLookupResultEventHandler;
import com.delrima.webgene.client.event.handler.UpdateActionCompleteEventHandler;
import com.delrima.webgene.client.model.HasAncestors;
import com.delrima.webgene.client.model.HasDescendants;
import com.delrima.webgene.client.model.IsTreeMember;
import com.delrima.webgene.client.widgets.TreeViewType;
import com.google.gwt.user.client.ui.HasWidgets;

public interface MemberTreeView {

    interface Presenter extends UpdateActionCompleteEventHandler, RetrieveMemberLookupResultEventHandler {

        public final Integer LOOKUP_MINIMUM_CHAR_COUNT = 2;

        /**
         * <p>
         * The command to execute when a member is searched and selected
         * </p>
         * 
         * @param memberId
         *            - member that is being searched
         */
        void onRootMemberSuggestionSelected(IsTreeMember member);

        void onTreeMemberSelected(IsTreeMember member);

        void onDisplayModeSelected();

        void onViewTypeSelected();

        void onAddChild();

        void onEditMember();

        void onAddParent();

        void onAddMember();

        void onDeleteMember();

        void onSelectMember();

    }

    MemberDisplayMode getSelectedDisplayMode();

    TreeViewType getSelectedViewType();

    void renderFamilyTree(HasAncestors ancestors, TreeViewType viewType);

    void renderFamilyTree(HasDescendants descendants, TreeViewType viewType);

    void showMenuForMember(IsTreeMember member);

    void showMemberEditLayer();

    void setPresenter(Presenter presenter);

    void addToContainer(HasWidgets container);

    void displayErrorMessage(String errorMessage);

    void clearErrorMessage();

    void updateRootMember(String name);

    void clearTree();
}

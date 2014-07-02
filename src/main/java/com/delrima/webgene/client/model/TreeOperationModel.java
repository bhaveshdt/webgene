package com.delrima.webgene.client.model;

import com.delrima.webgene.client.enums.MemberDisplayMode;
import com.delrima.webgene.client.result.RetrieveMemberTreeResult;
import com.delrima.webgene.client.widgets.TreeViewType;

public class TreeOperationModel {

    private IsTreeMember selectedMember;
    private RetrieveMemberTreeResult displayModel;
    private IsTreeMember rootMember;
    private MemberDisplayMode mode;
    private TreeViewType viewType;

    public IsTreeMember getSelectedMember() {
        return selectedMember;
    }

    public void setSelectedMember(IsTreeMember selectedMember) {
        this.selectedMember = selectedMember;
    }

    public IsTreeMember getRootMember() {
        return rootMember;
    }

    public void setRootMember(IsTreeMember topLevelParent) {
        this.rootMember = topLevelParent;
    }

    public MemberDisplayMode getMode() {
        return mode;
    }

    public void setMode(MemberDisplayMode mode) {
        this.mode = mode;
    }

    public final TreeViewType getViewType() {
        return viewType;
    }

    public final void setViewType(TreeViewType viewType) {
        this.viewType = viewType;
    }

    public RetrieveMemberTreeResult getDisplayModel() {
        return displayModel;
    }

    public void setDisplayModel(RetrieveMemberTreeResult displayModel) {
        this.displayModel = displayModel;
    }

}
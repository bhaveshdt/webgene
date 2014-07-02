package com.delrima.webgene.client.result;

import com.delrima.webgene.client.common.Result;
import com.delrima.webgene.client.model.HasDescendants;
import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class RetrieveDescendantsTreeResult implements Result, IsSerializable {

    private HasDescendants rootMember;

    /**
     * For GWT Serialization
     */
    public RetrieveDescendantsTreeResult() {
    }

    public RetrieveDescendantsTreeResult(HasDescendants rootMember) {
        super();
        this.rootMember = rootMember;
    }

    public HasDescendants getRootMember() {
        return rootMember;
    }

    public void setRootMember(HasDescendants familyTreeMembers) {
        this.rootMember = familyTreeMembers;
    }

}

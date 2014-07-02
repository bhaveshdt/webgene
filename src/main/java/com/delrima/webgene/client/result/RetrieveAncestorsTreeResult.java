package com.delrima.webgene.client.result;

import com.delrima.webgene.client.common.Result;
import com.delrima.webgene.client.model.HasAncestors;
import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class RetrieveAncestorsTreeResult implements Result, IsSerializable {

    private HasAncestors rootMember;

    /**
     * For GWT Serialization
     */
    public RetrieveAncestorsTreeResult() {
    }

    public RetrieveAncestorsTreeResult(HasAncestors rootMember) {
        super();
        this.rootMember = rootMember;
    }

    public HasAncestors getRootMember() {
        return rootMember;
    }

    public void setRootMember(HasAncestors familyTreeMembers) {
        this.rootMember = familyTreeMembers;
    }

}

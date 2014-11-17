package com.delrima.webgene.client.result;

import java.util.HashSet;
import java.util.Set;

import com.delrima.webgene.client.common.Result;
import com.delrima.webgene.client.model.IsTreeMember;
import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class RetrieveMemberTreeResult implements Result, IsSerializable {

	private Set<IsTreeMember> members;

	/**
	 * For GWT Serialization
	 */
	public RetrieveMemberTreeResult() {
	}

	public RetrieveMemberTreeResult(Set<IsTreeMember> members) {
		super();
		this.members = members;
	}

	public Set<IsTreeMember> getMembers() {
		return members;
	}

	public void setMembers(Set<IsTreeMember> members) {
		this.members = members;
	}

	public static RetrieveMemberTreeResult EMPTY_RESULT() {
		return new RetrieveMemberTreeResult(new HashSet<IsTreeMember>());
	}

}

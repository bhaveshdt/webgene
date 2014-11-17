package com.delrima.webgene.client.result;

import java.util.HashSet;

import com.delrima.webgene.client.common.Result;
import com.delrima.webgene.client.model.IsTreeMember;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author bhaveshthakker
 * 
 */
@SuppressWarnings("serial")
public class MemberLookupResult implements Result, IsSerializable {

	private HashSet<IsTreeMember> members;

	/**
	 * For GWT Serialization
	 */
	public MemberLookupResult() {
		this(new HashSet<IsTreeMember>());
	}

	public MemberLookupResult(HashSet<IsTreeMember> members) {
		super();
		this.members = members;
	}

	public HashSet<IsTreeMember> getMembers() {
		return members;
	}

	public void setMembers(HashSet<IsTreeMember> members) {
		this.members = members;
	}

}

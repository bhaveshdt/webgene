package com.delrima.webgene.client.result;

import com.delrima.webgene.client.common.Result;
import com.delrima.webgene.client.dto.Member;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author bhaveshthakker
 * 
 */
@SuppressWarnings("serial")
public class RetrieveSingleMemberResult implements Result, IsSerializable {

	private Member member;

	/**
	 * For GWT Serialization
	 */
	public RetrieveSingleMemberResult() {
		this(new Member());
	}

	public RetrieveSingleMemberResult(Member member) {
		super();
		this.member = member;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

}

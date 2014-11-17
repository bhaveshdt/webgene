package com.delrima.webgene.client.model;

import java.io.Serializable;
import java.util.Set;

import com.delrima.webgene.client.dto.Member;
import com.google.gwt.user.client.rpc.IsSerializable;

public class MemberWithImmediateRelations implements HasMember, IsSerializable, Serializable {

	private static final long serialVersionUID = 1L;

	private Member member;

	private Member mother;

	private Member father;

	private Member spouse;

	private Set<Member> children;

	@Deprecated
	public MemberWithImmediateRelations() {

	}

	public MemberWithImmediateRelations(Member member, Member father, Member mother, Member spouse, Set<Member> children) {
		super();
		this.member = member;
		this.mother = mother;
		this.father = father;
		this.spouse = spouse;
		this.children = children;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Member getMother() {
		return mother;
	}

	public void setMother(Member mother) {
		this.mother = mother;
	}

	public Member getFather() {
		return father;
	}

	public void setFather(Member father) {
		this.father = father;
	}

	public Member getSpouse() {
		return spouse;
	}

	public void setSpouse(Member spouse) {
		this.spouse = spouse;
	}

	public Set<Member> getChildren() {
		return children;
	}

	public void setChildren(Set<Member> children) {
		this.children = children;
	}

}

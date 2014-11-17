package com.delrima.webgene.client.dao;

import java.util.List;

import com.delrima.webgene.client.dto.Member;

public interface MemberTreeDAO extends MemberRelationDAO<Member> {
	List<Member> retrieveMembersByName(String name);

	List<Member> retrieveAllMembers();

	Member updateMember(Member member);

	Member addMember(Member member);

	void deleteMember(Long id);

}

package com.delrima.webgene.client.dao;

import java.util.List;
import java.util.Set;

import com.delrima.webgene.client.dto.Member;
import com.delrima.webgene.client.model.IsTreeMember;

/**
 * @author bhaveshthakker
 */
public interface MemberTreeDataProvider {

	public Member retrieveMemberById(Long memberId);

	public Set<IsTreeMember> retrieveAllMemberTree();

	public List<Member> retrieveMembersByName(String name);

	public Member updateMember(Member mEntry);

	public Member addMember(Member mEntry);

	public void deleteMember(Long memberId);

	public Set<Member> retrieveChildren(Long id);

}

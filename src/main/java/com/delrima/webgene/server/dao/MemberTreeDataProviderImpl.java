package com.delrima.webgene.server.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.delrima.webgene.client.dao.MemberTreeDAO;
import com.delrima.webgene.client.dao.MemberTreeDataProvider;
import com.delrima.webgene.client.dto.Member;
import com.delrima.webgene.client.model.IsTreeMember;

@Transactional
public class MemberTreeDataProviderImpl implements MemberTreeDataProvider {

	private final MemberTreeDAO dao;

	@Autowired
	public MemberTreeDataProviderImpl(@Qualifier("gaeDao") MemberTreeDAO dao) {
		this.dao = dao;
	}

	@Override
	public Member addMember(Member member) {
		member.setMembersince(new Date());
		capitalizeMemberName(member);

		return dao.addMember(member);
	}

	/**
	 * Capitalize names
	 *
	 * @param member
	 */
	private void capitalizeMemberName(Member member) {
		member.setFirstname(StringUtils.capitalize(member.getFirstname()));
		member.setLastname(StringUtils.capitalize(member.getLastname()));
		member.setMiddlename(StringUtils.capitalize(member.getMiddlename()));
		member.setMaidenname(StringUtils.capitalize(member.getMaidenname()));
	}

	@Override
	public void deleteMember(Long memberId) {
		dao.deleteMember(memberId);
	}

	@Override
	public Set<IsTreeMember> retrieveAllMemberTree() {
		final List<Member> members = dao.retrieveAllMembers();
		return new HashSet<IsTreeMember>(members);
	}

	@Override
	public Set<Member> retrieveChildren(Long id) {
		if (id == null) {
			return null;
		}
		return dao.retrieveChildren(id);
	}

	@Override
	public Member retrieveMemberById(Long memberId) {
		if (memberId == null) {
			return null;
		}
		return dao.retrieveMemberById(memberId);
	}

	@Override
	public List<Member> retrieveMembersByName(String name) {
		return dao.retrieveMembersByName(StringUtils.capitalize(name));
	}

	@Override
	public Member updateMember(Member member) {
		capitalizeMemberName(member);
		dao.updateMember(member);
		return member;
	}

}

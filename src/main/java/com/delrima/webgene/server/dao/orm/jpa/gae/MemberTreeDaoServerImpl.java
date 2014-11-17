/*
 * SqlMapFamilyTreeDao.java Created on March 8, 2007, 12:45 PM To change this
 * template, choose Tools | Template Manager and open the template in the
 * editor.
 */

package com.delrima.webgene.server.dao.orm.jpa.gae;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.delrima.webgene.client.dao.MemberTreeDAO;
import com.delrima.webgene.client.dto.Contact;
import com.delrima.webgene.client.dto.Member;

/**
 * Hibernate implementation of the family tree DAO
 */
@Transactional(propagation = Propagation.MANDATORY)
@Named("gaeDao")
public class MemberTreeDaoServerImpl implements MemberTreeDAO {

	@PersistenceContext
	private final EntityManager entityManager;

	@Inject
	public MemberTreeDaoServerImpl(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	public Contact retrieveContactById(Long id) {
		Contact contact = entityManager.find(Contact.class, id);
		detach(contact);
		return contact;
	}

	private Member retrieveAttachedMemberById(Long id) {
		return this.entityManager.find(Member.class, id);
	}

	public Member retrieveMemberById(Long id) {
		Member member = entityManager.find(Member.class, id);
		detach(member);
		return member;
	}

	void detach(Object entity) {
		this.entityManager.flush();
		this.entityManager.detach(entity);
	}

	public Member updateMember(Member member) {
		this.entityManager.merge(member);
		this.detach(member);
		return member;
	}

	public Member addMember(Member newMember) {
		this.entityManager.persist(newMember);
		this.entityManager.merge(newMember);
		detach(newMember);
		return newMember;
	}

	public void deleteMember(Long id) {
		Member member = this.retrieveAttachedMemberById(id);
		this.entityManager.remove(member);
		this.entityManager.refresh(member);
	}

	public Contact addContact(Contact contact) {
		this.entityManager.persist(contact);
		this.entityManager.merge(contact);
		detach(contact);
		return contact;
	}

	@SuppressWarnings("unchecked")
	public java.util.List<Member> retrieveMembersByName(String name) {
		Query query = entityManager.createQuery("SELECT o FROM Member o WHERE o.firstname >= :nameStart and o.firstname < :nameEnd ORDER BY o.firstname");
		query.setParameter("nameStart", name);
		query.setParameter("nameEnd", name + "\ufffd");
		List<Member> result = query.getResultList();

		for (Member m : result) {
			detach(m);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Member> retrieveChildren(Long id) {
		Set<Member> childrenResult = new HashSet<Member>();
		{
			Query query = entityManager.createQuery("SELECT o FROM Member o WHERE o.motherid = :parentId");
			query.setParameter("parentId", id);
			List<Member> members = query.getResultList();
			if (members != null) {
				childrenResult.addAll(members);
				// detach
				for (Member member : members) {
					detach(member);
				}
			}
		}
		{
			Query query = entityManager.createQuery("SELECT o FROM Member o WHERE o.fatherid = :parentId");
			query.setParameter("parentId", id);
			List<Member> members = query.getResultList();
			if (members != null) {
				childrenResult.addAll(members);
				// detach
				for (Member member : members) {
					detach(member);
				}
			}
		}
		return childrenResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Member> retrieveAllMembers() {
		Query query = entityManager.createQuery("SELECT o FROM Member o");
		List<Member> result = query.getResultList();

		for (Member m : result) {
			detach(m);
		}

		return result;

	}

}

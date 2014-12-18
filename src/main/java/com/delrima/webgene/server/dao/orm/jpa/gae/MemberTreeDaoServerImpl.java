/*
 * SqlMapFamilyTreeDao.java Created on March 8, 2007, 12:45 PM To change this
 * template, choose Tools | Template Manager and open the template in the
 * editor.
 */

package com.delrima.webgene.server.dao.orm.jpa.gae;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	public MemberTreeDaoServerImpl(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	public Contact addContact(Contact contact) {
		entityManager.persist(contact);
		entityManager.merge(contact);
		detach(contact);
		return contact;
	}

	@Override
	public Member addMember(Member newMember) {
		entityManager.persist(newMember);
		entityManager.merge(newMember);
		detach(newMember);
		return newMember;
	}

	@Override
	public void deleteMember(Long id) {
		final Member member = retrieveAttachedMemberById(id);
		entityManager.remove(member);
		entityManager.refresh(member);
	}

	void detach(Object entity) {
		entityManager.flush();
		entityManager.detach(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Member> retrieveAllMembers() {
		final Query query = entityManager.createQuery("SELECT o FROM Member o");
		final List<Member> result = query.getResultList();

		for (final Member m : result) {
			detach(m);
		}

		return result;

	}

	private Member retrieveAttachedMemberById(Long id) {
		return entityManager.find(Member.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Member> retrieveChildren(Long id) {
		final Set<Member> childrenResult = new HashSet<Member>();
		{
			final Query query = entityManager.createQuery("SELECT o FROM Member o WHERE o.motherid = :parentId");
			query.setParameter("parentId", id);
			final List<Member> members = query.getResultList();
			if (members != null) {
				childrenResult.addAll(members);
				// detach
				for (final Member member : members) {
					detach(member);
				}
			}
		}
		{
			final Query query = entityManager.createQuery("SELECT o FROM Member o WHERE o.fatherid = :parentId");
			query.setParameter("parentId", id);
			final List<Member> members = query.getResultList();
			if (members != null) {
				childrenResult.addAll(members);
				// detach
				for (final Member member : members) {
					detach(member);
				}
			}
		}
		return childrenResult;
	}

	public Contact retrieveContactById(Long id) {
		final Contact contact = entityManager.find(Contact.class, id);
		detach(contact);
		return contact;
	}

	@Override
	public Member retrieveMemberById(Long id) {
		final Member member = entityManager.find(Member.class, id);
		detach(member);
		return member;
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<Member> retrieveMembersByName(String name) {
		final Query query = entityManager.createQuery("SELECT o FROM Member o WHERE o.firstname >= :nameStart and o.firstname < :nameEnd ORDER BY o.firstname");
		query.setParameter("nameStart", name);
		query.setParameter("nameEnd", name + "\ufffd");
		final List<Member> result = query.getResultList();

		for (final Member m : result) {
			detach(m);
		}

		return result;
	}

	@Override
	public Member updateMember(Member member) {
		entityManager.merge(member);
		detach(member);
		return member;
	}

}

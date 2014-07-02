package com.delrima.webgene.server.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.delrima.webgene.client.common.HierarchicalDataCreator;
import com.delrima.webgene.client.dao.MemberTreeDataProvider;
import com.delrima.webgene.client.dto.Member;
import com.delrima.webgene.client.enums.GenderIdentifier;
import com.delrima.webgene.client.model.HasAncestors;
import com.delrima.webgene.client.model.HasDescendants;
import com.delrima.webgene.client.model.IsTreeMember;
import com.delrima.webgene.client.model.MemberWithImmediateRelations;
import com.delrima.webgene.server.configuration.ContainerConfiguration;

/**
 * <p>
 * These tests are expected to be run as a whole
 * </p>
 * 
 * @since Mar 24, 2012
 */
@TransactionConfiguration(defaultRollback = true)
@Transactional(propagation = Propagation.REQUIRED)
@ContextConfiguration(classes = ContainerConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class MemberTreeDataProviderTest extends DataProviderTestBase {

	enum InitialLoadMember {
		UNKNOWN
	}

	enum MemberName {
		RITIKA, RINA, NEHA, BHAVESH, JINISHA, KETAN, MRUDULA, DILIP, NARANJI, ZAVER, MANJI, VIMLA,

		DELETE
	}

	@Inject
	private MemberTreeDataProvider dataProvider;

	@Test
	public void initialize() {
		// delete all database entries
		Member member;
		for (MemberName name : MemberName.values()) {
			member = this.getFirstMember(name);
			if (member != null) {
				dataProvider.deleteMember(member.getId());
			}
		}
	}

	@Test
	public void testAddMember() {
		Member addedMember;
		Member retrievedMember;
		for (MemberName name : MemberName.values()) {
			addedMember = dataProvider.addMember(createMember(name));
			retrievedMember = dataProvider.retrieveMemberById(addedMember
					.getId());
			assertEquals("Failed for member: " + name, addedMember.getId(),
					retrievedMember.getId());
		}
	}

	Member createMember(MemberName name) {
		Member newMember = new Member();
		newMember.setFirstname(name.toString());
		newMember.setLastname(name.toString());
		newMember.setGender(GenderIdentifier.U.toString());
		return newMember;
	}

	@Test
	public void testRetrieveMembersByName() {
		List<Member> members;
		for (MemberName name : MemberName.values()) {
			members = dataProvider.retrieveMembersByName(name.toString());
			assertFalse("Failed for member: " + name, members == null);
			assertFalse("Failed for member: " + name, members.size() == 0);
			assertEquals("Failed for member: " + name, name.toString(), members
					.get(0).getFirstname());
		}
	}

	@Test
	public void testUpdateMember() {
		// update children with parents and assert valid update
		for (MemberName name : MemberName.values()) {
			switch (name) {
			case DILIP:
				setParents(name, MemberName.NARANJI, MemberName.ZAVER);
				assertValidParents(name, MemberName.NARANJI, MemberName.ZAVER);
				break;
			case MRUDULA:
				setParents(name, MemberName.MANJI, MemberName.VIMLA);
				assertValidParents(name, MemberName.MANJI, MemberName.VIMLA);
				break;
			case JINISHA:
			case BHAVESH:
				setParents(name, MemberName.DILIP, MemberName.MRUDULA);
				assertValidParents(name, MemberName.DILIP, MemberName.MRUDULA);
				break;
			case RITIKA:
			case RINA:
			case NEHA:
				setParents(name, MemberName.KETAN, MemberName.JINISHA);
				assertValidParents(name, MemberName.KETAN, MemberName.JINISHA);
				break;
			}
		}

		Member updateGenderMember = getFirstMember(MemberName.JINISHA);
		updateGenderMember.setGender(GenderIdentifier.M.toString());
		dataProvider.updateMember(updateGenderMember);
		assertEquals(GenderIdentifier.M.toString(),
				getFirstMember(MemberName.JINISHA).getGender());

		updateGenderMember.setGender(GenderIdentifier.F.toString());
		dataProvider.updateMember(updateGenderMember);
		assertEquals(GenderIdentifier.F.toString(),
				getFirstMember(MemberName.JINISHA).getGender());
	}

	private void assertValidParents(MemberName memberName, MemberName father,
			MemberName mother) {
		MemberWithImmediateRelations member = getMemberWithParentsByName(memberName);
		assertNotNull(member);
		assertNotNull("Empty father: " + memberName, member.getFather());
		assertNotNull("Empty mother: " + memberName, member.getMother());
		assertNotNull(father);
		assertNotNull(mother);
		assertEquals(father.toString(), member.getFather().getFirstname());
		assertEquals(mother.toString(), member.getMother().getFirstname());
	}

	private MemberWithImmediateRelations getMemberWithParentsByName(
			MemberName memberName) {
		Member member = this.getFirstMember(memberName);
		Member father = (this.dataProvider.retrieveMemberById(member
				.getFatherid()));
		Member mother = (this.dataProvider.retrieveMemberById(member
				.getMotherid()));
		return new MemberWithImmediateRelations(member, father, mother, null,
				null);
	}

	private void setParents(MemberName memberName, MemberName father,
			MemberName mother) {
		Member member = getFirstMember(memberName);
		Member firstFather = (getFirstMember(father));
		Member firstMother = (getFirstMember(mother));
		if (firstFather != null) {
			member.setFatherid(firstFather.getId());
		}
		if (firstMother != null) {
			member.setMotherid(firstMother.getId());
		}
		dataProvider.updateMember(member);
	}

	private Member getFirstMember(MemberName member) {
		List<Member> members = dataProvider.retrieveMembersByName(member
				.toString());
		if (org.springframework.util.CollectionUtils.isEmpty(members)) {
			return null;
		}
		return members.get(0);
	}

	@Test
	public void testDeleteMember() {
		// delete member
		dataProvider.deleteMember(this.getFirstMember(MemberName.DELETE)
				.getId());

		// ensure member deleted
		assertNull(getFirstMember(MemberName.DELETE));
	}

	@Test
	public void testRetrieveMemberDescendants() {
		String memberName = MemberName.DILIP.toString();
		List<String> firstGenerationFirstNames = Arrays.asList(new String[] { MemberName.JINISHA.toString(),
				MemberName.BHAVESH.toString() });
		List<String> secondGenerationFirstNames = Arrays.asList(new String[] { MemberName.RITIKA.toString(),
				MemberName.RINA.toString(), MemberName.NEHA.toString() });
		Set<IsTreeMember>members = dataProvider.retrieveAllMemberTree();
		HasDescendants descendants = new HierarchicalDataCreator(members, members).retrieveDescendants(dataProvider.retrieveMembersByName(MemberName.DILIP.toString()).get(0).getId());
		assertFalse(descendants == null);
		assertEquals(descendants.getMember().getFirstname(), memberName);
		// test children
		List<HasDescendants> firstGeneration = descendants.getChildren();
		assertTrue(firstGeneration.size() == 2);
		for (HasDescendants child : firstGeneration) {
			assertTrue(firstGenerationFirstNames.contains(child.getMember().getFirstname()));
		}
		// test grand children
		for (HasDescendants child : firstGeneration) {
			if (child.getMember().getFirstname().equalsIgnoreCase(MemberName.JINISHA.toString())) {
				List<HasDescendants> secondGeneration = child.getChildren();
				assertEquals(3, secondGeneration.size());
				for (HasDescendants grandChild : secondGeneration) {
					assertTrue(secondGenerationFirstNames.contains(grandChild.getMember().getFirstname()));
				}
			}
		}
	}

	@Test
	public void testRetrieveMemberAncestors() {
		String memberName = MemberName.RITIKA.toString();

		Set<IsTreeMember>members = dataProvider.retrieveAllMemberTree();
		HasAncestors ancestors = new HierarchicalDataCreator(members, members).retrieveAncestor(dataProvider.retrieveMembersByName(MemberName.RITIKA.toString()).get(0).getId());
		
		assertFalse(ancestors == null);
		assertEquals(ancestors.getMember().getFirstname(), memberName);

		// parents
		HasAncestors mother = ancestors.getMother();
		HasAncestors father = ancestors.getFather();
		assertEquals(father.getMember().getFirstname(),
				MemberName.KETAN.toString());
		assertEquals(mother.getMember().getFirstname(),
				MemberName.JINISHA.toString());

		// maternal grand parents
		HasAncestors maternalGrandMother = mother.getMother();
		HasAncestors maternalGrandFather = mother.getFather();
		assertEquals(maternalGrandMother.getMember().getFirstname(),
				MemberName.MRUDULA.toString());
		assertEquals(maternalGrandFather.getMember().getFirstname(),
				MemberName.DILIP.toString());

		// maternal - maternal - parents
		HasAncestors maternalMaternalGrandMother = maternalGrandMother
				.getMother();
		HasAncestors maternalMaternalGrandFather = maternalGrandMother
				.getFather();
		assertEquals(maternalMaternalGrandMother.getMember().getFirstname(),
				MemberName.VIMLA.toString());
		assertEquals(maternalMaternalGrandFather.getMember().getFirstname(),
				MemberName.MANJI.toString());

		// maternal - paternal - parents
		HasAncestors maternalPaternalGrandMother = maternalGrandFather
				.getMother();
		HasAncestors maternalPaternalGrandFather = maternalGrandFather
				.getFather();
		assertEquals(maternalPaternalGrandMother.getMember().getFirstname(),
				MemberName.ZAVER.toString());
		assertEquals(maternalPaternalGrandFather.getMember().getFirstname(),
				MemberName.NARANJI.toString());
	}

}

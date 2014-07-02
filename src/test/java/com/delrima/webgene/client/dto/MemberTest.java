//package com.delrima.webgene.client.dto;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.List;
//
//import org.junit.Assert;
//import org.junit.Test;
//
//import com.delrima.webgene.client.model.MemberWithRelations;
//
//public class MemberTest {
//
//	@Test
//	public void test_Compare() {
//		List<MemberWithRelations> children = new ArrayList<MemberWithRelations>();
//		children.add(createMemberWithBirthDate(1981, 6, 16 ));
//		children.add(createMemberWithBirthDate(1976, 8, 6 ));
//		children.add(createMemberWithBirthDate(1981, 0, 30 ));
//		
//		System.out.println(children);
//		System.out.println("-----------------------------------------");
//		Collections.sort(children);
//		System.out.println(children);
//		System.out.println("-----------------------------------------");
//		
//		Assert.assertTrue(children.get(0).getMember().getDob().getTime() < children.get(1).getMember().getDob().getTime());
//		Assert.assertTrue(children.get(1).getMember().getDob().getTime() < children.get(2).getMember().getDob().getTime());
//	}
//	
//	MemberWithRelations createMemberWithBirthDate(int year, int month, int date) {
//		Member member = new Member();
//		member.setDob(new Date(new GregorianCalendar(year, month, date).getTimeInMillis()));
//		MemberWithRelations m = new MemberWithRelations(member, null, null, null, null );
//		return m;
//	}
//
// }

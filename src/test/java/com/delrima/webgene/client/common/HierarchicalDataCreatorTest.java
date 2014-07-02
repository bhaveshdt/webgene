package com.delrima.webgene.client.common;

import java.util.HashSet;

import org.junit.Test;

import com.delrima.webgene.client.dto.Member;


public class HierarchicalDataCreatorTest {

    @Test
    public void test() {
        HashSet<Member> members = new HashSet<Member>();
        for (int i=1; i<=10; i++) {
            members.add(createMember(i, "initial_first_"));
        }
        System.out.println(members.contains(createMember(0, "something")));
        members.add(createMember(0, "changed_first_"));
        System.out.println(members.iterator().next().getFirstname());
        System.out.println(members.size());
        
    }
    
    Member createMember(long id, String firstName) {
        Member m = new Member();
        m.setId(id);
        m.setFirstname(firstName + id);
        return m;
    }

}

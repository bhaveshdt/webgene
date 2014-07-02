package com.delrima.webgene.client.comparator;

import java.util.Comparator;
import java.util.Date;

import com.delrima.webgene.client.model.HasTreeMember;

public class MemberAgeComparator implements Comparator<HasTreeMember> {

    @Override
    public int compare(HasTreeMember o1, HasTreeMember o2) {
        Date d1 = o1.getMember().getDob();
        Date d2 = o2.getMember().getDob();

        // when dates not available
        if (d1 == null && d2 == null) {
            if (d1 == null || d2 == null) {
                return o1.getMember().getFirstname().compareToIgnoreCase(o2.getMember().getFirstname());
            }
        }

        // at least one date available
        if (d1 == null) {
            return 1;
        }
        if (d2 == null) {
            return -1;
        }

        return d1.compareTo(d2);
    }

}

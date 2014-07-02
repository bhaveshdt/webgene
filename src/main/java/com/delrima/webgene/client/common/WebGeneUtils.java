package com.delrima.webgene.client.common;

import com.delrima.webgene.arch.client.utils.StringUtils;
import com.delrima.webgene.client.dto.Member;
import com.delrima.webgene.client.enums.GenderIdentifier;

public class WebGeneUtils {

    public static boolean isMemberMale(Member member) {
        if (member != null) {
            return StringUtils.equalsIgnoreCase(member.getGender(), GenderIdentifier.M.toString());
        }
        return false;
    }
}

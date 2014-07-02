package com.delrima.webgene.server.services;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.dao.DataAccessException;

import com.delrima.webgene.client.action.RetrieveMemberTreeAction;
import com.delrima.webgene.client.action.SingleActionHandler;
import com.delrima.webgene.client.common.ActionException;
import com.delrima.webgene.client.dao.MemberTreeDataProvider;
import com.delrima.webgene.client.model.IsTreeMember;
import com.delrima.webgene.client.result.RetrieveMemberTreeResult;

/**
 * Retrieves all members
 */
public class RetrieveMemberTreeActionHandler extends AbstractWebgeneActionHandler implements SingleActionHandler<RetrieveMemberTreeAction, RetrieveMemberTreeResult> {

    @Inject
    public RetrieveMemberTreeActionHandler(MemberTreeDataProvider dataProvider) {
        super(dataProvider);
    }

    public RetrieveMemberTreeResult execute(RetrieveMemberTreeAction action) throws ActionException {

        Set<IsTreeMember> members = null;
        try {
            members = new HashSet<IsTreeMember>();
            if (!ArrayUtils.isEmpty(action.getMemberIds())) {
                for (Long id : action.getMemberIds()) {
                    members.add(this.getDataProvider().retrieveMemberById(id));
                }
            } else {
                members = this.getDataProvider().retrieveAllMemberTree();
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return new RetrieveMemberTreeResult(members);
    }

    public Class<RetrieveMemberTreeAction> getActionType() {
        return RetrieveMemberTreeAction.class;
    }

}
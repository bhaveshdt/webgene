package com.delrima.webgene.server.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.delrima.webgene.client.action.MemberLookupAction;
import com.delrima.webgene.client.action.SingleActionHandler;
import com.delrima.webgene.client.common.ActionException;
import com.delrima.webgene.client.dao.MemberTreeDataProvider;
import com.delrima.webgene.client.dto.Member;
import com.delrima.webgene.client.model.IsTreeMember;
import com.delrima.webgene.client.result.MemberLookupResult;

/**
 * @author Bhavesh
 * 
 */
public class MemberLookupActionHandler extends AbstractWebgeneActionHandler implements SingleActionHandler<MemberLookupAction, MemberLookupResult> {

    private final static Logger logger = LoggerFactory.getLogger(MemberLookupActionHandler.class);

    @Inject
    public MemberLookupActionHandler(MemberTreeDataProvider dataProvider) {
        super(dataProvider);
    }

    public MemberLookupResult execute(MemberLookupAction action) throws ActionException {

        List<Member> members = new ArrayList<Member>();
        try {
            members = getDataProvider().retrieveMembersByName(action.getMemberLookupQuery());
        } catch (DataAccessException e) {
            logger.error("", e);
        }
        return new MemberLookupResult(new HashSet<IsTreeMember>(members));
    }

    public Class<MemberLookupAction> getActionType() {
        return MemberLookupAction.class;
    }

}
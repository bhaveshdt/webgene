package com.delrima.webgene.server.services;

import com.delrima.webgene.client.dao.MemberTreeDataProvider;
import com.delrima.webgene.client.dto.Member;

/**
 * @author Bhavesh
 * @param <A>
 * @param <R>
 */
public abstract class AbstractWebgeneActionHandler {

	private final MemberTreeDataProvider dataProvider;

	public AbstractWebgeneActionHandler(MemberTreeDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public MemberTreeDataProvider getDataProvider() {
		return dataProvider;
	}

	protected final Member retrieveMemberById(Long id) {
		return this.getDataProvider().retrieveMemberById(id);
	}

}

package com.delrima.webgene.client.dao;

import java.util.Set;

public interface MemberRelationDAO<T> {
	Set<T> retrieveChildren(Long id);

	T retrieveMemberById(Long id);
}

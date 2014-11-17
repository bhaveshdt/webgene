package com.delrima.webgene.client.common;

public interface Command<T> {

	void execute(T response);

}

package com.delrima.webgene.client.common;

/**
 * @author Bhavesh
 * 
 */
@SuppressWarnings("serial")
public class ActionException extends Exception {

	public ActionException() {
		super();
	}

	public ActionException(Throwable throwable) {
		super(throwable);
	}

	public ActionException(String message) {
		super(message);
	}

}
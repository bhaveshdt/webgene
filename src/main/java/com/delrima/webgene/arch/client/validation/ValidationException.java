package com.delrima.webgene.arch.client.validation;

/**
 * <p>
 * Exception thrown when validation fails
 * </p>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since Sep 22, 2008
 * 
 */
@SuppressWarnings("serial")
public class ValidationException extends Exception {

	/** Creates a new instance of ValidationException */
	public ValidationException(String message) {
		super(message);
	}

}

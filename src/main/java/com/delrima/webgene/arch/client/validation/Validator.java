package com.delrima.webgene.arch.client.validation;

/**
 * <p>
 * Base interface used for client-side field validation
 * </p>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since Sep 22, 2008
 * 
 */
public interface Validator {

	public boolean isValid(Object value);
}

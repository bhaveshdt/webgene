package com.delrima.webgene.arch.client.validation.validators;

import com.delrima.webgene.arch.client.validation.Validator;

/**
 * <p>
 * Validate a double value
 * </p>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since Sep 22, 2008
 * 
 */
public class DoubleValidator implements Validator {

	public static final DoubleValidator INSTANCE = new DoubleValidator();

	/** Creates a new instance of DoubleValidator */
	private DoubleValidator() {
	}

	public boolean isValid(Object value) {
		if (value == null) {
			return false;
		}
		try {
			new Double(value.toString());
		} catch (NumberFormatException nfe) {
			return false;
		}

		return true;
	}

}

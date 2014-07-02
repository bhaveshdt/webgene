package com.delrima.webgene.arch.client.validation.validators;

import com.delrima.webgene.arch.client.validation.Validator;

/**
 * <p>
 * Validate an integer value
 * </p>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since Sep 22, 2008
 * 
 */
public class IntegerValidator implements Validator {

    public static final IntegerValidator INSTANCE = new IntegerValidator();

    /** Creates a new instance of DoubleValidator */
    private IntegerValidator() {
    }

    public boolean isValid(Object value) {
        if (value == null) {
            return false;
        }
        try {
            new Integer(value.toString());
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

}

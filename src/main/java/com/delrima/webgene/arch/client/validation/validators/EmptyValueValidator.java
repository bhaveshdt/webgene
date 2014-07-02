package com.delrima.webgene.arch.client.validation.validators;

import com.delrima.webgene.arch.client.validation.Validator;

/**
 * <p>
 * Validate an empty value
 * </p>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since Sep 22, 2008
 * 
 */
public class EmptyValueValidator implements Validator {

    public static final EmptyValueValidator INSTANCE = new EmptyValueValidator();

    /** Creates a new instance of DoubleValidator */
    private EmptyValueValidator() {
    }

    public boolean isValid(Object value) {
        if (value == null || value.toString().equals("")) {
            return false;
        }
        return true;
    }

}

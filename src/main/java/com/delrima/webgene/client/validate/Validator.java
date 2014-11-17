package com.delrima.webgene.client.validate;

import java.util.List;

public interface Validator {

	boolean validate();

	List<String> getValidationResult();

}

package com.delrima.webgene.client.validate;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractValidator {

    private List<String> validationResult;

    public List<String> getValidationResult() {
        return validationResult;
    }

    public void addValidationResult(String result) {
        if (validationResult == null) {
            validationResult = new ArrayList<String>();
        }
        validationResult.add(result);
    }

}

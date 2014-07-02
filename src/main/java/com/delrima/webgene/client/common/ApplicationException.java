package com.delrima.webgene.client.common;

/**
 * @author Bhavesh
 * 
 */
@SuppressWarnings("serial")
public class ApplicationException extends Exception {

    public ApplicationException() {
        super();
    }

    public ApplicationException(Throwable throwable) {
        super(throwable);
    }

    public ApplicationException(String message) {
        super(message);
    }

}
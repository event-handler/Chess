package com.bbdr.chess;

public class BaileysFaultException extends java.lang.Exception {
    
    /**
     * Constructor for BaileysFaultException.
     * Declares that something is Bailey's fault.
     * @param message the error message.
     */
    public BaileysFaultException(String message) {
        super(message);
    }
    
    /**
     * No-args constructor for BaileysFaultException.
     * Declares that something is Bailey's fault.
     */
    public BaileysFaultException() {
        super();
    }
}

package com.mutantsparade.mutantdetected.errors;

public class InvalidDnaCodeException extends RuntimeException {

    public InvalidDnaCodeException(String s) {
        super(s);
    }

    /**
     * This method was overwrited to prevent printStackTrace
     * to the standad output, when this exception is thrown.
     *
     * @return
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}

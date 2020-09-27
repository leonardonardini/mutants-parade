package com.mutantsparade.mutantdetected.errors;

public class InvalidDnaCodeException extends Exception {

    public InvalidDnaCodeException(String s) {
        super(s);
    }

    /**
     * This method was overwrited to prevent printStackTrace
     * to the standad output, when this exception is thrown.
     * There is no need to log this exception.
     *
     * @return this exception
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}

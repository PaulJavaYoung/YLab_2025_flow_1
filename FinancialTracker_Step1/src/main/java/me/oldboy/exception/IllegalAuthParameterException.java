package me.oldboy.exception;

public class IllegalAuthParameterException extends RuntimeException {
    public IllegalAuthParameterException(String msg) {
        super(msg);
    }
}

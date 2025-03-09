package me.oldboy.exception;

public class NotUniquePasswordException extends RuntimeException {
    public NotUniquePasswordException(String msg) {
        super(msg);
    }
}

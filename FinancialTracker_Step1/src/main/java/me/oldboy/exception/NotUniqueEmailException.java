package me.oldboy.exception;

public class NotUniqueEmailException extends RuntimeException {
    public NotUniqueEmailException(String msg) {
        super(msg);
    }
}

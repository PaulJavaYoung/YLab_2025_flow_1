package me.oldboy.exception;

public class WrongEnteredDataFormatException extends RuntimeException {
    public WrongEnteredDataFormatException(String msg) {
        super(msg);
    }
}

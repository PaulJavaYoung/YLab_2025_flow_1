package me.oldboy.exception;

public class UnexpectedEnteredData extends RuntimeException {
    public UnexpectedEnteredData(String msg) {
        super(msg);
    }
}

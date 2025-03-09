package me.oldboy.exception;

public class TransactionServiceException extends RuntimeException {
    public TransactionServiceException(String msg) {
        super(msg);
    }
}

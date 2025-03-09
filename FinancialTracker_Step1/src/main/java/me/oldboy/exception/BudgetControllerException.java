package me.oldboy.exception;

public class BudgetControllerException extends RuntimeException {
    public BudgetControllerException(String msg) {
        super(msg);
    }
}

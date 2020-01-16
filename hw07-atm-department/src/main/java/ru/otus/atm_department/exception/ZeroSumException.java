package ru.otus.atm_department.exception;

/**
 * Thrown out if the requested amount is zero
 */
public class ZeroSumException extends ATMException {

    public ZeroSumException(String message) {
        super(message);
    }
}

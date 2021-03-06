package ru.otus.atm_department.atm.exception;

/**
 * Thrown out if the requested amount is not a multiple of the banknote denominations
 */
public class SumParityException extends ATMException {

    public SumParityException(String message) {
        super(message);
    }
}

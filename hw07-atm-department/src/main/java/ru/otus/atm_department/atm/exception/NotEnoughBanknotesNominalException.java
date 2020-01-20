package ru.otus.atm_department.atm.exception;

/**
 * Thrown out if not enough denominations of banknotes
 */
public class NotEnoughBanknotesNominalException extends ATMException {

    public NotEnoughBanknotesNominalException(String message) {
        super(message);
    }
}

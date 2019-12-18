package ru.otus.hw.exception;

/**
 * Thrown out if the amount of banknotes is insufficient
 */
public class NotEnoughBanknotesSumException extends ATMException {

    public NotEnoughBanknotesSumException(String message) {
        super(message);
    }
}

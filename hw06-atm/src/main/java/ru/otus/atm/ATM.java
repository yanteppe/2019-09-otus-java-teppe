package ru.otus.atm;

import ru.otus.atm.banknote.Ruble;

public interface ATM {

    void acceptBanknotes(Ruble ruble, int amount);

    void getBanknotes(int sum);

    void displayAccountStatus();

    void displayIssuedBanknotes(String issuedSum, String issuedBanknotes);
}

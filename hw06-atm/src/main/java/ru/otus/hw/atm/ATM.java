package ru.otus.hw.atm;

import ru.otus.hw.Ruble;

public interface ATM {

    void acceptBanknotes(Ruble ruble, int amount);

    void getBanknotes(int sum);

    void displayAccountStatus();

    void displayIssuedBanknotes(String issuedSum, String issuedBanknotes);
}

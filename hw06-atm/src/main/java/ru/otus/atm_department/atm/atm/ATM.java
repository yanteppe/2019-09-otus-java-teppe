package ru.otus.atm_department.atm.atm;

import ru.otus.atm_department.atm.banknote.Ruble;

public interface ATM {

    void acceptBanknotes(Ruble ruble, int amount);

    void getBanknotes(int sum);

    void displayAccountStatus();

    void displayIssuedBanknotes(String issuedSum, String issuedBanknotes);
}

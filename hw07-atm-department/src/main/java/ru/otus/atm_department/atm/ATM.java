package ru.otus.atm_department.atm;

import ru.otus.atm_department.atm.banknote.Ruble;

public interface ATM {

    void acceptBanknotes(Ruble ruble, int amount);

    void getBanknotes(int sum);

    int getAtmBalance();

    void loadState(ATMImpl.ATMMemento atmMemento);

    void displayAccountStatus();

    void displayIssuedBanknotes(String issuedSum, String issuedBanknotes);
}

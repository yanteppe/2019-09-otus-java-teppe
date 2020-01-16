package ru.otus.atm_department.atm;

import ru.otus.atm_department.banknote.Ruble;

import java.util.List;
import java.util.SortedMap;

public interface BanknoteContainer {

    List getBanknotesForIssue(int sum);

    int getIssuedSum();

    void foldBanknotes(Ruble ruble, int amount);

    void selectBanknotes(int sum);

    int getEmptyCellAmount();

    int getBanknoteContainerTotalSum();

    SortedMap<Ruble, Integer> getBanknotesContainer();
}

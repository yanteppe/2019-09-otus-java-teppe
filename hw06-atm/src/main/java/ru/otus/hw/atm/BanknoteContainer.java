package ru.otus.hw.atm;

import ru.otus.hw.banknote.Ruble;

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

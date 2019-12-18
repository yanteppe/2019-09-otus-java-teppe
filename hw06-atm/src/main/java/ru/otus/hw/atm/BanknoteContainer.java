package ru.otus.hw.atm;

import ru.otus.hw.Ruble;

import java.util.List;
import java.util.Map;

public interface BanknoteContainer {

    List getBanknotesForIssue(int sum);

    int getIssuedSum();

    void foldBanknotes(Ruble ruble, int amount);

    void selectBanknotes(int sum);

    int getEmptyCellAmount();

    int getBanknoteContainerTotalSum();

    Map<Integer, Integer> getBanknotesContainer();
}

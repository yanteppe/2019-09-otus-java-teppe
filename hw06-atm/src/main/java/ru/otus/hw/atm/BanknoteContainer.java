package ru.otus.hw.atm;

import ru.otus.hw.banknotes.Banknote;
import ru.otus.hw.banknotes.Dollar;
import ru.otus.hw.banknotes.Ruble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.otus.hw.banknotes.Dollar.*;
import static ru.otus.hw.banknotes.Ruble.*;

/**
 * ATM banknote storage container
 */
class BanknoteContainer {
    private int rubCounter50;
    private int rubCounter100;
    private int rubCounter500;
    private int rubCounter1000;
    private int rubCounter5000;
    private int dollarCounter5;
    private int dollarCounter10;
    private int dollarCounter20;
    private int dollarCounter50;
    private int dollarCounter100;

    /**
     * Get banknotes by sum
     *
     * @param sum desired sum
     */
    List getOutBanknotes(Banknote banknote, int sum) {
        List<Banknote> banknotes = new ArrayList<>();
        // TODO: реализовать логику выдачи банкнот
        return banknotes;
    }

    /**
     * Fold banknotes in container by banknote type and nominal
     *
     * @param banknote banknote
     */
    void putInContainer(Banknote banknote) {
        if (banknote instanceof Ruble) {
            switch (banknote.getNominal()) {
                case 50: ++rubCounter50; break;
                case 100: ++rubCounter100; break;
                case 500: ++rubCounter500; break;
                case 1000: ++rubCounter1000; break;
                case 5000:++rubCounter5000; break;
            }
        } else if (banknote instanceof Dollar) {
            switch (banknote.getNominal()) {
                case 5: ++dollarCounter5; break;
                case 10: ++dollarCounter10; break;
                case 20: ++dollarCounter20; break;
                case 50: ++dollarCounter50; break;
                case 100: ++dollarCounter100; break;
            }
        }
    }

    protected int getRubleTotalData() {
        return rubCounter50 * RUB_50.getNominal()
                + rubCounter100 * RUB_100.getNominal()
                + rubCounter500 * RUB_500.getNominal()
                + rubCounter1000 * RUB_1000.getNominal()
                + rubCounter5000 * RUB_5000.getNominal();
    }

    protected int getDollarTotalData() {
        return dollarCounter5 * DOLLAR_5.getNominal()
                + dollarCounter10 * DOLLAR_10.getNominal()
                + dollarCounter20 * DOLLAR_20.getNominal()
                + dollarCounter50 * DOLLAR_50.getNominal()
                + dollarCounter100 * DOLLAR_100.getNominal();
    }

    protected Map getGeneralContainerData() {
        Map<String, Integer> data = new HashMap<>();
        data.put("rubles", getRubleTotalData());
        data.put("dollars", getDollarTotalData());
        return data;
    }
}

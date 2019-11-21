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
//    private SortedMap<Ruble2, List> rublesCase = new TreeMap<>();
//    private SortedMap<Dollar2, List> dollarsCase = new TreeMap<>();
//    private List<ru.otus.hw.garbage.Ruble2> rubNominal50 = new ArrayList<>();
//    private List<ru.otus.hw.garbage.Ruble2> rubNominal100 = new ArrayList<>();
//    private List<ru.otus.hw.garbage.Ruble2> rubNominal500 = new ArrayList<>();
//    private List<ru.otus.hw.garbage.Ruble2> rubNominal1000 = new ArrayList<>();
//    private List<ru.otus.hw.garbage.Ruble2> rubNominal5000 = new ArrayList<>();
//    private List<ru.otus.hw.garbage.Dollar2> dollarNominal5 = new ArrayList<>();
//    private List<ru.otus.hw.garbage.Dollar2> dollarNominal10 = new ArrayList<>();
//    private List<ru.otus.hw.garbage.Dollar2> dollarNominal20 = new ArrayList<>();
//    private List<ru.otus.hw.garbage.Dollar2> dollarNominal50 = new ArrayList<>();
//    private List<ru.otus.hw.garbage.Dollar2> dollarNominal100 = new ArrayList<>();

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
//    private int rubleTotal = 0;
//    private int dollarTotal = 0;

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

//    private int getDollarTotalData() {
//        int dollarTotal = 0;
//        for (DollarNominal nominalKey : dollarsCase.keySet()) {
//            switch (nominalKey) {
//                case DOLLAR_5:
//                    dollarTotal = dollarTotal + dollarsCase.get(nominalKey).size() * DOLLAR_5.getNominal();
//                    break;
//                case DOLLAR_10:
//                    dollarTotal = dollarTotal + dollarsCase.get(nominalKey).size() * DOLLAR_10.getNominal();
//                    break;
//                case DOLLAR_20:
//                    dollarTotal = dollarTotal + dollarsCase.get(nominalKey).size() * DOLLAR_20.getNominal();
//                    break;
//                case DOLLAR_50:
//                    dollarTotal = dollarTotal + dollarsCase.get(nominalKey).size() * DOLLAR_50.getNominal();
//                    break;
//                case DOLLAR_100:
//                    dollarTotal = dollarTotal + dollarsCase.get(nominalKey).size() * DOLLAR_100.getNominal();
//                    break;
//            }
//        }
//        return dollarTotal;
//    }

//    Map getGeneralContainerData() {
//        for (Ruble2 nominalKey : rublesCase.keySet()) {
//            switch (nominalKey) {
//                case RUB_50:
//                    rubleTotal = rubleTotal + rublesCase.get(nominalKey).size() * RUB_50.getNominal();
//                    break;
//                case RUB_100:
//                    rubleTotal = rubleTotal + rublesCase.get(nominalKey).size() * RUB_100.getNominal();
//                    break;
//                case RUB_500:
//                    rubleTotal = rubleTotal + rublesCase.get(nominalKey).size() * RUB_500.getNominal();
//                    break;
//                case RUB_1000:
//                    rubleTotal = rubleTotal + rublesCase.get(nominalKey).size() * RUB_1000.getNominal();
//                    break;
//                case RUB_5000:
//                    rubleTotal = rubleTotal + rublesCase.get(nominalKey).size() * RUB_5000.getNominal();
//                    break;
//            }
//        }
//        for (Dollar2 nominalKey : dollarsCase.keySet()) {
//            switch (nominalKey) {
//                case DOLLAR_5:
//                    dollarTotal = dollarTotal + dollarsCase.get(nominalKey).size() * DOLLAR_5.getNominal();
//                    break;
//                case DOLLAR_10:
//                    dollarTotal = dollarTotal + dollarsCase.get(nominalKey).size() * DOLLAR_10.getNominal();
//                    break;
//                case DOLLAR_20:
//                    dollarTotal = dollarTotal + dollarsCase.get(nominalKey).size() * DOLLAR_20.getNominal();
//                    break;
//                case DOLLAR_50:
//                    dollarTotal = dollarTotal + dollarsCase.get(nominalKey).size() * DOLLAR_50.getNominal();
//                    break;
//                case DOLLAR_100:
//                    dollarTotal = dollarTotal + dollarsCase.get(nominalKey).size() * DOLLAR_100.getNominal();
//                    break;
//            }
//        }
//        Map<String, Integer> data = new HashMap<>();
//        data.put("rubles", rubleTotal);
//        data.put("dollars", dollarTotal);
//        return data;
//    }
}

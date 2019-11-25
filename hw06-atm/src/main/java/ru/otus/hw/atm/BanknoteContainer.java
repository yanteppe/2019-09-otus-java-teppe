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
    private List<Integer> rublesForIssue = new ArrayList<>();
    private List<Integer> dollarsForIssue = new ArrayList<>();

    /**
     * Get desired banknotes
     *
     * @return List desired banknotes
     */
    List getBanknotesForIssue(String banknote) {
        List banknotes = new ArrayList();
        if (banknote.equals("ruble")) banknotes = rublesForIssue;
        if (banknote.equals("dollar")) banknotes = dollarsForIssue;
        return banknotes;
    }

    /**
     * Get sum issued banknotes
     *
     * @return sum of banknotes for issue (int)
     */
    int getSumIssuedBanknotes() {
        int sum = 0;
        for (Integer banknote : rublesForIssue) {
            sum = sum + banknote;
        }
        return sum;
    }

    /**
     * Collect desired banknotes in banknote container
     *
     * @param banknote banknote
     * @param sum desired sum
     */
    void collectBanknotes(String banknote, int sum) {
        if (banknote.equals("ruble")) getRubles(sum);
        if (banknote.equals("dollar")) getDollars(sum);
    }

    /**
     * Get rubles by sum
     *
     * @param sum desired sum of rubles
     */
    private void getRubles(int sum) {
        for (int i = 0; i < Ruble.values().length; i++) {
            int nominal = 0;
            if (sum >= RUB_5000.getNominal()) {
                nominal = RUB_5000.getNominal();
                rubCounter5000--;
                rublesForIssue.add(RUB_5000.getNominal());
            }
            if (sum <= RUB_5000.getNominal() && sum >= RUB_1000.getNominal()) {
                nominal = RUB_1000.getNominal();
                rubCounter1000--;
                rublesForIssue.add(RUB_1000.getNominal());
            }
            if (sum <= RUB_1000.getNominal() && sum >= RUB_500.getNominal()) {
                nominal = RUB_500.getNominal();
                rubCounter500--;
                rublesForIssue.add(RUB_500.getNominal());
            }
            if (sum <= RUB_500.getNominal() && sum >= RUB_100.getNominal()) {
                nominal = RUB_100.getNominal();
                rubCounter100--;
                rublesForIssue.add(RUB_100.getNominal());
            }
            if (sum <= RUB_100.getNominal() && sum >= RUB_50.getNominal()) {
                nominal = RUB_50.getNominal();
                rubCounter50--;
                rublesForIssue.add(RUB_50.getNominal());
            }
            sum = sum - nominal;
            if (sum == 0) {
                break;
            } else {
                getRubles(sum);
            }
            break;
        }
    }

    /**
     * Get dollars by sum
     *
     * @param sum desired sum of dollars
     */
    private void getDollars(int sum) {
        for (int i = 0; i < Dollar.values().length; i++) {
            int nominal = 0;
            if (sum >= DOLLAR_100.getNominal()) {
                nominal = DOLLAR_100.getNominal();
                dollarsForIssue.add(selectBanknote("dollar"));
            }
            if (sum <= DOLLAR_100.getNominal() && sum >= DOLLAR_50.getNominal()) {
                nominal = DOLLAR_50.getNominal();
                dollarCounter50--;
                dollarsForIssue.add(DOLLAR_50.getNominal());
            }
            if (sum <= DOLLAR_50.getNominal() && sum >= DOLLAR_20.getNominal()) {
                nominal = DOLLAR_20.getNominal();
                dollarCounter20--;
                dollarsForIssue.add(DOLLAR_20.getNominal());
            }
            if (sum <= DOLLAR_20.getNominal() && sum >= DOLLAR_10.getNominal()) {
                nominal = DOLLAR_10.getNominal();
                dollarCounter10--;
                dollarsForIssue.add(DOLLAR_10.getNominal());
            }
            if (sum <= DOLLAR_10.getNominal() && sum >= DOLLAR_5.getNominal()) {
                nominal = DOLLAR_5.getNominal();
                dollarCounter5--;
                dollarsForIssue.add(DOLLAR_5.getNominal());
            }
            sum = sum - nominal;
            if (sum == 0) {
                break;
            } else {
                getDollars(sum);
            }
            break;
        }
    }

    private int selectBanknote(String banknote) {
        int value = 0;
        if (banknote.equals("dollar")) {
            if (dollarCounter100 == 0 && dollarCounter50 != 0)  {
                value = DOLLAR_50.getNominal();
            } else {
                value = DOLLAR_100.getNominal();
            }

            if (dollarCounter50 == 0 && dollarCounter20 != 0) {
                value = DOLLAR_20.getNominal();
            } else value = DOLLAR_50.getNominal();

            if (dollarCounter20 == 0 && dollarCounter10 != 0) {
                value = DOLLAR_10.getNominal();
            } else value = DOLLAR_20.getNominal();

            if (dollarCounter10 == 0 && dollarCounter5 != 0) {
                value = DOLLAR_5.getNominal();
            } else value = DOLLAR_10.getNominal();

            if (dollarCounter5 == 0) {
                value = 0;
            }
        }
     return value;
    }

//    private void getDollars(int sum) {
//        for (int i = 0; i < Dollar.values().length; i++) {
//            int nominal = 0;
//            if (sum >= DOLLAR_100.getNominal()) {
//                nominal = DOLLAR_100.getNominal();
//                dollarCounter100--;
//                dollarsForIssue.add(DOLLAR_100.getNominal());
//            }
//            if (sum <= DOLLAR_100.getNominal() && sum >= DOLLAR_50.getNominal()) {
//                nominal = DOLLAR_50.getNominal();
//                dollarCounter50--;
//                dollarsForIssue.add(DOLLAR_50.getNominal());
//            }
//            if (sum <= DOLLAR_50.getNominal() && sum >= DOLLAR_20.getNominal()) {
//                nominal = DOLLAR_20.getNominal();
//                dollarCounter20--;
//                dollarsForIssue.add(DOLLAR_20.getNominal());
//            }
//            if (sum <= DOLLAR_20.getNominal() && sum >= DOLLAR_10.getNominal()) {
//                nominal = DOLLAR_10.getNominal();
//                dollarCounter10--;
//                dollarsForIssue.add(DOLLAR_10.getNominal());
//            }
//            if (sum <= DOLLAR_10.getNominal() && sum >= DOLLAR_5.getNominal()) {
//                nominal = DOLLAR_5.getNominal();
//                dollarCounter5--;
//                dollarsForIssue.add(DOLLAR_5.getNominal());
//            }
//            sum = sum - nominal;
//            if (sum == 0) {
//                break;
//            } else {
//                getDollars(sum);
//            }
//            break;
//        }
//    }

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
                case 5000: ++rubCounter5000; break;
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

    int getRubleTotalData() {
        return rubCounter50 * RUB_50.getNominal()
                + rubCounter100 * RUB_100.getNominal()
                + rubCounter500 * RUB_500.getNominal()
                + rubCounter1000 * RUB_1000.getNominal()
                + rubCounter5000 * RUB_5000.getNominal();
    }

    int getDollarTotalData() {
        return dollarCounter5 * DOLLAR_5.getNominal()
                + dollarCounter10 * DOLLAR_10.getNominal()
                + dollarCounter20 * DOLLAR_20.getNominal()
                + dollarCounter50 * DOLLAR_50.getNominal()
                + dollarCounter100 * DOLLAR_100.getNominal();
    }

    Map getGeneralContainerData() {
        Map<String, Integer> data = new HashMap<>();
        data.put("rubles", getRubleTotalData());
        data.put("dollars", getDollarTotalData());
        return data;
    }
}

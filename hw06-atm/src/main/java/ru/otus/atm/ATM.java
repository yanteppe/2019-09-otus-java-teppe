package ru.otus.atm;

import java.util.HashMap;
import java.util.Map;

/**
 * ATM emulator class
 */
public class ATM {
    private BanknoteContainer banknoteContainer;

    ATM() {
        banknoteContainer = new BanknoteContainer();
    }

    ATM acceptBanknote(Banknote banknote) {
        checkBanknoteNominal(banknote.getNominalValue());
        banknoteContainer.foldBanknotes(banknote);
        return this;
    }

    /**
     * Get banknotes by type and amount
     *
     * @param banknoteType monetary unit (dollar or ruble)
     * @param amount       desired banknote amount
     * @return ATM
     */
    public ATM getBanknotes(String banknoteType, int amount) {
        checkBanknoteNominal(amount);
        // TODO продолжить - реализовать
        return this;
    }

    private ATM checkBanknoteNominal(int nominal) {
        if (nominal == 0) System.out.println("Номинал банкноты не может быть равен '0'");
        return this;
    }

    /**
     * Display account status
     *
     * @return ATM
     */
    public ATM printDisplayAccountStatus() {
        banknoteContainer.getGeneralContainerData();
        return this;
    }

    /**
     * ATM banknote storage container
     */
    private class BanknoteContainer {
        private Map<String, Integer> rubles = new HashMap<>();
        private Map<String, Integer> dollars = new HashMap<>();
        private int rubleCountNominal5 = 0;
        private int rubleCountNominal10 = 0;
        private int rubleCountNominal50 = 0;
        private int rubleCountNominal100 = 0;
        private int dollarCountNominal5;
        private int dollarCountNominal10;
        private int dollarCountNominal50;
        private int dollarCountNominal100;
        private int rubleTotalAmount = 0;
        private int dollarTotalAmount = 0;

        BanknoteContainer() {
            rubles.put("ruble 5", 0);
            rubles.put("ruble 10", 0);
            rubles.put("ruble 50", 0);
            rubles.put("ruble 100", 0);
            dollars.put("dollar 5", 0);
            dollars.put("dollar 10", 0);
            dollars.put("dollar 50", 0);
            dollars.put("dollar 100", 0);
        }

        /**
         * Fold banknotes in container
         *
         * @param banknote banknote
         * @return BanknoteContainer
         */
        BanknoteContainer foldBanknotes(Banknote banknote) {
            if (banknote.getBanknoteType().equals("Ruble")) {
                if (banknote.getNominalValue() == 5) {
                    rubles.put("ruble 5", ++rubleCountNominal5);
                }
                if (banknote.getNominalValue() == 10) {
                    rubles.put("ruble 10", ++rubleCountNominal10);
                }
                if (banknote.getNominalValue() == 50) {
                    rubles.put("ruble 50", ++rubleCountNominal50);
                }
                if (banknote.getNominalValue() == 100) {
                    rubles.put("ruble 100", ++rubleCountNominal100);
                }
            } else if (banknote.getBanknoteType().equals("Dollar")) {
                if (banknote.getNominalValue() == 5) {
                    dollars.put("dollar 5", ++dollarCountNominal5);
                }
                if (banknote.getNominalValue() == 10) {
                    dollars.put("dollar 10", ++dollarCountNominal10);
                }
                if (banknote.getNominalValue() == 50) {
                    dollars.put("dollar 50", ++dollarCountNominal50);
                }
                if (banknote.getNominalValue() == 100) {
                    dollars.put("dollar 100", ++dollarCountNominal100);
                }
            }
            return this;
        }

        public void getGeneralContainerData() {
//            rubleTotalAmount = (rubleCountNominal5 * 5)
//                    + (rubleCountNominal10 * 10)
//                    + (rubleCountNominal50 * 50)
//                    + (rubleCountNominal100 * 100);
            dollarTotalAmount = (dollarCountNominal5 * 5)
                    + (dollarCountNominal10 * 10)
                    + (dollarCountNominal50 * 50)
                    + (dollarCountNominal100 * 100);
            for (String key : rubles.keySet()) {
                if (key.equals("ruble 5")) {
                    rubleTotalAmount = rubleTotalAmount + rubles.get(key) * 5;
                }
                if (key.equals("ruble 10")) {
                    rubleTotalAmount = rubleTotalAmount + rubles.get(key) * 10;
                }
                if (key.equals("ruble 50")) {
                    rubleTotalAmount = rubleTotalAmount + rubles.get(key) * 50;
                }
                if (key.equals("ruble 100")) {
                    rubleTotalAmount = rubleTotalAmount + rubles.get(key) * 100;
                }
            }
//            for (String key : dollars.keySet()) {
//                dollarTotal = dollarTotal + dollars.get(key);
//            }
            System.out.println("Rubles: " + rubleTotalAmount);
            System.out.println("Dollars: " + dollarTotalAmount);
            System.out.println("Rubles banknote counter: " + rubles.toString());
            System.out.println("Dollars banknote counter: " + dollars.toString());
        }
    }
}
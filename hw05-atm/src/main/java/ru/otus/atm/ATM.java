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
        banknoteContainer.getContainerStatus();
        return this;
    }

    /**
     * ATM banknote storage container
     */
    private class BanknoteContainer {
        private Map<String, Integer> rubles = new HashMap<>();
        private Map<String, Integer> dollars = new HashMap<>();

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
                    rubles.put("ruble 5", banknote.getNominalValue());
                }
                if (banknote.getNominalValue() == 10) {
                    rubles.put("ruble 10", banknote.getNominalValue());
                }
                if (banknote.getNominalValue() == 50) {
                    rubles.put("ruble 50", banknote.getNominalValue());
                }
                if (banknote.getNominalValue() == 100) {
                    rubles.put("ruble 100", banknote.getNominalValue());
                }
            } else if (banknote.getBanknoteType().equals("Dollar")) {
                if (banknote.getNominalValue() == 5) {
                    dollars.put("dollar 5", banknote.getNominalValue());
                }
                if (banknote.getNominalValue() == 10) {
                    dollars.put("dollar 10", banknote.getNominalValue());
                }
                if (banknote.getNominalValue() == 50) {
                    dollars.put("dollar 50", banknote.getNominalValue());
                }
                if (banknote.getNominalValue() == 100) {
                    dollars.put("dollar 100", banknote.getNominalValue());
                }
            }
            return this;
        }

        public void getContainerStatus() {
            int rubleStatus = 0;
            int dollarStatus = 0;
            for (String key : rubles.keySet()) {
                rubleStatus = rubleStatus + rubles.get(key);
            }
            for (String key : dollars.keySet()) {
                dollarStatus = dollarStatus + dollars.get(key);
            }
            System.out.println("Rubles: " + rubleStatus);
            System.out.println("Dollars: " + dollarStatus);
        }
    }
}
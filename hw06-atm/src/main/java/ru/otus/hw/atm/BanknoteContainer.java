package ru.otus.hw.atm;

import ru.otus.hw.Ruble;
import ru.otus.hw.exception.NotEnoughBanknotesNominalException;

import java.util.*;

/**
 * ATM banknote storage container
 */
class BanknoteContainer {
    private SortedMap<Integer, Integer> banknotesContainer = new TreeMap<>(Collections.reverseOrder());
    private List<Integer> banknotesForIssue = new ArrayList<>();
    private int rubCounter50 = 0;
    private int rubCounter100 = 0;
    private int rubCounter200 = 0;
    private int rubCounter500 = 0;
    private int rubCounter1000 = 0;
    private int rubCounter2000 = 0;
    private int rubCounter5000 = 0;

    List<Integer> getBanknotesForIssue(int sum) {
        selectBanknotes(sum);
        return banknotesForIssue;
    }

    int getIssuedSum() {
        int sum = 0;
        for (Integer banknote : banknotesForIssue) {
            sum = sum + banknote;
        }
        return sum;
    }

    void foldBanknotes(Ruble ruble, int amount) {
        banknotesContainer.put(ruble.getNominal(), amount);
    }

    /**
     * Get sum by banknotes
     *
     * @param sum desired sum
     */
    private void selectBanknotes(int sum) {
        for (int i = 0; i < Ruble.getNominals().length; i++) {
            if (sum / Ruble.getNominals()[i] >= 1) {
                int nominal = sum - (sum - Ruble.getNominals()[i]);
                sum = sum - nominal;
                for (int j = 0; j < Ruble.getNominals().length; j++) {
                    if (nominal == Ruble.getNominals()[j]) {
                        Integer banknoteCounter = banknotesContainer.get(Ruble.getNominals()[i]);
                        banknotesContainer.put(Ruble.getNominals()[i], --banknoteCounter);
                        banknotesForIssue.add(Ruble.getNominals()[j]);
                        break;
                    }
                }
            } else {
                continue;
            }
            if (sum == 0) {
                break;
            } else {
                selectBanknotes(sum);
                break;
            }
        }
    }

//    public boolean checkBanknoteCount(int banknote) {
//        boolean result = true;
//        if (banknote == Ruble.RUB_5000.getNominal()) {
//            if (rubCounter5000 == 0) result = false;
////            else {
////                banknotesContainer.put(String.valueOf(banknote), --rubCounter5000);
////            }
//        } else if (banknote == Ruble.RUB_2000.getNominal()) {
//            if (rubCounter2000 == 0) result = false;
////            else {
////                banknotesContainer.put(String.valueOf(banknote), --rubCounter2000);
////            }
//        } else if (banknote == Ruble.RUB_1000.getNominal()) {
//            if (rubCounter1000 == 0) result = false;
////            else {
////                banknotesContainer.put(String.valueOf(banknote), --rubCounter1000);
////            }
//        } else if (banknote == Ruble.RUB_500.getNominal()) {
//            if (rubCounter500 == 0) result = false;
////            else {
////                banknotesContainer.put(String.valueOf(banknote), --rubCounter500);
////            }
//        } else if (banknote == Ruble.RUB_200.getNominal()) {
//            if (rubCounter200 == 0) result = false;
////            else {
////                banknotesContainer.put(String.valueOf(banknote), --rubCounter200);
////            }
//        } else if (banknote == Ruble.RUB_100.getNominal()) {
//            if (rubCounter100 == 0) result = false;
////            else {
////                banknotesContainer.put(String.valueOf(banknote), --rubCounter100);
////            }
//        } else if (banknote == Ruble.RUB_50.getNominal()) {
//            if (rubCounter50 == 0) result = false;
////            else {
////                banknotesContainer.put(String.valueOf(banknote), --rubCounter50);
////            }
//        }
//        return result;
//    }

    private List<Integer> getBanknoteCounters() {
        List<Integer> counters = new ArrayList<>();
        counters.add(rubCounter5000);
        counters.add(rubCounter2000);
        counters.add(rubCounter1000);
        counters.add(rubCounter500);
        counters.add(rubCounter200);
        counters.add(rubCounter100);
        counters.add(rubCounter50);
        return counters;
    }

    /**
     * Display error of not enough banknotesContainer nominal in banknote container
     *
     * @param nominal banknote nominal value
     */
    private void displayNotEnoughBanknotesNominalError(int nominal) {
        throw new NotEnoughBanknotesNominalException(String.format("ОШИБКА: Сумма не может быть выдана, недостаточно банкнот номиналом %s", nominal));
    }

    /**
     * Get total sum of banknotesContainer in banknote container
     *
     * @return total sum of banknotesContainer
     */
    int getBanknoteContainerTotalSum() {
        int banknotesTotal = 0;
        for (Integer key : banknotesContainer.keySet()) {
            for (Ruble nominal : Ruble.values()) {
                if (key == nominal.getNominal()) {
                    banknotesTotal = banknotesTotal + banknotesContainer.get(key) * nominal.getNominal();
                    break;
                }
            }
        }
        return banknotesTotal;
    }

    Map<Integer, Integer> getBanknotesContainer() {
        return banknotesContainer;
    }
}


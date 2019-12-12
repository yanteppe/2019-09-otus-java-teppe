package ru.otus.hw.atm;

import ru.otus.hw.Ruble;

import java.util.*;

/**
 * ATM banknote storage container
 */
class BanknoteContainer {
    private Map<String, Integer> banknotes = new HashMap<>();
    private List<Integer> banknotesForIssue = new ArrayList<>();
    private int rubCounter50 = 0;
    private int rubCounter100 = 0;
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
        switch (ruble) {
            case RUB_50:
                banknotes.put("50", rubCounter50 += amount);
                break;
            case RUB_100:
                banknotes.put("100", rubCounter100 += amount);
                break;
            case RUB_200:
                banknotes.put("200", rubCounter500 += amount);
                break;
            case RUB_500:
                banknotes.put("500", rubCounter500 += amount);
                break;
            case RUB_1000:
                banknotes.put("1000", rubCounter1000 += amount);
                break;
            case RUB_2000:
                banknotes.put("2000", rubCounter1000 += amount);
                break;
            case RUB_5000:
                banknotes.put("5000", rubCounter5000 += amount);
                break;
        }
    }

    /**
     * Get banknotes by sum
     *
     * @param sum desired sum of banknotes
     */
    private void selectBanknotes(int sum) {
        for (String banknoteNominal : banknotes.keySet()) {
            int nominal = 0;
            if (sum >= 5000) {
                nominal = 5000;
                if (rubCounter5000 <= 0) displayNotEnoughBanknotesError(nominal);
                banknotes.put("5000", --rubCounter5000);
                banknotesForIssue.add(nominal);
            }
            if (sum >= 2000 && sum < 5000) {
                nominal = 2000;
                if (rubCounter2000 <= 0) displayNotEnoughBanknotesError(nominal);
                banknotes.put("2000", --rubCounter2000);
                banknotesForIssue.add(nominal);
            }
            if (sum >= 1000 && sum < 2000) {
                nominal = 1000;
                if (rubCounter1000 <= 0) displayNotEnoughBanknotesError(nominal);
                banknotes.put("1000", --rubCounter1000);
                banknotesForIssue.add(nominal);
            }
            if (sum >= 500 && sum < 1000) {
                nominal = 500;
                if (rubCounter500 <= 0) displayNotEnoughBanknotesError(nominal);
                banknotes.put("500", --rubCounter500);
                banknotesForIssue.add(nominal);
            }
            if (sum >= 100 && sum < 500) {
                nominal = 100;
                if (rubCounter100 <= 0) displayNotEnoughBanknotesError(nominal);
                banknotes.put("100", --rubCounter100);
                banknotesForIssue.add(nominal);
            }
            if (sum >= 50 && sum < 100) {
                nominal = 50;
                if (rubCounter50 <= 0) displayNotEnoughBanknotesError(nominal);
                banknotes.put("50", --rubCounter50);
                banknotesForIssue.add(nominal);
            }
            sum = sum - nominal;
            if (sum == 0) {
                break;
            } else {
                selectBanknotes(sum);
            }
            break;
        }
    }

    private void displayNotEnoughBanknotesError(int nominal) {
        throw new RuntimeException(String.format("ОШИБКА: Сумма не может быть выдана, недостаточно банкнот номиналом %s", nominal));
    }

    int getContainerData() {
        int banknotesTotal = 0;
        for (String key : banknotes.keySet()) {
            if (key.equals("50")) {
                banknotesTotal = banknotesTotal + banknotes.get(key) * 50;
            }
            if (key.equals("100")) {
                banknotesTotal = banknotesTotal + banknotes.get(key) * 100;
            }
            if (key.equals("500")) {
                banknotesTotal = banknotesTotal + banknotes.get(key) * 500;
            }
            if (key.equals("1000")) {
                banknotesTotal = banknotesTotal + banknotes.get(key) * 1000;
            }
            if (key.equals("2000")) {
                banknotesTotal = banknotesTotal + banknotes.get(key) * 2000;
            }
            if (key.equals("5000")) {
                banknotesTotal = banknotesTotal + banknotes.get(key) * 5000;
            }
        }
        return banknotesTotal;
    }

    Map<String, Integer> getBanknotes() {
        return banknotes;
    }
}


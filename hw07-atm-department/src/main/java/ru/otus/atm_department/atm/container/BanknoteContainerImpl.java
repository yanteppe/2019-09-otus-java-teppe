package ru.otus.atm_department.atm.container;

import ru.otus.atm_department.atm.banknote.Ruble;

import java.io.Serializable;
import java.util.*;

/**
 * ATMImpl banknote storage container
 */
public class BanknoteContainerImpl implements BanknoteContainer, Serializable {
    private SortedMap<Ruble, Integer> banknotesContainer;
    private List<Integer> banknotesForIssue;

    public BanknoteContainerImpl() {
        this.banknotesContainer = new TreeMap<>(Collections.reverseOrder());
        this.banknotesForIssue = new ArrayList<>();
    }

    private BanknoteContainerImpl(SortedMap<Ruble, Integer> banknotesContainer, List<Integer> banknotesForIssue) {
        this.banknotesContainer = banknotesContainer;
        this.banknotesForIssue = banknotesForIssue;
    }

    /**
     * Constructor for copying object
     *
     * @param container implementation of BanknoteContainer
     */
    BanknoteContainerImpl(BanknoteContainer container) {
        this(container.getBanknotesContainer(), container.getBanknotesForIssue());
    }

    public List<Integer> getBanknotesForIssue() {
        return banknotesForIssue;
    }

    public List<Integer> getSumBanknotesToIssue(int sum) {
        selectBanknotes(sum);
        return banknotesForIssue;
    }

    public int getIssuedSum() {
        int sum = 0;
        for (Integer banknote : banknotesForIssue) {
            sum = sum + banknote;
        }
        return sum;
    }

    /**
     * Fold banknotes in banknote container
     *
     * @param ruble  money unit
     * @param amount banknotes amount
     */
    public void foldBanknotes(Ruble ruble, int amount) {
        banknotesContainer.put(ruble, amount);
    }

    /**
     * Get sum by banknotes
     *
     * @param sum desired sum
     */
    public void selectBanknotes(int sum) {
        for (int i = 0; i < Ruble.values().length; i++) {
            // Определение наибольшего номинала банкноты для требуемой суммы через деление
            if (sum / Ruble.values()[i].getNominal() >= 1) {
                int nominal = sum - (sum - Ruble.values()[i].getNominal());
                for (int j = 0; j < Ruble.values().length; j++) {
                    // Выбор банкноты нужного номинала
                    if (nominal == Ruble.values()[j].getNominal()) {
                        Integer banknoteCounter = banknotesContainer.get(Ruble.values()[j]);
                        // Переход к следующей банкноте если кончились банкноты одного из номиналов
                        if (banknoteCounter == 0) {
                            int emptyCellsAmount = getEmptyCellAmount();
                            i = emptyCellsAmount;
                            j = emptyCellsAmount;
                            banknoteCounter = banknotesContainer.get(Ruble.values()[i]);
                            nominal = sum - (sum - Ruble.values()[i].getNominal());
                        }
                        // Обновление счетчика банкнот определенного номинала в контейнере банкнот
                        banknotesContainer.put(Ruble.values()[i], --banknoteCounter);
                        // Добавить выбранную банкноту для выдачи в отдельный массив
                        banknotesForIssue.add(Ruble.values()[j].getNominal());
                        break;
                    }
                }
                // Вычесть из требуемой суммы выданные банкноты
                if (sum != 0) {
                    sum = sum - nominal;
                }
            } else {
                // Если номинал банкноты не подходит для выдачи суммы возврат в начало цикла
                continue;
            }
            // Если сумма набрана выход из массива и цикла
            if (sum == 0) {
                break;
            } else {
                // Если сумма не набрана рекурсивный вызов
                selectBanknotes(sum);
                break;
            }
        }
    }

    /**
     * Getting empty cells amount in banknote container
     *
     * @return int
     */
    public int getEmptyCellAmount() {
        int emptyCellsAmount = 0;
        for (Integer value : banknotesContainer.values()) {
            if (value == 0) emptyCellsAmount++;
        }
        return emptyCellsAmount;
    }

    /**
     * Get a balance at an ATM
     *
     * @return ATM balance
     */
    public int getBanknoteContainerBalance() {
        int balance = 0;
        for (Ruble key : banknotesContainer.keySet()) {
            for (Ruble nominal : Ruble.values()) {
                if (key.getNominal() == nominal.getNominal()) {
                    balance = balance + banknotesContainer.get(key) * nominal.getNominal();
                    break;
                }
            }
        }
        return balance;
    }

    public SortedMap<Ruble, Integer> getBanknotesContainer() {
        return banknotesContainer;
    }
}


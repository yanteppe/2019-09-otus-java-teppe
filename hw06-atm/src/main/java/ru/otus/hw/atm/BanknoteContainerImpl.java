package ru.otus.hw.atm;

import ru.otus.hw.Ruble;
import ru.otus.hw.exception.NotEnoughBanknotesNominalException;

import java.util.*;

/**
 * ATMImpl banknote storage container
 */
class BanknoteContainerImpl implements BanknoteContainer {
    private SortedMap<Integer, Integer> banknotesContainer = new TreeMap<>(Collections.reverseOrder());
    private List<Integer> banknotesForIssue = new ArrayList<>();

    public List<Integer> getBanknotesForIssue(int sum) {
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
        banknotesContainer.put(ruble.getNominal(), amount);
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
                        Integer banknoteCounter = banknotesContainer.get(Ruble.values()[j].getNominal());
                        // Переход к следующей банкноте если кончились банкноты одного из номиналов
                        if (banknoteCounter == 0) {
                            int emptyCellsAmount = getEmptyCellAmount();
                            i = emptyCellsAmount;
                            j = emptyCellsAmount;
                            banknoteCounter = banknotesContainer.get(Ruble.values()[i].getNominal());
                            nominal = sum - (sum - Ruble.values()[i].getNominal());
                        }
                        // Обновления счетчика банкнот определенного номинала в контейнере банкнот
                        banknotesContainer.put(Ruble.values()[i].getNominal(), --banknoteCounter);
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
                // Если сумма не набрана рекурсивный вызов этого же метода
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
    public int getBanknoteContainerTotalSum() {
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

    public Map<Integer, Integer> getBanknotesContainer() {
        return banknotesContainer;
    }
}


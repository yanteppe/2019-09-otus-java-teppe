package ru.otus.hw.atm;

import ru.otus.hw.banknote.Ruble;
import ru.otus.hw.exception.NotEnoughBanknotesSumException;
import ru.otus.hw.exception.SumParityException;
import ru.otus.hw.exception.ZeroSumException;

import java.util.Arrays;

/**
 * ATMImpl emulator class
 */
public class ATMImpl implements ATM {
    private BanknoteContainer banknoteContainer;

    public ATMImpl() {
        banknoteContainer = new BanknoteContainerImpl();
    }

    /**
     * Accept the required number of banknotes
     *
     * @param ruble  currency unit
     * @param amount number of banknotes accepted
     */
    public void acceptBanknotes(Ruble ruble, int amount) {
        checkDesiredSumOnZero(ruble.getNominal());
        banknoteContainer.foldBanknotes(ruble, amount);
    }

    /**
     * Get desired sum
     *
     * @param sum desired sum
     */
    public void getBanknotes(int sum) {
        checkDesiredSumOnZero(sum);
        checkDesiredSumInBanknoteContainer(sum);
        checkNominal(sum);
        String issuedBanknotes = String.valueOf(banknoteContainer.getBanknotesForIssue(sum));
        String issuedSum = String.valueOf(banknoteContainer.getIssuedSum());
        displayIssuedBanknotes(issuedSum, issuedBanknotes);
    }

    /**
     * Display account status
     */
    public void displayAccountStatus() {
        System.out.println("Account status: " + banknoteContainer.getBanknoteContainerTotalSum() +
                ", banknotes: " + banknoteContainer.getBanknotesContainer().toString());
    }

    /**
     * Display issued banknotes
     */
    public void displayIssuedBanknotes(String issuedSum, String issuedBanknotes) {
        System.out.println("ISSUED BY: " + issuedSum + ", banknotes: " + issuedBanknotes);
    }

    /**
     * Check banknote value is not equal to zero
     *
     * @param nominal banknote nominal value
     */
    private void checkDesiredSumOnZero(int nominal) {
        if (nominal == 0) throw new ZeroSumException("\nОШИБКА: Запрашиваемая сумма не может быть равна 0\n");
    }

    /**
     * Check the desired sum in banknote container
     *
     * @param sum desired sum
     */
    private void checkDesiredSumInBanknoteContainer(int sum) {
        int availableSum = banknoteContainer.getBanknoteContainerTotalSum();
        if (sum > availableSum) throw new NotEnoughBanknotesSumException(
                String.format("\nОШИБКА: Сумма банкнот в банкомате недостаточна, запрошено: %s, доступно: %s\n", sum, availableSum));
    }

    /**
     * Check the amount is a multiple of the minimum face value of a banknote
     *
     * @param sum desired sum
     */
    private void checkNominal(int sum) {
        if (sum % 50 != 0)
            throw new SumParityException(String.format("\nОШИБКА: Запрашиваемая сумма должна быть кратной номиналу банкнот. " +
                    "\nНоминалы: %s\n", Arrays.toString(Ruble.values())));
    }
}

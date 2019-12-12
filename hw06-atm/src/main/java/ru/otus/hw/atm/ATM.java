package ru.otus.hw.atm;

import ru.otus.hw.Ruble;

/**
 * ATM emulator class
 */
public class ATM {
    private BanknoteContainer banknoteContainer;

    public ATM() {
        banknoteContainer = new BanknoteContainer();
    }

    public void acceptBanknotes(Ruble ruble, int amount) {
        checkBanknoteOnZero(ruble.getNominal());
        banknoteContainer.foldBanknotes(ruble, amount);
    }

    public void getBanknotes(int sum) {
        checkBanknoteOnZero(sum);
        checkDesiredSumOnAccount(sum);
        checkNominal(sum);
        String issuedBanknotes = String.valueOf(banknoteContainer.getBanknotesForIssue(sum));
        String issuedSum = String.valueOf(banknoteContainer.getIssuedSum());
        displayIssuedBanknotes(issuedSum, issuedBanknotes);
    }

    /**
     * Check banknote value is not equal to zero
     *
     * @param nominal
     */
    private void checkBanknoteOnZero(int nominal) {
        if (nominal == 0) throw new RuntimeException("\nОШИБКА: Запрашиваемая сумма не может быть равна '0'\n");
    }

    /**
     * Check the desired sum is on the account
     *
     * @param sum desired sum
     */
    private void checkDesiredSumOnAccount(int sum) {
        if (sum > banknoteContainer.getContainerData()) throw new RuntimeException("\nОШИБКА: На счете недостаточно средств\n");
    }

    /**
     * Check the amount is a multiple of the minimum face value of a banknote
     *
     * @param sum desired sum
     */
    private void checkNominal(int sum) {
        if (sum % 50 != 0) throw new RuntimeException("\nОШИБКА: Запрашиваемая сумма должна быть кратной номиналу банкнот. " +
                    "\nНоминалы: 50, 100, 500, 1000, 2000, 5000\n");
    }

    /**
     * Display account status
     */
    public void displayAccountStatus() {
        System.out.println("Account status: " + banknoteContainer.getContainerData() +
                ", banknotes: " + banknoteContainer.getBanknotes().toString());
    }

    /**
     * Display issued banknotes
     */
    private void displayIssuedBanknotes(String issuedSum, String issuedBanknotes) {
        System.out.println("ISSUED: " + issuedSum + ", banknotes: " + issuedBanknotes);

    }
}

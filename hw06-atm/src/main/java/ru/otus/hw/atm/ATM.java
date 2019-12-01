package ru.otus.hw.atm;

import ru.otus.hw.Banknote;

/**
 * ATM emulator class
 */
public class ATM {
    private BanknoteContainer banknoteContainer;

    public ATM() {
        banknoteContainer = new BanknoteContainer();
    }

    public void acceptBanknotes(Banknote banknote, int amount) {
        checkBanknoteNominal(banknote.getNominalValue());
        banknoteContainer.foldBanknotes(banknote, amount);
    }

    public void getBanknotes(int sum) {
        checkBanknoteNominal(sum);
        String issuedBanknotes = String.valueOf(banknoteContainer.getBanknotesForIssue(sum));
        String issuedSum = String.valueOf(banknoteContainer.getIssuedSum());
        displayIssuedBanknotes(issuedSum, issuedBanknotes);
    }

    private void checkBanknoteNominal(int nominal) {
        if (nominal == 0) System.out.println("Номинал банкноты не может быть равен '0'");
    }

    /**
     * Display account status
     */
    public void displayAccountStatus() {
        System.out.println("Account status: " + banknoteContainer.getGeneralContainerData() +
                ", banknotes: " + banknoteContainer.getBanknotes().toString());
    }

    /**
     * Display issued banknotes
     */
    private void displayIssuedBanknotes(String issuedSum, String issuedBanknotes) {
        System.out.println("Issued: " + issuedSum + ", banknotes: " + issuedBanknotes);

    }
}

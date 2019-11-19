package ru.otus.hw.atm;

import ru.otus.hw.Banknote;

import java.util.Map;

/**
 * ATM emulator class
 */
public class ATM {
    private BanknoteContainer banknoteContainer;

    public ATM() {
        banknoteContainer = new BanknoteContainer();
    }

    public void acceptBanknote(Banknote banknote) {
        checkBanknoteNominal(banknote.getNominalValue());
        banknoteContainer.putInContainer(banknote);
    }

    /**
     * Get banknotes by type and amount
     *
     * @param banknoteType monetary unit (dollar or ruble)
     * @param amount       desired banknote amount
     */
    public void getBanknotes(String banknoteType, int amount) {
        checkBanknoteNominal(amount);
        // TODO продолжить - реализовать
    }

    private void checkBanknoteNominal(int nominal) {
        if (nominal == 0) System.out.println("Номинал банкноты не может быть равен '0'");
    }

    /**
     * Display account state
     */
    public void printDisplayAccountState() {
        Map data = banknoteContainer.getGeneralContainerData();
        System.out.println("ACCOUNT STATE:");
        System.out.println("Rubles: " + data.get("rubles"));
        System.out.println("Dollars: " + data.get("dollars"));
    }
}
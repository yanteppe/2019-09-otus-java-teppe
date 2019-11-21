package ru.otus.hw.atm;

import ru.otus.hw.banknotes.Banknote;
import ru.otus.hw.banknotes.Dollar;
import ru.otus.hw.banknotes.Ruble;

import java.util.Map;

/**
 * ATM emulator class
 */
public class ATM {
    private BanknoteContainer banknoteContainer;

    public ATM() {
        banknoteContainer = new BanknoteContainer();
    }

    /**
     * Accept banknotes
     *
     * @param banknote monetary unit (dollar or ruble)
     */
    public void acceptBanknote(Banknote banknote) {
        banknoteContainer.putInContainer(banknote);
    }

    /**
     * Get banknotes by type and sum
     *
     * @param banknote monetary unit (dollar or ruble)
     * @param sum      desired sum
     */
    public void getBanknotes(Banknote banknote, int sum) {
        if (!checkSum(banknote, sum)) {
            printATMMessage(String.format("\nОШИБКА: Запрашиваемая сумма должна быть кратной номиналу банкнот. " +
                    "\nРубли номиналы: %s. Доллары номиналы: %s.", Ruble.TYPE.toString(), Dollar.TYPE.toString()));
        }
        if (checkDesiredSumOnAccount(banknote, sum)) banknoteContainer.getOutBanknotes(banknote, sum);
        else printATMMessage(String.format("\nОШИБКА: На счете '%s' недостаточно средств", banknote.getType()));
        // TODO: продолжить - реализовать
    }

    /**
     * Check the amount is a multiple of the minimum face value of a banknote
     *
     * @param sum desired sum
     */
    private boolean checkSum(Banknote banknote, int sum) {
        if (banknote instanceof Ruble) {
            return sum % Ruble.RUB_50.getNominal() == 0;
        } else {
            return sum % Dollar.DOLLAR_5.getNominal() == 0;
        }
    }

    /**
     * Check the desired sum is on the account
     *
     * @param banknote monetary unit (dollar or ruble)
     * @param sum      desired sum
     */
    private boolean checkDesiredSumOnAccount(Banknote banknote, int sum) {
        if (banknote instanceof Ruble) {
            return sum <= banknoteContainer.getRubleTotalData();
        } else {
            return sum <= banknoteContainer.getDollarTotalData();
        }
    }

    private void printATMMessage(String message) {
        System.out.println(message);
    }

    /**
     * Print account state
     */
    public void printAccountState() {
        Map data = banknoteContainer.getGeneralContainerData();
        System.out.println("ACCOUNT STATE:");
        System.out.println("Rubles: " + data.get("rubles"));
        System.out.println("Dollars: " + data.get("dollars"));
    }
}
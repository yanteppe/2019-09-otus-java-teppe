package ru.otus.hw.atm;

import ru.otus.hw.banknotes.Banknote;
import ru.otus.hw.banknotes.Dollar;
import ru.otus.hw.banknotes.Ruble;

import java.util.Arrays;
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
    public void getBanknotes(String banknote, int sum) {
        if (!checkSum(banknote, sum)) {
            printATMMessage(String.format("\nОШИБКА: Запрашиваемая сумма должна быть кратной номиналу банкнот. " +
                    "\nРубли номиналы: %s. Доллары номиналы: %s.", Arrays.toString(Ruble.values()), Arrays.toString(Dollar.values())));
        }
        if (checkDesiredSumOnAccount(banknote, sum)) {
            banknoteContainer.collectBanknotes(banknote, sum);
            System.out.println("\nBanknotes issued, "
                    + banknote + ": "
                    + banknoteContainer.getSumIssuedBanknotes() + " "
                    + banknoteContainer.getBanknotesForIssue(banknote));
        } else {
            printATMMessage(String.format("ОШИБКА: На счете '%s' недостаточно средств", banknote));
        }
    }

    /**
     * Check the amount is a multiple of the minimum face value of a banknote
     *
     * @param sum desired sum
     */
    private boolean checkSum(String banknote, int sum) {
        if (banknote.equals("ruble")) {
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
    private boolean checkDesiredSumOnAccount(String banknote, int sum) {
        if (banknote.equals("ruble")) {
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
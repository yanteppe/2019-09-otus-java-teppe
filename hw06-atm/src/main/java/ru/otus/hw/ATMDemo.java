package ru.otus.hw;

import ru.otus.hw.atm.ATM;

/**
 * ATM emulator demo<br>
 * 1. Add banknotes to an ATM<br>
 * 2. Display account status after inserting banknotes<br>
 * 3. Receive banknotes for the required amount<br>
 * 4. Displays the status of the account after receiving banknotes<br>
 */
public class ATMDemo {

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.acceptBanknotes(Ruble.RUB_50, 3);
        atm.acceptBanknotes(Ruble.RUB_100, 3);
        atm.acceptBanknotes(Ruble.RUB_200, 3);
        atm.acceptBanknotes(Ruble.RUB_500, 3);
        atm.acceptBanknotes(Ruble.RUB_1000, 3);
        atm.acceptBanknotes(Ruble.RUB_2000, 3);
        atm.acceptBanknotes(Ruble.RUB_5000, 3);
        atm.displayAccountStatus();
        atm.getBanknotes(15400);
        atm.displayAccountStatus();
    }
}

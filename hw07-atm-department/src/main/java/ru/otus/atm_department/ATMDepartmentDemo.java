package ru.otus.atm_department;

import ru.otus.atm_department.atm.ATM;
import ru.otus.atm_department.atm.ATMImpl;
import ru.otus.atm_department.atm.banknote.Ruble;

import java.util.Arrays;

public class ATMDepartmentDemo {

    public static void main(String[] args) {
        ATM atmA = new ATMImpl();
        ATM atmB = new ATMImpl();
        ATM atmC = new ATMImpl();

        // region Demo Observer pattern
        ATMDepartment atmDepartment = new ATMDepartment(Arrays.asList(atmA, atmB, atmC));
        atmDepartment.subscribe((ATMEventPublisher) atmA);
        atmDepartment.subscribe((ATMEventPublisher) atmB);
        atmDepartment.subscribe((ATMEventPublisher) atmC);

        atmA.acceptBanknotes(Ruble.RUB_100, 1);
        atmB.acceptBanknotes(Ruble.RUB_100, 2);
        atmC.acceptBanknotes(Ruble.RUB_100, 3);
        atmDepartment.displayATMsTotalBalance();

        atmA.getBanknotes(100);
        atmDepartment.displayATMsTotalBalance();

        atmB.getBanknotes(100);
        atmDepartment.displayATMsTotalBalance();

        atmC.getBanknotes(100);
        atmDepartment.displayATMsTotalBalance();
        // endregion

    }
}

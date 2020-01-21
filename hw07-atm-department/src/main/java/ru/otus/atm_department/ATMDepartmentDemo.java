package ru.otus.atm_department;

import ru.otus.atm_department.atm.ATM;
import ru.otus.atm_department.atm.ATMCaretaker;
import ru.otus.atm_department.atm.ATMImpl;
import ru.otus.atm_department.atm.banknote.Ruble;

import java.util.Arrays;

public class ATMDepartmentDemo {

    public static void main(String[] args) {


        // region Demo Observer pattern
        System.out.println("--- Demo Observer pattern ---");
        ATM atm1 = new ATMImpl();
        ATM atm2 = new ATMImpl();
        ATM atm3 = new ATMImpl();
        ATMDepartment atmDepartment1 = new ATMDepartment(Arrays.asList(atm1, atm2, atm3));
        // Подписать atmDepartment1 на события каждого банкомата (Изменение баланса)
        atmDepartment1.subscribe((ATMEventPublisher) atm1);
        atmDepartment1.subscribe((ATMEventPublisher) atm2);
        atmDepartment1.subscribe((ATMEventPublisher) atm3);

        // Пополнить баланс в каждом банкомате
        System.out.println("\nПополнить баланс в каждом банкомате:");
        atm1.acceptBanknotes(Ruble.RUB_100, 1);
        atm2.acceptBanknotes(Ruble.RUB_100, 2);
        atm3.acceptBanknotes(Ruble.RUB_100, 3);
        atm1.displayAccountStatus();
        atm2.displayAccountStatus();
        atm3.displayAccountStatus();
        atmDepartment1.displayATMsTotalBalance();

        /*
         Выдать купюры из банкоматов.
         Департамент получает уведомления от банкоматов об изменении баланса
         и отображет измененный общий баланс банкоматов.
        */
        System.out.println("\nВыдать купюры из банкоматов и отобразить измененный общий баланс:");
        atm1.getBanknotes(100);
        atmDepartment1.displayATMsTotalBalance();
        System.out.println();

        atm2.getBanknotes(100);
        atmDepartment1.displayATMsTotalBalance();
        System.out.println();

        atm3.getBanknotes(100);
        atmDepartment1.displayATMsTotalBalance();
        // endregion


        // region Demo Memento pattern
        System.out.println("\n--- Demo Memento pattern ---");
        ATM atm4 = new ATMImpl();
        ATM atm5 = new ATMImpl();
        ATM atm6 = new ATMImpl();
        ATMDepartment atmDepartment2 = new ATMDepartment(Arrays.asList(atm4, atm5, atm6));
        atmDepartment2.subscribe((ATMEventPublisher) atm4);
        atmDepartment2.subscribe((ATMEventPublisher) atm5);
        atmDepartment2.subscribe((ATMEventPublisher) atm6);

        // Установка начального состояния
        atm4.acceptBanknotes(Ruble.RUB_50, 1);
        atm5.acceptBanknotes(Ruble.RUB_100, 2);
        atm6.acceptBanknotes(Ruble.RUB_200, 3);

        // Сохранение состояния
        ATMCaretaker caretaker4 = new ATMCaretaker();
        ATMCaretaker caretaker5 = new ATMCaretaker();
        ATMCaretaker caretaker6 = new ATMCaretaker();
        caretaker4.setAtmMemento(((ATMImpl) atm4).saveState());
        caretaker5.setAtmMemento(((ATMImpl) atm5).saveState());
        caretaker6.setAtmMemento(((ATMImpl) atm6).saveState());
        System.out.println("\nЗаданное состояние:");
        atm4.displayAccountStatus();
        atm5.displayAccountStatus();
        atm6.displayAccountStatus();
        atmDepartment2.displayATMsTotalBalance();

        // Изменение состояния
        System.out.println("\nИзмененное состояние:");
        //atm4.acceptBanknotes(Ruble.RUB_100, 1);
        //atm5.acceptBanknotes(Ruble.RUB_200, 1);
        //atm6.acceptBanknotes(Ruble.RUB_500, 1);
        atm4.getBanknotes(50);
        atm5.getBanknotes(100);
        atm6.getBanknotes(400);
        atm4.displayAccountStatus();
        atm5.displayAccountStatus();
        atm6.displayAccountStatus();
        atmDepartment2.displayATMsTotalBalance();

        // Восстановление изначального состояния
        System.out.println("\nВосстановление изначального состояния:");
        atmDepartment2.restoreATMsOriginalState(
                Arrays.asList(caretaker4.getAtmMemento(), caretaker5.getAtmMemento(), caretaker6.getAtmMemento()));
        atm4.displayAccountStatus();
        atm5.displayAccountStatus();
        atm6.displayAccountStatus();
        atmDepartment2.displayATMsTotalBalance();
        // endregion
    }
}

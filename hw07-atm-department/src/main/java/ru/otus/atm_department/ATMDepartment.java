package ru.otus.atm_department;


import ru.otus.atm_department.atm.ATM;

import java.util.ArrayList;
import java.util.List;

public class ATMDepartment implements ATMEventSubscriber {
    private List<ATM> atmList;
    private List<ATMEventPublisher> publishers;
    private int totalAtmBalance;

    ATMDepartment(List<ATM> atmList) {
        this.atmList = atmList;
        publishers = new ArrayList<>();
    }

    void displayATMsTotalBalance() {
        System.out.println("TOTAL ATMs BALANCE: " + totalAtmBalance);
    }

    @Override
    public void updateTotalBalance() {
        int counter = 0;
        for (ATM atm : atmList) {
            counter = counter + atm.getATMBalance();
        }
        totalAtmBalance = counter;
    }

    void subscribe(ATMEventPublisher atmEventPublisher) {
        publishers.add(atmEventPublisher);
        atmEventPublisher.addSubscriber(this);
    }

    void unsubscribe(ATMEventPublisher atmEventPublisher) {
        publishers.remove(atmEventPublisher);
        atmEventPublisher.removeSubscriber(this);
    }

    void unsubscribeAll(List<ATMEventPublisher> publishers) {
        publishers.forEach(publisher -> publisher.removeSubscriber(this));
    }
}

package ru.otus.atm_department;


import ru.otus.atm_department.atm.ATM;
import ru.otus.atm_department.atm.ATMImpl;

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
        updateTotalBalance();
        System.out.println("TOTAL ATMs BALANCE: " + totalAtmBalance);
    }

    // TODO: вместо этого метода рассмотреть паттерн Chains of responsibility или Command...
    void restoreATMsOriginalState(List<ATMImpl.ATMMemento> atmMemento) {
        for (int i = 0; i < atmList.size(); i++) {
            atmList.get(i).loadState(atmMemento.get(i));
        }
    }

    @Override
    public void updateTotalBalance() {
        int counter = 0;
        for (ATM atm : atmList) {
            counter = counter + atm.getAtmBalance();
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

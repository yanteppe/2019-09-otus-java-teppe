package ru.otus.atm_department;

import java.util.List;

public interface ATMEventPublisher {

    void addSubscriber(ATMEventSubscriber atmSubscriber);

    void removeSubscriber(ATMEventSubscriber subscriber);

    void notifySubscribers();

    List<ATMEventSubscriber> getSubscribers();

    void removeAllSubscribers();
}

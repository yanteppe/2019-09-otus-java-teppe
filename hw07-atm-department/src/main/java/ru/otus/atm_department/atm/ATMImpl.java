package ru.otus.atm_department.atm;

import ru.otus.atm_department.ATMEventPublisher;
import ru.otus.atm_department.ATMEventSubscriber;
import ru.otus.atm_department.atm.banknote.Ruble;
import ru.otus.atm_department.atm.container.BanknoteContainer;
import ru.otus.atm_department.atm.container.BanknoteContainerImpl;
import ru.otus.atm_department.atm.exception.NotEnoughBanknotesSumException;
import ru.otus.atm_department.atm.exception.SumParityException;
import ru.otus.atm_department.atm.exception.ZeroSumException;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * ATMImpl emulator class
 */
public class ATMImpl implements ATM, ATMEventPublisher {
    private String atmId;
    private List<ATMEventSubscriber> subscribers;
    private BanknoteContainer banknoteContainer;

    public ATMImpl() {
        this.atmId = "ATM №" + (int) (+1 + Math.random() * 99);
        banknoteContainer = new BanknoteContainerImpl();
        subscribers = new LinkedList<>();
    }

    /**
     * Accept the required number of banknotes
     *
     * @param ruble  currency unit
     * @param amount number of banknotes accepted
     */
    public void acceptBanknotes(Ruble ruble, int amount) {
        checkDesiredSumOnZero(ruble.getNominal());
        banknoteContainer.foldBanknotes(ruble, amount);
        createEvent();
    }

    // region Displayed

    /**
     * Display account status
     */
    public void displayAccountStatus() {
        System.out.println(this.atmId + " Account status: " + getAtmBalance() +
                ", banknotes: " + banknoteContainer.getBanknotesContainer().toString());
    }

    /**
     * Display issued banknotes
     */
    public void displayIssuedBanknotes(String issuedSum, String issuedBanknotes) {
        System.out.println(this.atmId + " ISSUED: " + issuedSum + ", banknotes: " + issuedBanknotes);
    }

    /**
     * Display ATM subscribers
     */
    public void displaySubscribers() {
        System.out.println("ATM subscribers: " + subscribers.toString());
    }
    // endregion

    // region Checks

    /**
     * Check banknote value is not equal to zero
     *
     * @param nominal banknote nominal value
     */
    private void checkDesiredSumOnZero(int nominal) {
        if (nominal == 0) throw new ZeroSumException("\nОШИБКА: Запрашиваемая сумма не может быть равна 0\n");
    }

    /**
     * Check the desired sum in banknote container
     *
     * @param sum desired sum
     */
    private void checkDesiredSumInBanknoteContainer(int sum) {
        int availableSum = getAtmBalance();
        if (sum > availableSum) throw new NotEnoughBanknotesSumException(
                String.format("\nОШИБКА: Сумма банкнот в банкомате недостаточна, запрошено: %s, доступно: %s\n", sum, availableSum));
    }

    /**
     * Check the amount is a multiple of the minimum face value of a banknote
     *
     * @param sum desired sum
     */
    private void checkNominal(int sum) {
        if (sum % 50 != 0)
            throw new SumParityException(String.format("\nОШИБКА: Запрашиваемая сумма должна быть кратной номиналу банкнот. " +
                    "\nНоминалы: %s\n", Arrays.toString(Ruble.values())));
    }
    // endregion

    // region Getters and Setters
    public int getAtmBalance() {
        return banknoteContainer.getBanknoteContainerBalance();
    }

    /**
     * Get desired sum
     *
     * @param sum desired sum
     */
    public void getBanknotes(int sum) {
        checkDesiredSumOnZero(sum);
        checkDesiredSumInBanknoteContainer(sum);
        checkNominal(sum);
        String issuedBanknotes = String.valueOf(banknoteContainer.getSumBanknotesToIssue(sum));
        String issuedSum = String.valueOf(banknoteContainer.getIssuedSum());
        createEvent();
        displayIssuedBanknotes(issuedSum, issuedBanknotes);
    }
    // endregion

    // region Publisher methods
    private void createEvent() {
        notifySubscribers();
    }

    @Override
    public void addSubscriber(ATMEventSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(ATMEventSubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers() {
        subscribers.forEach(subscriber -> subscriber.updateTotalBalance());
    }

    @Override
    public List<ATMEventSubscriber> getSubscribers() {
        return subscribers;
    }

    @Override
    public void removeAllSubscribers() {
        subscribers.clear();
    }
    // endregion

    // region Memento class and methods

    /**
     * Save ATM state in ATMMemento
     *
     * @return new ATMMemento
     */
    public ATMMemento saveState() {
        return new ATMMemento(atmId, subscribers, banknoteContainer);
    }

    /**
     * Load ATM state from ATMMemento
     *
     * @param atmMemento ATMMemento
     */
    public void loadState(ATMMemento atmMemento) {
        this.atmId = atmMemento.getAtmId();
        this.subscribers = atmMemento.getSubscribers();
        this.banknoteContainer = atmMemento.getContainer();
    }

    public class ATMMemento {
        private String atmId;
        private List<ATMEventSubscriber> subscribers;
        private BanknoteContainer container;

        ATMMemento(String atmId, List<ATMEventSubscriber> subscribers, BanknoteContainer banknoteContainer) {
            this.atmId = atmId;
            this.subscribers = new LinkedList<>(subscribers);
            try {
                this.container = serializeToSave(banknoteContainer);
            } catch (ClassNotFoundException exception) {
                exception.printStackTrace();
            }
        }

        public String getAtmId() {
            return atmId;
        }

        List<ATMEventSubscriber> getSubscribers() {
            return subscribers;
        }

        BanknoteContainer getContainer() {
            return container;
        }

        /**
         * Saving the state of BanknoteContainer - copying object through serialization
         *
         * @param banknoteContainer object BanknoteContainer
         * @return BanknoteContainer
         * @throws ClassNotFoundException ClassNotFoundException
         */
        private BanknoteContainer serializeToSave(BanknoteContainer banknoteContainer) throws ClassNotFoundException {
            BanknoteContainer container = null;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(banknoteContainer);
                objectOutputStream.close();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                container = (BanknoteContainer) objectInputStream.readObject();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            return container;
        }
    }

    // endregion
}

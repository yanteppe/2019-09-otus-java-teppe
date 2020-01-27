package ru.otus.atm_department.atm;

public class ATMCaretaker {
    private ATMImpl.ATMMemento atmMemento;

    public ATMImpl.ATMMemento getAtmMemento() {
        return atmMemento;
    }

    public void setAtmMemento(ATMImpl.ATMMemento atmMemento) {
        this.atmMemento = atmMemento;
    }
}

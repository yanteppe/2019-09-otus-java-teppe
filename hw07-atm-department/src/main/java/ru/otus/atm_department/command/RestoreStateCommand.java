package ru.otus.atm_department.command;

import ru.otus.atm_department.ATMDepartment;
import ru.otus.atm_department.atm.ATMImpl;

import java.util.List;

public class RestoreStateCommand implements Command {
    private ATMDepartment atmDepartment;
    private List<ATMImpl.ATMMemento> atmMemento;

    public RestoreStateCommand(ATMDepartment atmDepartment, List<ATMImpl.ATMMemento> atmMemento) {
        this.atmDepartment = atmDepartment;
        this.atmMemento = atmMemento;
    }

    @Override
    public void execute() {
        atmDepartment.restoreATMsOriginalState(atmMemento);
    }
}
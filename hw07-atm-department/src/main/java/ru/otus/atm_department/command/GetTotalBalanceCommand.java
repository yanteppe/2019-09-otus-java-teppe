package ru.otus.atm_department.command;

import ru.otus.atm_department.ATMDepartment;

public class GetTotalBalanceCommand implements Command {
    ATMDepartment atmDepartment;

    public GetTotalBalanceCommand(ATMDepartment atmDepartment) {
        this.atmDepartment = atmDepartment;
    }

    @Override
    public void execute() {
        atmDepartment.updateTotalBalance();
        atmDepartment.displayATMsTotalBalance();
    }
}

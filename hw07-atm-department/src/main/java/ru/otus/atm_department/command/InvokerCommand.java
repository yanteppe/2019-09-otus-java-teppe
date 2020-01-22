package ru.otus.atm_department.command;

public class InvokerCommand {
    private Command getTotalBalance;
    private Command restoreState;

    public InvokerCommand(Command getTotalBalance, Command restoreState) {
        this.getTotalBalance = getTotalBalance;
        this.restoreState = restoreState;
    }

    public void getGetTotalBalance() {
        getTotalBalance.execute();
    }

    public void restoreATMsState() {
        restoreState.execute();
    }
}

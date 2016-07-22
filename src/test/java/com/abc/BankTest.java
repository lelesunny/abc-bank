package com.abc;

import java.util.Calendar;

import org.junit.Test;

import static org.junit.Assert.*;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalDailyInterestPaid() * Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalDailyInterestPaid() * Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account_no_withdraw() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(150.0, bank.totalDailyInterestPaid() * Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account_withdraw() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);
        checkingAccount.withdraw(2000.0);

        assertEquals(1.0, bank.totalDailyInterestPaid() * Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR), DOUBLE_DELTA);
    }
    
	@Test
    public void transferToTest() {
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingAccount = new Account(Account.SAVINGS);
        Customer customer = new Customer("Bill");
        customer.openAccount(checkingAccount);
        customer.openAccount(savingAccount);

        checkingAccount.deposit(3000.0);
        assertTrue(savingAccount.transactions.isEmpty());
        checkingAccount.transferTo(savingAccount, 1000);
        assertEquals(1, savingAccount.transactions.size());
        assertEquals(1000.0, savingAccount.sumTransactions(), DOUBLE_DELTA);
        assertEquals(2000.0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }
}

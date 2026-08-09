package com.services;
import java.util.List;

import com.data.Account;
import com.data.Transaction;

public interface AccountServices {
     
	boolean createAccount(Account account);
	Account login(int accountNo, String password);
	double deposit(int accountNo, double amount);
	double withdraw(int accountNo, double amount);
	boolean transferMoney(int sender, int receiver, double amount);
	double checkBalance(int accountNo);
	List<Transaction> miniStatement(int accountNo);
	boolean changePassword(int accountNo, String oldPassword, String newPassword);
	boolean closeAccount(int accountNo);
}

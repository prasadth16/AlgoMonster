package com.java.threads.deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {

	private Long accountNumber;
	private String holderName;
	private Double accountBalance;
	public Lock lock;
	public BankAccount(Long accountNumber, String holderName) {
		this.accountNumber = accountNumber;
		this.holderName = holderName;
		this.accountBalance = 1000.0;
		lock=new ReentrantLock();
	}

	public Double addBalance(Double amount) {
		accountBalance += amount;
		return accountBalance;
	}

	public Double deductAmount(Double amount) {
		accountBalance -= amount;
		return accountBalance;
	}

	public Double getBalance() {
		return this.accountBalance;
	}

	public String toString() {

		return accountNumber +"<-->"+ holderName +"<-->"+ accountBalance;
	}

}

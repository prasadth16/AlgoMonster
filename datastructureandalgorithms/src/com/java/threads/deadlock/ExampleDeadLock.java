package com.java.threads.deadlock;

import java.time.LocalDateTime;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExampleDeadLock {

	public boolean transferBalance(BankAccount sender, BankAccount receiver, Double amount) {

		synchronized (sender) {
			if (sender.getBalance() > amount) {
				synchronized (receiver) {
					sender.deductAmount(amount);
					receiver.addBalance(amount);
				}
			}
		}
		return true;
	}

	public boolean transferBalanceWithoutDeadLock(BankAccount sender, BankAccount receiver, Double amount) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime deadLine = now.plusSeconds(1);
		while (true) {
			if (sender.getBalance() < amount) {
				System.out.println("No sufficient balance....");
				return false;
			} else {
				if (sender.lock.tryLock()) {
					try {
						if (receiver.lock.tryLock()) {
							try {
								sender.deductAmount(amount);
								receiver.addBalance(amount);
								return true;
							} finally {
								receiver.lock.unlock();

							}
						}

					} finally {
						sender.lock.unlock();
					}
				}
				System.out.println(deadLine.toString()+" ---- "+LocalDateTime.now().toString());
				if (deadLine.isBefore(LocalDateTime.now())) {
					return false;
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		//badExample();
		goodExample();

	}

	public static void badExample() throws InterruptedException {
		ExampleDeadLock be = new ExampleDeadLock();
		BankAccount trupti = new BankAccount(Long.valueOf(656578657887242L), "Trupti Thakur");
		BankAccount prasad = new BankAccount(Long.valueOf(656578657887243L), "Prasad Thakur");
		Thread t1 = new Thread(() -> {
			be.transferBalance(prasad, trupti, 100.0);
		});

		Thread t2 = new Thread(() -> {
			be.transferBalance(trupti, prasad, 200.0);
		});

		t1.start();
		t2.start();
		t1.join();
		t2.join();

		System.out.println(trupti.toString());
		System.out.println(prasad.toString());
	}

	public static void goodExample() throws InterruptedException {
		ExampleDeadLock be = new ExampleDeadLock();
		BankAccount trupti = new BankAccount(Long.valueOf(656578657887242L), "Trupti Thakur");
		BankAccount prasad = new BankAccount(Long.valueOf(656578657887243L), "Prasad Thakur");
		Thread t1 = new Thread(() -> {
			if (be.transferBalanceWithoutDeadLock(prasad, trupti, 100.0)) {
				System.out.println("Successfuly transfered $100.0 from prasad to trupti");
			}else {
				System.out.println("UnSuccessful transfer of $100.0 from prasad to trupti");
			}
		});

		Thread t2 = new Thread(() -> {
			if (be.transferBalanceWithoutDeadLock(trupti, prasad, 200.0)) {
				System.out.println("Successfuly transfered $200.0 from trupti to prasad");
			}else {
				System.out.println("UnSuccessful transfer of $200.0 from trupti to prasad");
			}
		});

		t1.start();
		t2.start();
		t1.join();
		t2.join();

		System.out.println(trupti.toString());
		System.out.println(prasad.toString());
	}

}

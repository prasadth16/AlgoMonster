package com.java.threads.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AutomicVariableDemo {
	private static AtomicInteger count = new AtomicInteger(0);

	public static void addInteger(int ipValue) {
		if (ipValue == 1)
			count.incrementAndGet();
		else
			count.addAndGet(ipValue);
	}

	public static void substractInteger(int ipValue) {
		if (ipValue == 1)
			count.decrementAndGet();
		else {
			boolean isupdated = false;
			while (!isupdated) {
				int curruntCount = count.get();
				int updatedCount = curruntCount - ipValue;

				isupdated = count.compareAndSet(curruntCount, updatedCount);
			}
		}
	}

	public static void main(String[] args) {

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					AutomicVariableDemo.addInteger(1);
					System.out.println("Count is..."+count.get());
					TimeUnit.SECONDS.sleep(1);
					AutomicVariableDemo.addInteger(10);
					System.out.println("Count is..."+count.get());
					TimeUnit.SECONDS.sleep(1);
					AutomicVariableDemo.substractInteger(3);
					System.out.println("Count is..."+count.get());
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					AutomicVariableDemo.addInteger(3);
					System.out.println("Count is..."+count.get());
					TimeUnit.SECONDS.sleep(1);
					AutomicVariableDemo.addInteger(7);
					System.out.println("Count is..."+count.get());
					TimeUnit.SECONDS.sleep(1);
					AutomicVariableDemo.substractInteger(9);
					System.out.println("Count is..."+count.get());
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread t3 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					AutomicVariableDemo.addInteger(11);
					System.out.println("Count is..."+count.get());
					TimeUnit.SECONDS.sleep(1);
					AutomicVariableDemo.addInteger(2);
					System.out.println("Count is..."+count.get());
					TimeUnit.SECONDS.sleep(1);
					AutomicVariableDemo.substractInteger(6);
					System.out.println("Count is..."+count.get());
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		t1.start();
		t2.start();
		t3.start();
	}

}

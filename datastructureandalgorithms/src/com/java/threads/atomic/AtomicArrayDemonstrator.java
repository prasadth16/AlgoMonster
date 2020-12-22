package com.java.threads.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicArrayDemonstrator {
	private AtomicIntegerArray atomicArray = new AtomicIntegerArray(new int[] { 0, 0 });

	public void addToArray(int value, int index) {
		int newValue = 0;
		if (value <= 1)
			newValue = atomicArray.incrementAndGet(index);
		else
			newValue = atomicArray.addAndGet(index, value);
		System.out.println("new Value " + value + " added by " + Thread.currentThread().getName() + " And Total Is...."
				+ atomicArray.get(index));
	}

	public void substractFromArray(int value, int index) {
		if (value <= 1)
			atomicArray.decrementAndGet(index);
		else {
			boolean isUpdated = false;
			while (!isUpdated) {
				int currValue = atomicArray.get(index);
				int newValue = currValue - value;
				isUpdated = atomicArray.compareAndSet(index, currValue, newValue);
			}
		}
		System.out.println("new Value " + value + " substracted by " + Thread.currentThread().getName() + " And Total Is...."
				+ atomicArray.get(index));
	}

	public int getValue(int index) {
		return atomicArray.get(index);

	}

	public static void main(String[] args) {
		AtomicArrayDemonstrator demo = new AtomicArrayDemonstrator();

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				demo.addToArray(20, 1);
				demo.substractFromArray(9, 0);
			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				demo.addToArray(34, 0);
				demo.substractFromArray(1, 0);
			}
		});

		Thread t3 = new Thread(new Runnable() {

			@Override
			public void run() {
				demo.addToArray(26, 0);
				demo.substractFromArray(9, 1);
			}
		});

		Thread t4 = new Thread(new Runnable() {

			@Override
			public void run() {
				demo.addToArray(5, 1);
				demo.substractFromArray(1, 0);
			}
		});

		Thread t5 = new Thread(new Runnable() {

			@Override
			public void run() {
				demo.addToArray(1, 1);
				demo.substractFromArray(1, 1);
			}
		});

		Thread t6 = new Thread(new Runnable() {

			@Override
			public void run() {
				demo.addToArray(6, 0);
				demo.substractFromArray(8, 1);
			}
		});

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();

		try {
			t1.join();
			t2.join();
			t3.join();
			t4.join();
			t5.join();
			t6.join();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		System.out.println("Final Values are....");
		System.out.println("At index 0->" + demo.getValue(0));
		System.out.println("At index 1->" + demo.getValue(1));
	}
}

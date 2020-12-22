package com.java.threads.atomic;

public class TestConcurrentLinkedList {

	public static void main(String[] args) {
		ConcurrentLinkedlist<Integer> integerLinkedList = new ConcurrentLinkedlist<>();
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				integerLinkedList.addNode(12);
				integerLinkedList.addNode(13);
				integerLinkedList.addNode(14);
				integerLinkedList.addNode(15);
			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				integerLinkedList.addNode(22);
				integerLinkedList.addNode(23);
				integerLinkedList.addNode(24);
				integerLinkedList.addNode(25);
			}
		});

		Thread t3 = new Thread(new Runnable() {

			@Override
			public void run() {
				integerLinkedList.addNode(32);
				integerLinkedList.addNode(33);
				integerLinkedList.addNode(34);
				integerLinkedList.addNode(35);
			}
		});

		Thread t4 = new Thread(new Runnable() {

			@Override
			public void run() {
				integerLinkedList.addNode(42);
				integerLinkedList.addNode(43);
				integerLinkedList.addNode(44);
				integerLinkedList.addNode(45);
			}
		});

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
		try {
			t1.join();
			t2.join();
			t3.join();
			t4.join();
		}catch(InterruptedException ex) {
			ex.printStackTrace();
		}
		
		ConcurrentLinkedlist.Node head=integerLinkedList.getHead();
		
		head=head.getNextNode();
		while(head!=null) {
			System.out.print(head.getValue()+"->");
			head=head.getNextNode();
		}
		//System.out.print(head.getValue()+"->");
		System.out.print("NULL");
	}
}

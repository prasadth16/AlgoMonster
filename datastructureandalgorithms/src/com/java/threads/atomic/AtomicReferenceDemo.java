package com.java.threads.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {

	private AtomicReference<WebPageStatistics> atomicReference = new AtomicReference<WebPageStatistics>(
			new WebPageStatistics(0, 0));

	public void addToPageStatistics(boolean isErrorPage) {
		WebPageStatistics current, changed;
		Integer newPageCount, newErrorPageCount;
		do {
			current = atomicReference.get();
			newPageCount = current.getNoOfPagesAccessed() + 1;
			newErrorPageCount = current.getNoOfErrorPagesEncountered();
			if (isErrorPage) {
				newErrorPageCount += 1;
			}
		} while (!atomicReference.compareAndSet(current, new WebPageStatistics(newPageCount, newErrorPageCount)));
	}

	public static void main(String[] args) {
		AtomicReferenceDemo demo = new AtomicReferenceDemo();
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				demo.addToPageStatistics(true);
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				demo.addToPageStatistics(false);
			}
		});

		Thread t3 = new Thread(new Runnable() {
			public void run() {
				demo.addToPageStatistics(true);
			}
		});

		Thread t4 = new Thread(new Runnable() {
			public void run() {
				demo.addToPageStatistics(true);
			}
		});

		Thread t5 = new Thread(new Runnable() {
			public void run() {
				demo.addToPageStatistics(false);
			}
		});

		Thread t6 = new Thread(new Runnable() {
			public void run() {
				demo.addToPageStatistics(false);
			}
		});

		Thread t7 = new Thread(new Runnable() {
			public void run() {
				demo.addToPageStatistics(false);
			}
		});

		Thread t8 = new Thread(new Runnable() {
			public void run() {
				demo.addToPageStatistics(false);
			}
		});

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
		t8.start();

		try {
			t1.join();
			t2.join();
			t3.join();
			t4.join();
			t5.join();
			t6.join();
			t7.join();
			t8.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Statistics are as below....");
		System.out.println("Total number of pages accessed: " + demo.atomicReference.get().getNoOfPagesAccessed());
		System.out.println("Total number of error pages: " + demo.atomicReference.get().getNoOfErrorPagesEncountered());
	}

}

class WebPageStatistics {
	private Integer noOfPages;
	private Integer noOfErrorPages;

	public WebPageStatistics(int noOfPages, int noOfErrorPages) {
		this.noOfPages = noOfPages;
		this.noOfErrorPages = noOfErrorPages;
	}

	public Integer getNoOfPagesAccessed() {
		return this.noOfPages;
	}

	public Integer getNoOfErrorPagesEncountered() {
		return this.noOfErrorPages;
	}

}

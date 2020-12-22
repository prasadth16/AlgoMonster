package com.java.threads.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

import javax.naming.OperationNotSupportedException;

public class ConcurrentLinkedlist<T> {
	private static final AtomicReferenceFieldUpdater<Node, Node> NODE_NEXT_FIELD_UPDATOR = AtomicReferenceFieldUpdater
			.newUpdater(Node.class, Node.class, "next");
	private static final AtomicReferenceFieldUpdater<Node, Node> NODE_PREVIOUS_FIELD_UPDATOR = AtomicReferenceFieldUpdater
			.newUpdater(Node.class, Node.class, "previous");

	static class Node<T> {
		private final T value;
		volatile Node next;
		volatile Node previous;

		public Node(T value) {
			this.value = value;
			next = null;
			previous = null;
		}

		public Node() {
			this.value = null;
			next = null;
			previous = null;
		}

		public T getValue() {
			return value;
		}

		public Node getNextNode() {
			return next;
		}

		public Node getPrevious() {
			return previous;
		}
	}

	private Node head;
	private Node tail;

	public ConcurrentLinkedlist() {
		head = new Node();
		tail = head;
	}

	public Node getHead() {
		return head;
	}

	public Node addNode(T value) {
		Node<T> tmpNode = new Node<>(value);
		// if this is the first insertion in the list
		if (this.head == this.tail) {
			insertNode(head, tmpNode, true, false);
		} else {// This insertion is more than first insertion
			insertNode(tail, tmpNode, false, false);
		}

		return head;
	}

	public Node insertInMiddle(Node afterThis, T value) {
		insertNode(afterThis, new Node(value), false, true);
		return head;
	}

	public void insertNode(Node headNode, Node newNode, boolean isFirstInsertion, boolean isMiddleInsertion) {
		if (isFirstInsertion) {
			if (headNode == tail) {
				boolean isUpdated = false;
				while (!isUpdated) {
					Node currentNext = NODE_NEXT_FIELD_UPDATOR.get(headNode);
					isUpdated = NODE_NEXT_FIELD_UPDATOR.compareAndSet(headNode, currentNext, newNode);
				}
				newNode.previous = headNode;
				tail = newNode;
			}
		} else {
			if (isMiddleInsertion) {
				boolean isUpdated = false;
				// newNode's next node is headNode.next
				Node currNextNode = NODE_NEXT_FIELD_UPDATOR.get(headNode);
				newNode.next = currNextNode;

				// headNode.next=newNode
				isUpdated = false;
				while (!isUpdated) {
					Node currentNextNode = NODE_NEXT_FIELD_UPDATOR.get(headNode);
					isUpdated = NODE_NEXT_FIELD_UPDATOR.compareAndSet(headNode, currentNextNode, newNode);
				}
				// newNode.previous=headNode
				newNode.previous = headNode;

				// newNode.next.previous=newNode
				isUpdated = false;
				while (!isUpdated) {
					Node currentPrevious = NODE_PREVIOUS_FIELD_UPDATOR.get(newNode.next);
					isUpdated = NODE_PREVIOUS_FIELD_UPDATOR.compareAndSet(newNode.next, currentPrevious, newNode);
				}
			} else {
				boolean isUpdated = false;
				while (!isUpdated) {
					Node currentNext = NODE_NEXT_FIELD_UPDATOR.get(tail);
					isUpdated = NODE_NEXT_FIELD_UPDATOR.compareAndSet(tail, currentNext, newNode);
				}
				newNode.previous = tail;
				tail = newNode;
			}
		}
	}

	public Node deleteNode(Node node) throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}
}

package com.java.graphs.dfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
 * Time complexity is O(n+m) approximately
 * */
public class GraphClone {
	static class Node {
		public int val;
		public List<Node> neighbors;

		public Node() {
			val = 0;
			neighbors = new ArrayList<Node>();
		}

		public Node(int _val) {
			val = _val;
			neighbors = new ArrayList<Node>();
		}

		public Node(int _val, ArrayList<Node> _neighbors) {
			val = _val;
			neighbors = _neighbors;
		}

		public String toString() {
			return val + "";
		}
	}

	Node startReference = new Node(1);
	Map<Node, Node> visited = new HashMap<>();
	Map<Node, Node> parent = new HashMap<>();
	Map<Integer, Node> clones = new HashMap<>();

	public Node cloneGraph(Node node) {
		return cloneDFSHelp(node);
	}

	public Node cloneDFSHelp(Node node) {
		if (node == null)
			return null;
		visited.put(node, node);
		Node clone = new Node(node.val);
		clones.put(clone.val, clone);
		for (Node nd : node.neighbors) {
			if (!visited.containsKey(nd)) {
				parent.put(nd, node);
				Node tmpClone = cloneDFSHelp(nd);
				tmpClone.neighbors.add(clone);
				clone.neighbors.add(tmpClone);

			} else if (visited.containsKey(nd) && parent.get(node) != nd
					&& !clones.get(nd.val).neighbors.contains(clone)) {
				clones.get(nd.val).neighbors.add(clone);
				clone.neighbors.add(clones.get(nd.val));
			}
		}

		return clone;
	}

	public static void main(String[] args) {
		Node n1 = new Node(1);
		Node n2 = new Node(2);
		Node n3 = new Node(3);
		Node n4 = new Node(4);
		n1.neighbors.add(n2);
		n1.neighbors.add(n4);
		n2.neighbors.add(n1);
		n2.neighbors.add(n3);
		n3.neighbors.add(n2);
		n3.neighbors.add(n4);
		n4.neighbors.add(n3);
		n4.neighbors.add(n1);
		GraphClone gc = new GraphClone();
		Node clone = gc.cloneGraph(n1);
		System.out.println("Graph is cloned....");
	}
}

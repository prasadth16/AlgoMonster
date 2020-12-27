package com.java.graphs.dfs;

import java.util.HashMap;
import java.util.Map;

public class GraphIsTree {
	private Map<Integer, Map<Integer, Boolean>> adjMap = new HashMap<>();
	private int[] parent;
	private int[] visitted;
	private int[] startTime;
	private int[] endTime;
	int timeCounter = 0;
	int component = 1;

	public boolean validTree(int n, int[][] edges) {
		if (edges.length == 0) {
			return false;
		}
		parent = new int[n];
		visitted = new int[n];
		startTime = new int[n];
		endTime = new int[n];

		for (int i = 0; i < n; i++) {
			parent[i] = -1;
			startTime[i] = -1;
			endTime[i] = -1;
			visitted[i] = -1;
		}
		buildAdjMap(edges);
		for (int i = 0; i < n; i++) {
			if (visitted[i] == -1) {

				if (component++ > 1 || !dfsHelper(i))
					return false;
			} 
		}
		return true;
	}

	private void buildAdjMap(int[][] edges) {
		for (int i = 0; i < edges.length; i++) {
			if (adjMap.containsKey(edges[i][0])) {
				adjMap.get(edges[i][0]).put(edges[i][1], true);
			} else {
				Map<Integer, Boolean> tmpMap = new HashMap<>();
				tmpMap.put(edges[i][1], true);
				adjMap.put(edges[i][0], tmpMap);
			}
			if (adjMap.containsKey(edges[i][1])) {
				adjMap.get(edges[i][1]).put(edges[i][0], true);
			} else {
				Map<Integer, Boolean> tmpMap = new HashMap<>();
				tmpMap.put(edges[i][0], true);
				adjMap.put(edges[i][1], tmpMap);
			}
		}
	}

	private boolean dfsHelper(int node) {
		visitted[node] = component;
		startTime[node] = timeCounter++;
		for (Integer neighbor : adjMap.get(node).keySet()) {
			if (visitted[neighbor] != -1) {
				if (parent[node] != neighbor) {
					return false;
				}
			} else {
				parent[neighbor] = node;
				if (!dfsHelper(neighbor))
					return false;
			}
		}
		endTime[node] = timeCounter++;

		return true;
	}

	public static void main(String[] args) {
		GraphIsTree g = new GraphIsTree();
		int[][] edgeList = new int[][] { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 1, 4 } };// op should be true
		System.out.println(g.validTree(5, edgeList));

		edgeList = new int[][] { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 1, 3 }, { 1, 4 } };// op should be false
		System.out.println(g.validTree(5, edgeList));
	}
}

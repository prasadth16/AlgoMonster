package com.java.graphs.dfs;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopologicalSort {

	private Map<Integer, Map<Integer, Boolean>> adjMap = new HashMap<>();
	private int[] startTime;
	private int[] endTime;
	public int[] sortedNodes;
	private boolean[] visited;
	private int timeCounter;
	private int sortCounter;

	private boolean topoSort(int numCourses, int[][] prerequisites) {
		startTime = new int[numCourses];
		endTime = new int[numCourses];
		sortedNodes = new int[numCourses];
		visited = new boolean[numCourses];
		for (int i = 0; i < numCourses; i++) {
			startTime[i] = -1;
			endTime[i] = -1;
			adjMap.put(i, new HashMap<Integer, Boolean>());
		}
		buildAdjMap(prerequisites);
		for(int i=0; i<visited.length;i++) {
			if(!visited[i]) {
				if(!sortHelper(i)) {
					return false;
				}
			}
		}
		return true;
	}

	private void buildAdjMap(int[][] edgeList) {
		if (edgeList.length == 0)
			return;
		for (int i = 0; i < edgeList.length; i++) {
			adjMap.get(edgeList[i][1]).put(edgeList[i][0], true);
		}
	}

	private boolean sortHelper(Integer node) {
		visited[node] = true;
		startTime[node] = timeCounter++;
		for (Integer nd : adjMap.get(node).keySet()) {
			if (!visited[nd]) {
				if (!sortHelper(nd)) {
					return false;
				}
			} else if (endTime[nd] == -1) {
				return false;
			}
		}
		endTime[node] = timeCounter++;
		sortedNodes[sortCounter++] = node;
		return true;
	}

	public static void main(String[] args) {
		int[][] edgeList = new int[][] { { 1, 0 }, { 4, 0 }, { 3, 1 }, { 2, 1 }, { 2, 4 }, { 3, 4 }, { 3, 2 } };
		TopologicalSort s = new TopologicalSort();
		System.out.println(s.topoSort(5, edgeList));
		for (int i = s.sortedNodes.length - 1; i >= 0; i--)
			System.out.print(s.sortedNodes[i] + " ");
		System.out.println("Sorted....");
	}

}

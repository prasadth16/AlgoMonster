package com.java.graphs.bfs;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class BFSGraphTraversal {

	private Map<Integer, Map<Integer, Boolean>> adjMap = new HashMap<>();
	private int[] parent;
	private boolean[] visited;
	private Deque<Integer> discovered = new ArrayDeque<>();//FIFO
	private int component = 0;

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

	public void bfsHelper(int n, int[][] edges) {
		//initilization of arrays
		parent = new int[n];
		visited = new boolean[n];
		for (int i = 0; i < n; i++) {
			parent[i] = -1;
		}
		//building adjacency List out of all the edges provided as an input.
		buildAdjMap(edges);
		//Actual BFS logic starts here.
		//Outside array actually makes sure that all the nodes are visited in case there a multiple connected components.
		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				parent[i] = i;
				discovered.addLast(i);
				System.out.println("Visitted nodes in Component " + ++component);
				//Below while loops is continued until discovery area is empty and all the nodes are visited. 
				while (!discovered.isEmpty()) {
					Integer currNode = discovered.removeFirst();
					//Below for loop adds all the neighbor which are not visited and which are not discovered into the discover area.
					for (Integer neighbor : adjMap.get(currNode).keySet()) {
						if (!visited[neighbor] && parent[neighbor]==-1) {
							discovered.addLast(neighbor);
							parent[neighbor] = currNode;
						}
					}
					//once all the neighbors are discovered, mark the vertex as visited.
					visited[currNode] = true;
					System.out.print(currNode + ", ");
				}
				System.out.println("");
			}
		}
	}

	public static void main(String[] args) {
		int[][] edges = new int[][] { { 0, 1 }, { 0, 3 }, { 0, 8 }, { 8, 4 }, { 3, 4 }, { 3, 2 }, { 1, 7 }, { 2, 5 },
				{ 5, 6 } };
		BFSGraphTraversal gt = new BFSGraphTraversal();
		gt.bfsHelper(9, edges);
	}

}

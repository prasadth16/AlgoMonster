package com.java.graphs.representation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/*
 * This is the program to create the adjacency list representation for the given graph
 * Input is given in terms of list of edges
 * every edge has start and end vertex
 * */
public class AdjacencyListGraph {
	public static List<List<Integer>> getAdjListForGraph(List<List<Integer>> edgeList, int noOfVertex) {
		List<List<Integer>> adjList = new ArrayList<>();
		//initialize the adjacency List
		for (int i = 0; i < noOfVertex; i++) {
			adjList.add(new ArrayList<Integer>());
		}
		edgeList.forEach(edge -> {
			// For all edges repete the process

			// add end node as neighbor to the start node
			adjList.get(edge.get(0)).add(edge.get(1));

			// add start node as a neighbor of end node
			adjList.get(edge.get(1)).add(edge.get(0));

		});
		return adjList;

	}

	public static void main(String[] args) {
		// Input is given as List of lists. Every inner list has start vertex and end
		// vertex
		List<List<Integer>> edges = new ArrayList<>();
		List<Integer> l1 = Arrays.asList(0, 1);
		List<Integer> l2 = Arrays.asList(1, 4);
		List<Integer> l3 = Arrays.asList(0, 5);
		List<Integer> l4 = Arrays.asList(2, 0);
		List<Integer> l5 = Arrays.asList(3, 5);
		List<Integer> l6 = Arrays.asList(4, 2);
		List<Integer> l7 = Arrays.asList(3, 4);
		List<Integer> l8 = Arrays.asList(4, 5);
		edges.add(l1);
		edges.add(l2);
		edges.add(l3);
		edges.add(l4);
		edges.add(l5);
		edges.add(l6);
		edges.add(l7);
		edges.add(l8);
		List<List<Integer>> opAdjList = getAdjListForGraph(edges, 6);
		// Printing the adjacency List
		int vertexCounter = 0;
		for (List<Integer> neighbors : opAdjList) {
			System.out.print(vertexCounter++ + "->");
			for (Integer neighbor : neighbors) {
				System.out.print(neighbor + " , ");
			}
			System.out.println("");
		}
	}
}

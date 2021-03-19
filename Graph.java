package assign07;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import a7.GenericQueue;
import a7xia.Edge;
import a7xia.Graph;
import a7xia.T;
import a7xia.Vertex;


/**
 * Represents a sparse, unweighted, directed graph (a set of vertices and a set
 * of edges). The graph is not generic and assumes that a string name is stored
 * at each vertex.
 * 
 * @author Erin Parker
 * 
 * @author Junlin Su
 * @version March 17, 2021
 */
public class Graph<Type> {

	// the graph -- a set of vertices (String name mapped to Vertex instance)
	private HashMap<Type, Vertex<Type>> vertices;

	/**
	 * Constructs an empty graph.
	 */
	public Graph() {
		vertices = new HashMap<Type, Vertex<Type>>();
	}

	/**
	 * Adds to the graph a directed edge from the vertex with name "name1" to the
	 * vertex with name "name2". (If either vertex does not already exist in the
	 * graph, it is added.)
	 * 
	 * @param name1 - string name for source vertex
	 * @param name2 - string name for destination vertex
	 */
	public void addEdge(Type vertVal1, Type vertVal2) {
		Vertex<Type> vertex1;
		// if vertex already exists in graph, get its object
		if (vertices.containsKey(vertVal1))
			vertex1 = vertices.get(vertVal1);
		// else, create a new object and add to graph
		else {
			vertex1 = new Vertex<Type>(vertVal1);
			vertices.put(vertVal1, vertex1);
		}

		Vertex<Type> vertex2;
		if (vertices.containsKey(vertVal2))
			vertex2 = vertices.get(vertVal2);
		else {
			vertex2 = new Vertex<Type>(vertVal2);
			vertices.put(vertVal2, vertex2);
		}

		// add new directed edge from vertex1 to vertex2
		vertex1.addEdge(vertex2);
	}

	public boolean depthFirstSearch(Type src, Type des) {
		// check to make sure valid arguments
		if (vertices.containsKey(src) == false || vertices.containsKey(des) == false) {
			
			throw new IllegalArgumentException("Cannot find vertex of the source or the destination");
		}
		// special case
		boolean b = false;
		if (src.equals(des)) {
			return true;
		}
		vertices.get(src).dfs = 0;
		dfs(vertices.get(src), vertices.get(des), b);
		return b;

	}

	private void dfs(Vertex<Type> src, Vertex<Type> des, boolean b) {
		Vertex<Type> vert = src;
		boolean b1 = b;
		if (b == false) {
			for (Iterator<Edge<Type>> it = vert.edges(); it.hasNext();) {// TODO: BOOLEAN TURNS FALSE WHEN GOING BACK
				// TO DRIVER.
				Edge<Type> edge = it.next();
				if (edge.getOtherVertex().getName().equals(des.getName())) {
					b = true;
					return;
				} else {
					if (edge.getOtherVertex().dfs != 0) {
						edge.getOtherVertex().dfs = vert.dfs + 1;
						dfs(edge.getOtherVertex(), des, b);
					}
				}
			}
		}
	}

	public boolean breadthFirstSearch(Vertex<Type> graph, Type src, Type des) {

		for (Vertex<Type> vertex : vertices.values()) {
			vertex.setVisit(false);
		}
		Queue<Vertex<Type>> queue = new LinkedList<Vertex<Type>>();
		graph.setVisit(true);
		queue.add(graph);
		
		while (!queue.isEmpty()) {
			Vertex<Type> check = queue.remove();
			for (Edge<Type> e : check.Edges()) {
				Vertex<Type> dst = e.getOtherVertex();
				if (!dst.Visited()) {
					dst.setVisit(true);
					if (dst.getName() == graph) {
						return true;
					}
					queue.add(dst);
				}
				else {
				}
			}
		}
		return false;
	}
	
	/**
	 * Topoligicalsort to sort the ordering of the graph, may have more than one
	 * ordering.
	 * 
	 * Throw if graph is cycle.
	 * 
	 * @param graph
	 * @return list
	 */
	public List<Type> topoligicalSort(Graph<Type> graph) {
		Queue<Vertex<Type>> queue = new LinkedList<Vertex<Type>>();
		List<Vertex<Type>> topologicalOrderList = new ArrayList<Vertex<Type>>();
		
		for (Vertex<Type> vertex : vertices.values()) {
			vertex.setVisit(false);
			if (vertex.calculateIndegree(vertices, vertex) == 0) {
				queue.add(vertex);
			}
		}
		while (!queue.isEmpty()) {
			Vertex<T> vertexToAdd = queue.remove();
			previousIndegree = vertexToAdd.getIndegree();
			topologicalOrderList.add(vertexToAdd);
			for (Edge<T> e : vertexToAdd.getEdges()) {
				Vertex<T> newVertex = e.getOtherVertex();
				if (newVertex.calculateIndegree(verticesMap, newVertex) - previousIndegree - 1 == 0 && !newVertex.isVisited()) {
					queue.add(newVertex);
					newVertex.setVisited(true);
				}
			}
			
		}
		return (List<T>) topologicalOrderList;
	}
	
	
	

	/**
	 * Generates the DOT encoding of this graph as string, which can be pasted into
	 * http://www.webgraphviz.com to produce a visualization.
	 */
	public String generateDot() {
		StringBuilder dot = new StringBuilder("digraph d {\n");

		// for every vertex
		for (Vertex v : vertices.values()) {
			// for every edge
			Iterator<Edge> edges = v.edges();
			while (edges.hasNext())
				dot.append("\t" + v.getName() + " -> " + edges.next() + "\n");

		}

		return dot.toString() + "}";
	}

	/**
	 * Generates a simple textual representation of this graph.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();

		for (Vertex v : vertices.values())
			result.append(v + "\n");

		return result.toString();
	}
}
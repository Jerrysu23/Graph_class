package assign07;

import java.util.LinkedList;

import a7.Edge;
import a7.Vertex;

import java.util.HashMap;
import java.util.Iterator;

/**
 * This class represents a vertex (AKA node) in a directed graph. The vertex is
 * not generic, assumes that a string name is stored there.
 * 
 * @author Erin Parker
 * @author Junlin Su
 * @version March 17, 2021
 */
public class Vertex<Type> {

	// used to id the Vertex
	private Type vertVal;

	// adjacency list
	private LinkedList<Edge<Type>> adj;

	public int inDegree = 0;

	public int dfs = 0;

	public boolean Visitor;

	/**
	 * Creates a new Vertex object, using the given name.
	 * 
	 * @param name - string used to identify this Vertex
	 */
	public Vertex(Type data) {
		this.vertVal = vertVal;
		this.adj = new LinkedList<Edge<Type>>();
	}

	/**
	 * @return the string used to identify this Vertex
	 */
	public Type getName() {
		return this.vertVal;
	}

	/**
	 * Adds a directed edge from this Vertex to another.
	 * 
	 * @param otherVertex - the Vertex object that is the destination of the edge
	 */
	public void addEdge(Vertex<Type> otherVertex) {
		adj.add(new Edge<Type>(otherVertex));
	}

	/**
	 * @return a iterator for accessing the edges for which this Vertex is the
	 *         source
	 */
	public Iterator<Edge<Type>> edges() {
		return adj.iterator();
	}

	/**
	 * Generates and returns a textual representation of this Vertex.
	 */
	public String toString() {
		String s = "Vertex " + vertVal + " adjacent to vertices ";
		Iterator<Edge<Type>> itr = adj.iterator();
		while (itr.hasNext())
			s += itr.next() + "  ";
		return s;
	}

	public double getDistanceFromStart() {
		return dfs;
	}

	public void setDistanceFromStart(double distanceFromStart) {
		this.dfs = dfs;
	}
	public void setUpVisit(boolean Visitor) {
		this.Visitor = Visitor;
	}
	
	public boolean Visited() {
		return Visitor;
	}
	public LinkedList<Edge<Type>> Edges() {
		return adj;
	}
	public int calculateIndegree (HashMap<Type, Vertex<Type>> verticesMap, Vertex<Type> vertex) {
		vertex.inDegree = 0;
		for(Vertex<Type> verticesToCheck : verticesMap.values()) {
			LinkedList<Edge<Type>> linkedListEdges = verticesToCheck.Edges();
			for (Edge<Type> e : linkedListEdges) {
				if (e.getOtherVertex().equals(vertex)) {
					vertex.inDegree++;
				}
			}
		}
		return vertex.inDegree;
	}
	
	public void setUpIndegree (int newIndegree) {
		this.inDegree = newIndegree;
	}
	
	public int getIndegree () {
		return inDegree;
	}

}
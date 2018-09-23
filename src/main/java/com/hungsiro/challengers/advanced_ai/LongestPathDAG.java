package com.hungsiro.challengers.advanced_ai;
import java.util.*;


/***
 * Has not completed yet.
 * The idea is using Topology sort to represent a linear ordering of
 * the graph
 * Once we have topological order (or linear representation), we one by one process all vertices
 * in topological order. For every vertex being processed, we update total point of its adjacent using
 * weight of current vertex.
 */

public class LongestPathDAG {
    // Represent a Node of the Graph.
    static class Node{

        // Display or Id of Node
        private String displayName;
        // Weight of the node
        private int weight;

        // Constructor
        public Node(String name , int weight){
            this.displayName = name;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node node = (Node) o;
            return weight == node.weight &&
                Objects.equals(displayName, node.displayName);
        }

        @Override
        public int hashCode() {

            return Objects.hash(displayName, weight);
        }

        public String getDisplayName() {

            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }

    static class Graph {
        private Map<Node,LinkedList<Node>> adjMap; // Adjacency List

        //Constructor
        Graph(Map<Node,LinkedList<Node>> adjMap) {
            this.adjMap = adjMap;
        }

        // Function to add an edge into the graph
        public  void addEdge(Node v,Node w) { adjMap.get(v).add(w); }

        // A recursive function used by topologicalSort
        private void topologicalSortUtil(Node v, Map<Node,Boolean> visited, Stack stack) {
            // Mark the current node as visited.
            visited.put(v,true);
            Node i;

            // Recur for all the vertices adjacent to this
            // vertex
            Iterator<Node> it = adjMap.get(v).iterator();
            while (it.hasNext())
            {
                i = it.next();
                if (!visited.get(i))
                    topologicalSortUtil(i, visited, stack);
            }

            // Push current vertex to stack which stores result
            stack.push(v);
        }

        // The function to do Topological Sort. It uses
        // recursive topologicalSortUtil()
        private Stack<Node> topologicalSort() {
            Stack stack = new Stack<Node>();

            // Mark all the vertices as not visited
            Map<Node,Boolean> visited = new LinkedHashMap<Node, Boolean>();
            for (Node node : adjMap.keySet())
                visited.put(node,false);

            // Call the recursive helper function to store
            // Topological Sort starting from all vertices
            // one by one
            for (Node node : adjMap.keySet())
                if (visited.get(node) == false)
                    topologicalSortUtil(node, visited, stack);

            // Print contents of stack
            return stack;

        }

        // Find maximum path
        public void theLongestPath(){
            Map<Node,Integer> dist = new LinkedHashMap<Node, Integer>();
            for (Node node : adjMap.keySet()){
                dist.put(node,Integer.MIN_VALUE);
            }
            Stack<Node> sortedTopo = this.topologicalSort();

            // Pick one node to be start node
            // The first of sorted Topology will be start source.
            Node startNode = sortedTopo.peek();
            dist.put(startNode,startNode.weight);

            while (sortedTopo.empty()==false){
                Node u = sortedTopo.pop();
                if(dist.get(u) > Integer.MIN_VALUE){
                    LinkedList<Node> adj = adjMap.get(u);
                    for(Node node : adj){
                        if(dist.get(node) < dist.get(u) + node.getWeight())
                            dist.put(node,dist.get(u) + node.getWeight());
                    }
                }
            }
            // print dist
            for(Map.Entry<Node,Integer> entry: dist.entrySet()){
                System.out.println(entry.getKey().displayName + " : " +  entry.getValue());
            }
            // Test main
        }

    }
    public static void main(String[] args){
            Node A = new Node("A",1);
            Node B = new Node("B",2);
            Node C = new Node("C", 2);

            LinkedList<Node> adjectionOfA = new LinkedList<Node>();
            adjectionOfA.add(B);
            adjectionOfA.add(C);

            LinkedList<Node> adjectionOfB = new LinkedList<Node>();
            adjectionOfB.add(C);

            Map<Node,LinkedList<Node>> graph = new LinkedHashMap<Node, LinkedList<Node>>();
            graph.put(A,adjectionOfA);
            graph.put(B,adjectionOfB);
            graph.put(C,new LinkedList<Node>());

            Graph g = new Graph(graph);
            g.theLongestPath();
            /**
            * Output will be :
            *  A : 1
            *  B : 3
            *  C : 5
            */
    }



}

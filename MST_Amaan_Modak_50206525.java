import java.io.*;
import java.util.*;

class Edge {
    private int from;
    private int to;
    private int weight;

    
	/**
	 * Constructor for Edge
	 * 
	 * @param from
	 *            : int - The source vertex
	 * @param to
	 *            : int - The destination vertex
	 * @param weight
	 *            : int - The weight associated with the edge between the two vertices
	 */
    public Edge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
    
    public int getfrom() {
        return from;
    }
    
    public int getto() {
        return to;
    }
    
    public int getWeight() {
        return weight;
    }
}

class PriorityQueue {
    private ArrayList<Edge> pq;
    
    public PriorityQueue() {
        pq = new ArrayList<Edge>(); // create a new empty priority queue 
    }
    
    public boolean empty() {
        return (pq.size() == 0);
    }
    
    /**
     * Add the value in the heap
     * call heapify up to locate the exact position of the new element
     * @param e: value to be added
     */
    public void push(Edge e) {
        pq.add(e);
        if (pq.size() == 1) {
            return;
        }
        
        // Heapify_up
        int index = pq.size() - 1;
        int parentIndex = (index - 1) / 2;
        
        while (parentIndex > 0) {
            if (pq.get(parentIndex).getWeight() <= pq.get(index).getWeight()) {
                break;
            }
            
            Edge temp = pq.get(parentIndex);
            pq.set(parentIndex, pq.get(index));
            pq.set(index, temp);
            
            index = parentIndex; // set the new index
            parentIndex = (index - 1) / 2; // move to parent node
        }
        
        if (pq.get(parentIndex).getWeight() > pq.get(index).getWeight()) {
            Edge temp = pq.get(parentIndex);
            pq.set(parentIndex, pq.get(index));
            pq.set(index, temp);
        }
    }
    
    public Edge pop() {
        if (empty()) {
            return null;
        }
        
        Edge minWeightEdge = pq.get(0);
        
        // Heapify_down
        pq.set(0, pq.get(pq.size() - 1));
        pq.remove(pq.size() - 1);
        
        if (pq.size() != 0) {
            int index = 0, leftIndex = 1, rightIndex = 2;
            while (rightIndex < pq.size()) {
                if ((pq.get(index).getWeight() <= pq.get(leftIndex).getWeight()) && (pq.get(index).getWeight() <= pq.get(rightIndex).getWeight())) {
                    break;
                }
                
                if (pq.get(leftIndex).getWeight() <= pq.get(rightIndex).getWeight()) {
                    Edge temp = pq.get(index);
                    pq.set(index, pq.get(leftIndex));
                    pq.set(leftIndex, temp); 
                    index = leftIndex; // set the new index
                }
                else {
                    Edge temp = pq.get(index);
                    pq.set(index, pq.get(rightIndex));
                    pq.set(rightIndex, temp);
                    index = rightIndex; // set the new index
                }
                
                leftIndex = (2 * index) + 1;
                rightIndex = (2 * index) + 2;
            }
            
            if (leftIndex < pq.size()) {
                if (pq.get(index).getWeight() > pq.get(leftIndex).getWeight()) {
                    Edge temp = pq.get(index);
                    pq.set(index, pq.get(leftIndex));
                    pq.set(leftIndex, temp); // set the new index
                }
            }
        }
        
        return minWeightEdge; // return the edge between the given vertices with the lowest edge weight
    }
}

class MST_Amaan_Modak_50206525 {
    private static void mst(ArrayList<ArrayList<Edge>> graph) {
        
    	/** In case of empty graph */
    	if (graph == null) {
            return;
        }
        
        int numVertices = graph.size(); // number of vertices present in the graph
        
        /** In case of only one vertex in the graph */
        if (numVertices < 1) {
            return;
        }
        
        ArrayList<ArrayList<Edge>> tree = new ArrayList<ArrayList<Edge>>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            tree.add(i, new ArrayList<Edge>());
        }
        
        boolean[] visited = new boolean[numVertices]; // the vertices which have been visited
        long totalMSTWeight = 0;
        PriorityQueue edges = new PriorityQueue(); // create priority queue for MST
        
        // Visit vertex 0
        visited[0] = true;
        for (Edge e : graph.get(0)) {
            edges.push(e);
            
        }
        
        while (!edges.empty()) {
            Edge leastEdge = edges.pop(); // removes minimum weight vertex
            if (visited[leastEdge.getto()] == false) {
                visited[leastEdge.getto()] = true;
                totalMSTWeight += leastEdge.getWeight(); // calculate the minimum cost of the spanning tree
                tree.get(leastEdge.getfrom()).add(leastEdge);
                
                for (Edge e : graph.get(leastEdge.getto())) {
                    if (visited[e.getto()] == false) {
                        edges.push(e); // to maintain the heap property
                        }
                }
            }
        }
        
        
        System.out.println(totalMSTWeight);

        /** print the minimum spanning tree to an output file */
        try{
            PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
            writer.println(totalMSTWeight);
            for (int i = 0; i < tree.size(); i++) {
                for (Edge e : tree.get(i)) {
                	writer.println((e.getfrom() + 1) + " " + (e.getto() + 1));
                }
            }
            writer.flush();
            writer.close();
        } 
        
        catch (Exception ex) {
           ex.printStackTrace();
        }

        return;
    }
    
    public static void main(String[] args) {
        
    	try {  
    		Scanner input = new Scanner(new File("input.txt")); //enter path of input.txt
    		int vertices = input.nextInt(); //read number of vertices
            int edges = input.nextInt(); //read number of edges
            ArrayList<ArrayList<Edge>> graph = new ArrayList<ArrayList<Edge>>(vertices);
            
            System.out.println("Reading input from input file:");
            /** reading the vertices from the input file and adding to the graph */
            for (int i = 0; i < vertices; i++) {
            	graph.add(i, new ArrayList<Edge>());
            }     
            while (edges > 0) {
            	int m = input.nextInt();
            	int n = input.nextInt();
            	int weight = input.nextInt();
				System.out.println("Vertex 1 = " +m + " \tVertex 2 = " + n + " \nEdge Weight = " + weight);
            	/** Initialize the graph */
            	graph.get(m-1).add(new Edge(m-1, n-1, weight));
            	graph.get(n-1).add(new Edge(n-1, m-1, weight));
            	edges--;
            }
            input.close();
            
            mst(graph);
                    	
        } 
    	
    	catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return;
    }
}
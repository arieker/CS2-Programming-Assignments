import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Railroad {
    private int numberOfTracks;
    private String tracksFileName;

    // Constructor
    public Railroad(int numberOfTracks, String tracksFileName) {
        this.numberOfTracks = numberOfTracks;
        this.tracksFileName = tracksFileName;
    }

    private final Map<String, Integer> labelToVertex = new HashMap<>();
    private int currentVertex = 0;

    public String buildRailroad() {
    List<Edge> edges = readInputFromFile();

    edges.sort(Comparator.comparingInt(Edge::getCost)
            .thenComparing(edge -> getLabel(edge.getSource()))
            .thenComparing(edge -> getLabel(edge.getDestination())));
    
    DisjointSetImproved ds = new DisjointSetImproved(numberOfTracks);
    
    int totalCost = 0;
    StringBuilder result = new StringBuilder();
    
    for (Edge edge : edges) {
        int source = edge.getSource();
        int destination = edge.getDestination();
    
        if (ds.find(source) != ds.find(destination)) {
            ds.union(source, destination);

            int firstVertex = Math.min(source, destination);
            int secondVertex = Math.max(source, destination);

            String sourceLabel = getLabel(firstVertex);
            String destinationLabel = getLabel(secondVertex);

            if (sourceLabel.compareTo(destinationLabel) > 0) {
                String temp = sourceLabel;
                sourceLabel = destinationLabel;
                destinationLabel = temp;
            }

            result.append(sourceLabel).append("---").append(destinationLabel)
                .append("\t").append("$" + edge.getCost()).append("\n");

            totalCost += edge.getCost();
        }
    }
    
    return result.toString() + "The cost of the railroad is $" + totalCost + ".";
}

    

    private List<Edge> readInputFromFile() {
        List<Edge> edges = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(tracksFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                int source = getVertex(parts[0]);
                int destination = getVertex(parts[1]);
                int cost = Integer.parseInt(parts[2]);
                edges.add(new Edge(source, destination, cost));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return edges;
    }

    private int getVertex(String label) {
        return labelToVertex.computeIfAbsent(label, k -> currentVertex++);
    }

    private String getLabel(int vertex) {
        return labelToVertex.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == vertex)
                .findFirst()
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new IllegalArgumentException("Vertex not found: " + vertex));
    }
}

class Edge {
    private final int source;
    private final int destination;
    private final int cost;

    public Edge(int source, int destination, int cost) {
        this.source = source;
        this.destination = destination;
        this.cost = cost;
    }

    public int getSource() {
        return source;
    }

    public int getDestination() {
        return destination;
    }

    public int getCost() {
        return cost;
    }
}


class DisjointSetImproved
{
	int [] rank;
	int [] parent;
    int n;
	
    public DisjointSetImproved(int n)
    {
        rank = new int[n];
        parent = new int[n];
        this.n = n;
        makeSet();
    }

    public void makeSet()
    {
        for (int i = 0; i < n; i++) 
		{
            parent[i] = i;
        }
    }
	
	public int find(int x)
    {
        if (parent[x] != x) 
		{
            parent[x] = find(parent[x]);
        }
 
        return parent[x];
    }
	
	public void union(int x, int y)
    {
        int xRoot = find(x), yRoot = find(y);
 
        if (xRoot == yRoot)
            return;
		
        if (rank[xRoot] < rank[yRoot])
            parent[xRoot] = yRoot;
		
        else if (rank[yRoot] < rank[xRoot])
            parent[yRoot] = xRoot;
        else 
        {
            parent[yRoot] = xRoot;
            rank[xRoot] = rank[xRoot] + 1;
        }
    }
	
	
	public static void printSets(int[] universe, DisjointSetImproved ds)
    {
        for (int i: universe) 
		{
            System.out.print(ds.find(i) + " ");
        }
 
        System.out.println();
    }
}
import java.util.*;

class Edge {
    int src, dest, weight;
    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}

class DisjointSet {
    int[] parent, rank;
    public DisjointSet(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }
    public int find(int i) {
        if (parent[i] != i)
            parent[i] = find(parent[i]);
        return parent[i];
    }
    public void union(int x, int y) {
        int xroot = find(x);
        int yroot = find(y);
        if (rank[xroot] < rank[yroot])
            parent[xroot] = yroot;
        else if (rank[xroot] > rank[yroot])
            parent[yroot] = xroot;
        else {
            parent[yroot] = xroot;
            rank[xroot]++;
        }
    }
}

class Graph {
    int V, E;
    List<Edge> edges = new ArrayList<>();
    public Graph(int V, int E) {
        this.V = V;
        this.E = E;
    }
    public void addEdge(int src, int dest, int weight) {
        edges.add(new Edge(src, dest, weight));
    }
    public void kruskalMST() {
        Collections.sort(edges, Comparator.comparingInt(e -> e.weight));
        DisjointSet ds = new DisjointSet(V);
        List<Edge> mst = new ArrayList<>();
        for (Edge edge : edges) {
            int x = ds.find(edge.src);
            int y = ds.find(edge.dest);
            if (x != y) {
                mst.add(edge);
                ds.union(x, y);
            }
        }
        System.out.println("Edges in MST:");
        for (Edge edge : mst)
            System.out.println(edge.src + " - " + edge.dest + " : " + edge.weight);
    }
}

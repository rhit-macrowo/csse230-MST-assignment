import java.util.*;

public class kruskalMST {

    private int V; // Number of vertices
    private List<Edge> edges;
    private List<Edge> mst;

    // Edge class
    static class Edge {
        public int src, dest, weight;

        Edge(int s, int d, int w) {
            src = s;
            dest = d;
            weight = w;
        }

        @Override
        public String toString() {
            return src + " - " + dest + " : " + weight;
        }
    }

    // Disjoint set for union-find
    private static class DisjointSet {
        int[] parent, rank;

        DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY) return;

            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }

    // Constructor
    public kruskalMST(int vertices) {
        this.V = vertices;
        this.edges = new ArrayList<>();
        this.mst = new ArrayList<>();
    }

    // Add an edge to the graph
    public void addEdge(int src, int dest, int weight) {
        edges.add(new Edge(src, dest, weight));
    }

    // Run Kruskal's algorithm and store MST result
    public void runKruskal() {
        mst.clear(); // Clear previous MST

        // Step 1: Sort edges by weight
        edges.sort(Comparator.comparingInt(e -> e.weight));

        DisjointSet ds = new DisjointSet(V);

        // Step 2: Process edges
        for (Edge edge : edges) {
            int root1 = ds.find(edge.src);
            int root2 = ds.find(edge.dest);

            // If adding this edge doesn't form a cycle
            if (root1 != root2) {
                mst.add(edge);
                ds.union(root1, root2);

                if (mst.size() == V - 1) break; // MST complete
            }
        }
    }

    // Print the MST edges and total weight
    public void printMST() {
        int totalWeight = 0;
        System.out.println("Edges in the Minimum Spanning Tree:");
        for (Edge e : mst) {
            System.out.println(e);
            totalWeight += e.weight;
        }
        System.out.println("Total Weight of MST: " + totalWeight);
    }

    // Return MST for testing
    public List<Edge> getMST() {
        return mst;
    }
}

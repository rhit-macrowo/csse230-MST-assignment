import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class kruskalMSTTest {

    @Test
    void testCorrectMST() {
        kruskalMST g = new kruskalMST(4);
        g.addEdge(0, 1, 10);
        g.addEdge(0, 2, 6);
        g.addEdge(0, 3, 5);
        g.addEdge(1, 3, 15);
        g.addEdge(2, 3, 4);

        g.runKruskal();
        List<?> mst = g.getMST();

        int totalWeight = mst.stream()
                .mapToInt(e -> ((kruskalMST.Edge) e).weight)
                .sum();

        assertEquals(15, totalWeight); // 4 + 5 + 6 or similar
        assertEquals(3, mst.size());   // V - 1 edges
    }

    @Test
    void testDisconnectedGraph() {
        kruskalMST g = new kruskalMST(4);
        g.addEdge(0, 1, 1);
        g.addEdge(2, 3, 2);

        g.runKruskal();
        List<?> mst = g.getMST();

        assertEquals(2, mst.size());
        int totalWeight = mst.stream()
                .mapToInt(e -> ((kruskalMST.Edge) e).weight)
                .sum();
        assertEquals(3, totalWeight);
    }

    @Test
    void testDuplicateEdges() {
        kruskalMST g = new kruskalMST(3);
        g.addEdge(0, 1, 10);
        g.addEdge(0, 1, 5);  // Duplicate with smaller weight
        g.addEdge(1, 2, 3);
        g.addEdge(0, 2, 7);

        g.runKruskal();
        List<?> mst = g.getMST();

        int totalWeight = mst.stream()
                .mapToInt(e -> ((kruskalMST.Edge) e).weight)
                .sum();
        assertEquals(8, totalWeight); // 3 + 5
    }

    @Test
    void testAllEqualWeights() {
        kruskalMST g = new kruskalMST(4);
        g.addEdge(0, 1, 1);
        g.addEdge(0, 2, 1);
        g.addEdge(0, 3, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 3, 1);

        g.runKruskal();
        List<?> mst = g.getMST();

        assertEquals(3, mst.size()); // V - 1 edges
        int totalWeight = mst.stream()
                .mapToInt(e -> ((kruskalMST.Edge) e).weight)
                .sum();
        assertEquals(3, totalWeight); // All weights are 1
    }

    @Test
    void testNegativeWeights() {
        kruskalMST g = new kruskalMST(3);
        g.addEdge(0, 1, -1);
        g.addEdge(1, 2, -2);
        g.addEdge(0, 2, 4);

        g.runKruskal();

        List<kruskalMST.Edge> mst = g.getMST();

        int totalWeight = mst.stream()
            .mapToInt(e -> e.weight)
            .sum();
        assertEquals(-3, totalWeight);
    }

    @Test
    void testLargeGraphPerformance() {
        int n = 500;
        kruskalMST g = new kruskalMST(n);
        int edgeCount = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n && edgeCount < n * 4; j++) {
                g.addEdge(i, j, (i + j) % 1000);
                edgeCount++;
            }
        }

        long start = System.currentTimeMillis();
        g.runKruskal();
        long end = System.currentTimeMillis();

        List<?> mst = g.getMST();
        assertEquals(n - 1, mst.size());
        assertTrue((end - start) < 5000, "Kruskal's algorithm should run under 5 seconds.");
    }
}

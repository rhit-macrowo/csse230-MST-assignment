//adding vscode test cases here
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.AfterClass;
import org.junit.Test;

public class testing {
  
@Test
void testCorrectMST() {
    Graph g = new Graph(4, 5);
    g.addEdge(0, 1, 10);
    g.addEdge(0, 2, 6);
    g.addEdge(0, 3, 5);
    g.addEdge(1, 3, 15);
    g.addEdge(2, 3, 4);

    List<Edge> mst = g.kruskalMST();

    int totalWeight = mst.stream().mapToInt(e -> e.weight).sum();
    assertEquals(15, totalWeight); // MST edges: (2-3:4), (0-3:5), (0-1:10)
    assertEquals(3, mst.size());  // For 4 vertices, MST has V-1 = 3 edges
}
@Test
void testDisconnectedGraph() {
    Graph g = new Graph(4, 2);
    g.addEdge(0, 1, 1);
    g.addEdge(2, 3, 2);

    List<Edge> mst = g.kruskalMST();

    assertEquals(2, mst.size());
    assertTrue(mst.contains(new Edge(0, 1, 1)));
    assertTrue(mst.contains(new Edge(2, 3, 2)));
}
@Test
void testDuplicateEdges() {
    Graph g = new Graph(3, 4);
    g.addEdge(0, 1, 10);
    g.addEdge(0, 1, 5);  // smaller weight should be chosen
    g.addEdge(1, 2, 3);
    g.addEdge(0, 2, 7);

    List<Edge> mst = g.kruskalMST();

    int totalWeight = mst.stream().mapToInt(e -> e.weight).sum();
    assertEquals(8, totalWeight); // MST: (1-2:3), (0-1:5)
}
@Test
void testLargeGraphPerformance() {
    int n = 1000;
    Graph g = new Graph(n, (n * (n - 1)) / 4); // ~25% density

    Random rand = new Random();
    for (int i = 0; i < g.E; i++) {
        int u = rand.nextInt(n);
        int v = rand.nextInt(n);
        if (u != v) {
            g.addEdge(u, v, rand.nextInt(1000));
        }
    }

    long start = System.currentTimeMillis();
    List<Edge> mst = g.kruskalMST();
    long end = System.currentTimeMillis();

    System.out.println("MST edges: " + mst.size() + ", Time taken: " + (end - start) + "ms");

    assertEquals(n - 1, mst.size());
    assertTrue((end - start) < 5000); // performance under 5 seconds
}


}

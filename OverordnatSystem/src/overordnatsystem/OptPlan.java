/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overordnatsystem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author oskst764
 */
public class OptPlan {

    private List<Vertex> nodes;
    private List<Edge> edges;
    private DataStore ds;

    public OptPlan(DataStore ds) {
        this.ds = ds;
    }

    public void createPlan(int start,int stop) {
        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        LinkedList<Vertex> path;
        int diff;

        for (int i = 0; i < ds.nodes; i++) {
            Vertex lovation = new Vertex("" + (i + 1), "Nod #" + (i + 1));
            //System.out.println(lovation.toString());
            nodes.add(lovation);
        }

        for (int i = 0; i < ds.arcs; i++) {
            diff = (int) Math.max(Math.abs(ds.nodeY[ds.arcStart[i] - 1] - ds.nodeY[ds.arcEnd[i] - 1]), Math.abs(ds.nodeX[ds.arcStart[i] - 1] - ds.nodeX[ds.arcEnd[i] - 1]));
            Edge lane = new Edge("" + (i + 1), nodes.get(ds.arcStart[i] - 1), nodes.get(ds.arcEnd[i] - 1), diff);
            edges.add(lane);
        }

        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        // Compute shortest path
        dijkstra.execute(nodes.get(start));
        path = dijkstra.getPath(nodes.get(stop));

        // Get shortest path
        for (int i = 0; i < path.size(); i++) {
            System.out.println(path.get(i));
        }
    }
}
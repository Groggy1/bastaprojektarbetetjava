/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overordnatsystem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.lang.Math;

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

    public void createPlan() {
        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        LinkedList<Vertex> path;
        //ArrayList<LinkedList<Vertex>> paths;
        int diff;

        for (int i = 0; i < ds.nodes; i++) {
            Vertex lovation = new Vertex("" + (i + 1), "Nod #" + (i + 1));
            //System.out.println(lovation.toString());
            nodes.add(lovation);
        }

        for (int i = 0; i < ds.arcs; i++) {
            diff = (int) Math.max(Math.abs(ds.nodeY[ds.arcStart[i] - 1] - ds.nodeY[ds.arcEnd[i] - 1]),Math.abs(ds.nodeX[ds.arcStart[i] - 1] - ds.nodeX[ds.arcEnd[i] - 1]));
            Edge lane = new Edge("" + (i + 1), nodes.get(ds.arcStart[i] - 1), nodes.get(ds.arcEnd[i] - 1), diff);
            edges.add(lane);
        }

        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        // Compute shortest path
        /*for (int i = 1; i <= ds.nodes; i++) {
            System.out.println("i" + i);
            for (int j = 1; j <= ds.nodes; j++) {
                System.out.println("j" + j);*/
                dijkstra.execute(nodes.get(3));
                path = dijkstra.getPath(nodes.get(32));
                //paths.add(path);
            //}
        //}
        // Get shortest path
        for (int i = 0; i < path.size(); i++) {
            System.out.println(path.get(i));
        }
    }
}
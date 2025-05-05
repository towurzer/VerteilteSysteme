package org.aau.homework.assignment_07.ex1;

import java.util.*;

public class Graph {
    public HashMap<Integer, Node> nodeMap = new HashMap<>();
    public int[][] adjacencyMatrix;

    public int[] dijkstra(int s) {
        int[] entfernung = new int[nodeMap.size()];
        Integer[] vorgaenger = new Integer[nodeMap.size()];
        boolean[] gewaehlt = new boolean[nodeMap.size()];
        List<Integer> R = new ArrayList<>();

        for (int i = 0; i < entfernung.length; i++) {
            entfernung[i] = Integer.MAX_VALUE;
            vorgaenger[i] = null;
            gewaehlt[i] = false;
        }

        entfernung[s] = 0;
        vorgaenger[s] = s;
        gewaehlt[s] = true;
        R.clear();

        ergaenze_R(s, R, entfernung, gewaehlt, vorgaenger);

        while (!R.isEmpty()) {
            R.sort((o1, o2) -> {
                if (entfernung[o1] == entfernung[o2])
                    return 0;
                if (entfernung[o1] < entfernung[o2])
                    return -1;
                return 1;
            });

            int v = R.getFirst();
            R.removeFirst();
            gewaehlt[v] = true;
            ergaenze_R(v, R, entfernung, gewaehlt, vorgaenger);

        }

        return entfernung;

    }

    public void ergaenze_R(int v, List<Integer> R, int[] entfernung, boolean[] gewaehlt, Integer[] vorgaenger) {
        int[] connections = adjacencyMatrix[v];
        for (int i = 0; i < connections.length; i++) {
            if (connections[i] == 0)
                continue;

            // else
            if (gewaehlt[i])
                continue;

            if (entfernung[v] + 1 >= entfernung[i])
                continue;

            vorgaenger[i] = v;
            entfernung[i] = entfernung[v] + 1;
            R.add(i);
        }

    }

    public int getDiameter() {
        int maxLen = 0;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            int[] result = Arrays.stream(dijkstra(i)).sorted().toArray();
            if (result[result.length - 1] > maxLen)
                maxLen = result[result.length - 1];
        }
        return maxLen;
    }

    public int getDistance(int v1, int v2) {
        return dijkstra(v1)[v2];
    }

    public double getAvgPathLength() {
        int sum = 0;
        int pairCount = 0;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                if (i == j)
                    continue;

                sum += getDistance(i, j);
                pairCount++;
            }
        }

        return (double) sum / pairCount;
    }

    public double getNetworkAverageClusteringCoefficient(){
        int nodeCount = 0;
        double clusterIngSum = 0;

        for (int i = 0; i < adjacencyMatrix.length; i++){
            if (Arrays.stream(adjacencyMatrix[i]).sum() <= 1)
                continue;

            clusterIngSum += getLocalClusteringCoefficient(i);
            nodeCount++;
        }

        return clusterIngSum / nodeCount;
    }

    public double getLocalClusteringCoefficient(int v) {
        int degree = Arrays.stream(adjacencyMatrix[v]).sum();
        if (degree <= 1)
            throw new IllegalArgumentException("Cant calculate ClusteringCoefficient of v with degree < 2");

        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix[v].length; i++) {
            if (adjacencyMatrix[v][i] == 1) {
                neighbors.add(i);
            }
        }

        int edges = 0;
        for (int i = 0; i < neighbors.size(); i++) {
            for (int j = i + 1; j < neighbors.size(); j++) {
                int neighborA = neighbors.get(i);
                int neighborB = neighbors.get(j);
                if (adjacencyMatrix[neighborA][neighborB] == 1) {
                    edges++;
                }
            }
        }

        return (double) (2 * edges) / (degree * (degree - 1));
    }


    public int[][] getAdjazenzmatrix() {
        int[][] matrix = new int[nodeMap.size()][nodeMap.size()];

        for (int i = 0; i < nodeMap.size(); i++) {
            int[] row = matrix[i];
            Node n = nodeMap.get(i);

            for (Node neighbour : n.neighbours) {
                row[neighbour.id] = 1;
            }
        }
        return matrix;
    }


    public void buildGraph() {
        Node a = new Node(0);
        Node b = new Node(1);
        Node c = new Node(2);
        Node d = new Node(3);
        Node e = new Node(4);
        Node f = new Node(5);
        Node g = new Node(6);
        Node h = new Node(7);
        Node i = new Node(8);
        Node j = new Node(9);
        Node k = new Node(10);
        Node l = new Node(11);
        Node m = new Node(12);
        Node n = new Node(13);


        a.addNeighbour(d);
        a.addNeighbour(f);

        b.addNeighbour(c);
        b.addNeighbour(e);
        b.addNeighbour(m);
        b.addNeighbour(k);

        c.addNeighbour(b);
        c.addNeighbour(h);

        d.addNeighbour(a);
        d.addNeighbour(g);
        d.addNeighbour(e);

        e.addNeighbour(d);
        e.addNeighbour(h);
        e.addNeighbour(b);
        e.addNeighbour(m);

        f.addNeighbour(a);
        f.addNeighbour(g);
        f.addNeighbour(i);
        f.addNeighbour(j);

        g.addNeighbour(d);
        g.addNeighbour(h);
        g.addNeighbour(i);
        g.addNeighbour(f);

        h.addNeighbour(e);
        h.addNeighbour(c);
        h.addNeighbour(g);

        i.addNeighbour(f);
        i.addNeighbour(g);
        i.addNeighbour(k);
        i.addNeighbour(j);

        j.addNeighbour(i);
        j.addNeighbour(f);

        k.addNeighbour(i);
        k.addNeighbour(b);
        k.addNeighbour(l);

        l.addNeighbour(k);

        m.addNeighbour(e);
        m.addNeighbour(b);
        m.addNeighbour(n);

        n.addNeighbour(m);

        nodeMap.put(a.id, a);
        nodeMap.put(b.id, b);
        nodeMap.put(c.id, c);
        nodeMap.put(d.id, d);
        nodeMap.put(e.id, e);
        nodeMap.put(f.id, f);
        nodeMap.put(g.id, g);
        nodeMap.put(h.id, h);
        nodeMap.put(i.id, i);
        nodeMap.put(j.id, j);
        nodeMap.put(k.id, k);
        nodeMap.put(l.id, l);
        nodeMap.put(m.id, m);
        nodeMap.put(n.id, n);


        adjacencyMatrix = getAdjazenzmatrix();
    }
}

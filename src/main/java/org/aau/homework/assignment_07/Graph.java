package org.aau.homework.assignment_07;

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


    public void queryFlooding(int fromId, int toId, int hopCount){
        int iterationCount = 1;
        int requestCount = 0;
        List<Integer> randMenge = new ArrayList<>();
        randMenge.add(fromId);
        boolean[][] recievedRequestFrom = new boolean[nodeMap.size()][nodeMap.size()];

        while (iterationCount <= hopCount){
            for (Integer nodeId:  List.copyOf(randMenge)){
                randMenge.remove(nodeId);

                if (nodeId == toId){
                    System.out.printf("Iteration %d: Found object of interest in Node %s%n", iterationCount, nodeMap.get(nodeId).id);
                    continue;
                }


                int[] connections = adjacencyMatrix[nodeId];
                for (int neighbour = 0; neighbour < adjacencyMatrix.length; neighbour++){
                    if (connections[neighbour] == 0 || neighbour == nodeId)
                        continue;
                    // neighbour (int) is a neighbour of nodeId


                    if (!recievedRequestFrom[nodeId][neighbour]) {
                        randMenge.add(neighbour);
                        recievedRequestFrom[neighbour][nodeId] = true;
                        System.out.printf("Iteration %d: %s query's %s%n", iterationCount, nodeMap.get(nodeId).id, (nodeMap.get(neighbour)).id);
                        requestCount++;
                    }

                }
            }
            iterationCount++;
            System.out.println("----------------------------------------------------------");
        }

        System.out.printf("Query done: %d iteration, %d total requests%n", --iterationCount, requestCount);
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

    public double getNetworkAverageClusteringCoefficient() {
        int nodeCount = 0;
        double clusterIngSum = 0;

        for (int i = 0; i < adjacencyMatrix.length; i++) {
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

    public Integer getKeyByValue(Node node) {
        for (HashMap.Entry<Integer, Node> entry : nodeMap.entrySet()) {
            if (node.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public int[][] getAdjazenzmatrix() {
        int[][] matrix = new int[nodeMap.size()][nodeMap.size()];

        for (int i = 0; i < nodeMap.size(); i++) {
            int[] row = matrix[i];
            Node n = nodeMap.get(i);

            for (Node neighbour : n.neighbours) {
                Integer key = getKeyByValue(neighbour);
                row[key] = 1;
            }
        }
        return matrix;
    }


    public void buildGraph() {
        Node a = new Node("A");
        Node b = new Node("B");
        Node c = new Node("C");
        Node d = new Node("D");
        Node e = new Node("E");
        Node f = new Node("F");
        Node g = new Node("G");
        Node h = new Node("H");
        Node i = new Node("I");
        Node j = new Node("J");
        Node k = new Node("K");
        Node l = new Node("L");
        Node m = new Node("M");
        Node n = new Node("N");


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

        int id = 0;
        nodeMap.put(id++, a);
        nodeMap.put(id++, b);
        nodeMap.put(id++, c);
        nodeMap.put(id++, d);
        nodeMap.put(id++, e);
        nodeMap.put(id++, f);
        nodeMap.put(id++, g);
        nodeMap.put(id++, h);
        nodeMap.put(id++, i);
        nodeMap.put(id++, j);
        nodeMap.put(id++, k);
        nodeMap.put(id++, l);
        nodeMap.put(id++, m);
        nodeMap.put(id, n);


        adjacencyMatrix = getAdjazenzmatrix();
    }
}

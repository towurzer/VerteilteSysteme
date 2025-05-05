package org.aau.homework.assignment_07;

public class Main {
    public static void main(String[] args) {
        Graph a = new Graph();
        a.buildGraph();
        System.out.printf("Distance A, B: %d%n", a.getDistance(0, 1));
        System.out.printf("Distance A, C: %d%n", a.getDistance(0, 2));
        System.out.printf("Diameter of G: %d%n", a.getDiameter());
        System.out.printf("Average path length in G: %.3f%n", a.getAvgPathLength());
        System.out.printf("Clustering coefficient of B: %.3f%n", a.getLocalClusteringCoefficient(1));
        System.out.printf("Network average clustering coefficient of G: %.3f%n", a.getNetworkAverageClusteringCoefficient());

    }
}

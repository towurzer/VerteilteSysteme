package org.aau.homework.assignment_09.ex1;

import java.util.*;

public class ChordSystem {
    TreeMap<Integer, Node> nodes;
    int m = 8; // ID Namespace - Bit Count

    int peerCount;
    int keyCount;

    public ChordSystem(int peerCount, int keyCount) {
        nodes = new TreeMap<>(); // TreeMap automatically sorts keys
        this.peerCount = peerCount;
        this.keyCount = keyCount;
        initPeers();
        buildFingerTables();
        distributeKeys(createKeys());
    }

    private void initPeers() {
        Random random = new Random();
        Set<Integer> peerIdSet = new HashSet<>();
        while (peerIdSet.size() < peerCount) {
            peerIdSet.add(random.nextInt(0, (int) (Math.pow(2, m) - 1)));
        }

        for (int i : peerIdSet){
            nodes.put(i, new Node(i, m, this));
        }
    }

    private Set<Integer> createKeys() {
        Random random = new Random();
        Set<Integer> keySet = new HashSet<>();
        while (keySet.size() < keyCount) {
            keySet.add(random.nextInt(0, (int) (Math.pow(2, m) - 1)));
        }

        return keySet;
    }

    private void buildFingerTables() {
        for (Node node: nodes.values()){
            node.buildFingerTables();
        }
    }

    private void distributeKeys(Set<Integer> keys) {
        for(int key: keys){
            Node responsiblePeer = findSuccessor(key);
            responsiblePeer.addKey(key);
        }
    }

    public Node findSuccessor(int targetId) {
        for(int key: nodes.keySet()){
            if (key >= targetId){
                return nodes.get(key);
            }
        }
        return nodes.get(nodes.firstKey());
    }

    public TreeMap<Integer, Node> getNodes() {
        return nodes;
    }


    public static boolean isInCircularInterval(int value, int lowerBoundExclusive, int upperBoundInclusive, int ringSize){
        lowerBoundExclusive = lowerBoundExclusive % ringSize;
        upperBoundInclusive = upperBoundInclusive % ringSize;
        value = value%ringSize;
        if (lowerBoundExclusive < upperBoundInclusive){
            return value > lowerBoundExclusive && value <= upperBoundInclusive;
        }else if (lowerBoundExclusive > upperBoundInclusive) {
            // Interval wraps around (e.g., lower=250, upper=10 for ring size 256)
            return value > lowerBoundExclusive || value <= upperBoundInclusive;
        } else {
            // lowerBoundExclusive == upperBoundInclusive
            return true;
        }
    }

    public static boolean isInCircularIntervalExclusiveUpperBound(int value, int lowerBoundExclusive, int upperBoundInclusive, int ringSize){
        lowerBoundExclusive = lowerBoundExclusive % ringSize;
        upperBoundInclusive = upperBoundInclusive % ringSize;
        value = value%ringSize;
        if (lowerBoundExclusive < upperBoundInclusive){
            return value > lowerBoundExclusive && value < upperBoundInclusive;
        }else if (lowerBoundExclusive > upperBoundInclusive) {
            // Interval wraps around (e.g., lower=250, upper=10 for ring size 256)
            return value > lowerBoundExclusive || value < upperBoundInclusive;
        } else {
            // lowerBoundExclusive == upperBoundInclusive
            return false;
        }
    }

}

package org.aau.homework.assignment_09;

import java.util.*;


import static java.lang.Math.pow;
import static org.aau.homework.assignment_09.ChordSystem.isInCircularInterval;
import static org.aau.homework.assignment_09.ChordSystem.isInCircularIntervalExclusiveUpperBound;

public class Node {
    final int id;
    final Node[] fingerTable;
    int m; // ID Namespace - Bit Count

    Set<Integer> storedKeys;

    ChordSystem parentSystem;

    Node successor;


    public Node(int id, int m, ChordSystem parentSystem) {
        this.id = id;
        this.m = m;
        this.fingerTable = new Node[m];
        this.parentSystem = parentSystem;
        this.storedKeys = new HashSet<>();
    }

    public void buildFingerTables(){
            for (int i = 0; i < m; i++) {
            int targetId = (int) (id + pow(2, i));
            targetId %= (int) pow(2, m);

            fingerTable[i] = parentSystem.findSuccessor(targetId);
        }
        successor = parentSystem.findSuccessor((int) ((id+1) % pow(2, m)));
    }

    public void addKey(int key) {
        storedKeys.add(key);
    }

    public Node searchForKey(int key){
        System.out.printf("Node %d is searching for %d. (Finger Table: '%s')%n", id, key, fingerTableToString());

        if (isInCircularInterval(key, id, successor.id, (int) (pow(2, m)))){
            return successor;
        }

        // else
        for (int i = m-1; i >= 0; i--){
            Node fingerNode = fingerTable[i];
            if(isInCircularIntervalExclusiveUpperBound(fingerNode.id, id, key, (int) pow(2, m))){
                return fingerNode.searchForKey(key);
            }
        }
        return successor.searchForKey(key); // try again in next node (as in paper)
    }

    private String fingerTableToString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < m; i++) {
            sb.append(fingerTable[i].id);
            if (i < m - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}

package org.aau.homework.assignment_07;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public String id;
    public List<Node> neighbours;

    public Node(String id){
        this.id = id;
        neighbours = new ArrayList<>();
    }

    public void addNeighbour(Node child){
        neighbours.add(child);
    }
}

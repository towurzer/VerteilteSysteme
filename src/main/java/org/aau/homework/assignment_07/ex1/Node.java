package org.aau.homework.assignment_07.ex1;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public int id;
    public List<Node> neighbours;

    public Node(int id){
        this.id = id;
        neighbours = new ArrayList<>();
    }

    public void addNeighbour(Node child){
        neighbours.add(child);
    }
}

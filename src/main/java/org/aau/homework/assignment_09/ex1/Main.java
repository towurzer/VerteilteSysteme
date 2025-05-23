package org.aau.homework.assignment_09.ex1;

import java.util.Map;
import java.util.TreeMap;

public class Main {
    static int peercount = 123;
    static int keyCount = 231;

    static int keyToFind = 132;

    public static void main(String[] args) {
        ChordSystem a = new ChordSystem(peercount, keyCount);
        TreeMap<Integer, Node> b = a.getNodes();

        b.get(b.firstKey()).searchForKey(keyToFind);
    }
}

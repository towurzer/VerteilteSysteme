package org.aau.homework.assignment_09;

import java.util.TreeMap;

public class Main1 {
    static int peerCount = 32;
    static int keyCount = 200;
    static int keyToFind = 132;

    public static void main(String[] args) {
        if (args.length == 2) {
            peerCount = Integer.parseInt(args[0]);
            keyCount = Integer.parseInt(args[1]);
        }

        System.out.println("Simulating CHORD with " + peerCount + " peers, and " + keyCount + " keys.");
        System.out.println("ID Namespace: 8-bit (0-255), Finger Table Entries: 8\n");

        ChordSystem chordSystem = new ChordSystem(peerCount, keyCount);
        TreeMap<Integer, Node> peers = chordSystem.getNodes();


        System.out.println("--- Generated Peer IDs ---");
        for (Integer peerId : peers.keySet()) {
            System.out.print(peerId + " ");
        }
        System.out.println("\n");


        System.out.println("--- Generated Keys ---");
        for (Node peer : peers.values()) {
            System.out.println("Peer " + peer.id + " stores keys: " + peer.storedKeys);
        }
        System.out.println("\n");

        // Search
        Node startingNode = peers.get(peers.firstKey());
        System.out.println("--- Search Path for key " + keyToFind + " (starting from Peer " + startingNode.id + ") ---");
        Node responsibleNode = startingNode.searchForKey(keyToFind);


        // Result
        System.out.println("--- Search Result ---");
        if (responsibleNode.storedKeys.contains(keyToFind)) {
            System.out.println("Key " + keyToFind + " is stored at Peer " + responsibleNode.id);
        } else {
            System.out.println("Key " + keyToFind + " is not available (Should be stored at Peer " + responsibleNode.id + ").");
        }
    }
}
package org.aau.homework.assignment_09;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class Main2 {
    static int peerCount = 10;
    static int keyCount = 50;

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

        // ------------------------------------
        System.out.println("Initial Chord System Setup done, simulating Join");

        // Generate new peer ID (must be unique)
        Random random = new Random();
        int newPeerId;
        do {
            newPeerId = random.nextInt(0, (int) (Math.pow(2, chordSystem.m) - 1));
        } while (peers.containsKey(newPeerId));

        // Select random existing peer as entry point
        List<Integer> existingPeerIds = new ArrayList<>(peers.keySet());
        int entryPointPeerId = existingPeerIds.get(random.nextInt(existingPeerIds.size()));

        // Call the join simulation
        chordSystem.join(newPeerId, entryPointPeerId);


        System.out.println("\n--- Final Peer IDs ---");
        for (Integer peerId : chordSystem.getNodes().keySet()) { // Get updated list
            System.out.print(peerId + " ");
        }
        System.out.println("\n");

        System.out.println("--- Final Keys Distribution ---");
        for (Node peer : chordSystem.getNodes().values()) {
            if (!peer.storedKeys.isEmpty()) {
                System.out.println("Peer " + peer.id + " stores keys: " + peer.storedKeys);
            } else {
                System.out.println("Peer " + peer.id + " stores no keys.");
            }
        }
        System.out.println("\n");
    }
}
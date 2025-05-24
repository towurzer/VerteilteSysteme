package org.aau.homework.assignment_09;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class Main2 {
    static int peerCount = 32;
    static int keyCount = 200;

    public static void main(String[] args) {
        if (args.length == 2) {
            peerCount = Integer.parseInt(args[0]);
            keyCount = Integer.parseInt(args[1]);
        }

        ChordSystem chordSystem = new ChordSystem(peerCount, keyCount);
        TreeMap<Integer, Node> peers = chordSystem.getNodes();


        System.out.println("--- Generated Peer IDs ---");
        chordSystem.printPeerIdList();
        System.out.println("--- Generated Keys ---");
        chordSystem.printKeyDistribution();

        // Generate new peer ID (must be unique)
        Random random = new Random();
        int newPeerId;
        do {
            newPeerId = random.nextInt(0, (int) (Math.pow(2, chordSystem.m) - 1));
        } while (peers.containsKey(newPeerId));

        // Select random existing peer as entry point
        List<Integer> existingPeerIds = new ArrayList<>(peers.keySet());
        int entryPointPeerId = existingPeerIds.get(random.nextInt(existingPeerIds.size()));

        // Let the peer Join
        System.out.println("Initial Chord System Setup done, simulating Join");
        chordSystem.join(newPeerId, entryPointPeerId);

        System.out.println("\n");
        System.out.println("--- Final Peer IDs ---");
        chordSystem.printPeerIdList();
        System.out.println("--- Final Keys Distribution ---");
        chordSystem.printKeyDistribution();
    }
}
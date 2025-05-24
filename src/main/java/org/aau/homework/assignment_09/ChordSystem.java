package org.aau.homework.assignment_09;

import java.util.*;

public class ChordSystem {
    TreeMap<Integer, Node> nodes;
    int m = 8; // ID Namespace - Bit Count

    int peerCount;
    int keyCount;

    public ChordSystem(int peerCount, int keyCount) {
        System.out.println("Simulating CHORD with " + peerCount + " peers, and " + keyCount + " keys.");
        System.out.println("ID Namespace: " + m + "-bit (0-" + ((int)Math.pow(2,m)-1) + "), Finger Table Entries: " + m + "\n");

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

        for (int i : peerIdSet) {
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
        for (Node node : nodes.values()) {
            node.buildFingerTables();
        }
    }

    private void distributeKeys(Set<Integer> keys) {
        for (int key : keys) {
            Node responsiblePeer = findSuccessor(key);
            responsiblePeer.addKey(key);
        }
    }

    public Node findSuccessor(int targetId) {
        for (int key : nodes.keySet()) {
            if (key >= targetId) {
                return nodes.get(key);
            }
        }
        return nodes.get(nodes.firstKey());
    }

    public TreeMap<Integer, Node> getNodes() {
        return nodes;
    }


    public static boolean isInCircularInterval(int value, int lowerBoundExclusive, int upperBoundInclusive, int ringSize) {
        lowerBoundExclusive = lowerBoundExclusive % ringSize;
        upperBoundInclusive = upperBoundInclusive % ringSize;
        value = value % ringSize;
        if (lowerBoundExclusive < upperBoundInclusive) {
            return value > lowerBoundExclusive && value <= upperBoundInclusive;
        } else if (lowerBoundExclusive > upperBoundInclusive) {
            // Interval wraps around (e.g., lower=250, upper=10 for ring size 256)
            return value > lowerBoundExclusive || value <= upperBoundInclusive;
        } else {
            // lowerBoundExclusive == upperBoundInclusive
            return true;
        }
    }

    public static boolean isInCircularIntervalExclusiveUpperBound(int value, int lowerBoundExclusive, int upperBoundInclusive, int ringSize) {
        lowerBoundExclusive = lowerBoundExclusive % ringSize;
        upperBoundInclusive = upperBoundInclusive % ringSize;
        value = value % ringSize;
        if (lowerBoundExclusive < upperBoundInclusive) {
            return value > lowerBoundExclusive && value < upperBoundInclusive;
        } else if (lowerBoundExclusive > upperBoundInclusive) {
            // Interval wraps around (e.g., lower=250, upper=10 for ring size 256)
            return value > lowerBoundExclusive || value < upperBoundInclusive;
        } else {
            // lowerBoundExclusive == upperBoundInclusive
            return false;
        }
    }


    public void join(int newPeerId, int entryPointPeerId) {
        System.out.println("\n--- Simulating Node Join ---");
        System.out.println("New Peer ID: " + newPeerId);
        System.out.println("Entry Point Peer ID: " + entryPointPeerId);

        if (nodes.containsKey(newPeerId)) {
            System.out.println("Error: Peer ID " + newPeerId + " already exists.");
            return;
        }
        Node entryPointNode = nodes.get(entryPointPeerId);
        if (entryPointNode == null) {
            System.out.println("Error: Entry point peer " + entryPointPeerId + " not found.");
        }

        Node newPeer = new Node(newPeerId, m, this);
        System.out.println("\n1. Initializing new node " + newPeerId + " (predecessor, successor):");


        // Find successor for newNode: newNode asks entryPointNode
        assert entryPointNode != null;
        System.out.println("New Node  '" + newPeerId + "' asks " + entryPointNode.id + " to find successor.");
        Node successorOfNewNode = entryPointNode.searchForKey(newPeerId);
        newPeer.successor = successorOfNewNode;
        System.out.println("New node " + newPeerId + " determined its successor: " + successorOfNewNode.id);

        // Set newNode's predecessor and update affected nodes
        Node oldPredecessorOfSuccessor = successorOfNewNode.predecessor;
        newPeer.predecessor = oldPredecessorOfSuccessor;
        System.out.println("New node " + newPeerId + " determined its predecessor: " + oldPredecessorOfSuccessor.id);

        // Update successor's predecessor
        successorOfNewNode.predecessor = newPeer;
        System.out.println("Successor " + successorOfNewNode.id + " updated its predecessor to " + newPeer.id);

        // Update old predecessor's successor
        if (oldPredecessorOfSuccessor.id != newPeer.id) {
            oldPredecessorOfSuccessor.successor = newPeer;
            System.out.println("Old predecessor " + oldPredecessorOfSuccessor.id + " updated its successor to " + newPeer.id);
        }

        // Add newNode to the system's list of nodes
        nodes.put(newPeer.id, newPeer);
        System.out.println("New node " + newPeerId + " is now part of the CHORD ring.");

        System.out.println("\n2. Initializing finger table for new node " + newPeerId + ":");
        newPeer.buildFingerTables();

        System.out.println("Finger table for " + newPeerId + ": " + newPeer.fingerTableToString());

        System.out.println("\n3. Updating other nodes' finger tables:");
        for (Node p : nodes.values()) {
            if (p.id == newPeer.id) continue;

            boolean fingerChanged = false;
            StringBuilder changes = new StringBuilder();
            for (int j = 0; j < m; j++) {
                int fingerStartId = (p.id + (1 << j)) % (int) Math.pow(2, m);
                Node oldFingerNode = p.fingerTable[j];
                Node newCorrectFingerNode = this.findSuccessor(fingerStartId); // Recalculate with newNode in system

                if (oldFingerNode != newCorrectFingerNode) {
                    p.fingerTable[j] = newCorrectFingerNode;
                    if (!fingerChanged) changes.append("Node ").append(p.id).append(" fingers updated: ");
                    changes.append("finger[").append(j).append("] for ").append(fingerStartId).append(" (was ").append(oldFingerNode.id).append(" -> now ").append(newCorrectFingerNode.id).append("); ");
                    fingerChanged = true;
                }
            }
            if (fingerChanged) {
                System.out.println(changes);
            }
        }

        System.out.println("\n4. Transferring keys to new node " + newPeerId + ":");
        List<Integer> movedKeys = new ArrayList<>();

        Node predecessorOfNewNode = newPeer.predecessor; // oldPredecessorOfSuccessor

        if (successorOfNewNode.id == newPeer.id) {
            System.out.println("New node is its own successor (e.g., was the only node). No keys to transfer from successor.");
        } else {
            System.out.println("Checking keys on node " + successorOfNewNode.id + ". Range for newNode " + newPeer.id + " is (" + (predecessorOfNewNode != null ? predecessorOfNewNode.id : "环绕点") + ", " + newPeer.id + "]");
            Iterator<Integer> keyIterator = successorOfNewNode.storedKeys.iterator();
            while (keyIterator.hasNext()) {
                Integer key = keyIterator.next();
                int predIdForRangeCheck;
                if (predecessorOfNewNode == null) {
                    if (nodes.size() == 2) {
                        predIdForRangeCheck = successorOfNewNode.id;
                    } else {
                        continue;
                    }
                } else {
                    predIdForRangeCheck = predecessorOfNewNode.id;
                }


                if (isInCircularInterval(key, predIdForRangeCheck, newPeer.id, (int) Math.pow(2, m))) {
                    newPeer.addKey(key);
                    movedKeys.add(key);
                    keyIterator.remove(); // Remove from sourceNodeForKeys.storedKeys
                }
            }
        }

        if (!movedKeys.isEmpty()) {
            System.out.println("Keys moved to node " + newPeerId + ": " + movedKeys);
        } else {
            System.out.println("No keys were moved to node " + newPeerId + ".");
        }
        System.out.println("--- Node Join Simulation Complete ---");
    }


    public void printPeerIdList() {
        for (Integer peerId : this.nodes.keySet()) {
            System.out.print(peerId + " ");
        }
        System.out.println("\n");
    }


    public void printKeyDistribution() {
        for (Node peer : this.nodes.values()) {
                if (!peer.storedKeys.isEmpty()) {
                    System.out.println("Peer " + peer.id + " stores keys: " + peer.storedKeys);
                } else {
                    System.out.println("Peer " + peer.id + " stores no keys.");
                }
            }
        System.out.println("\n");
    }
}
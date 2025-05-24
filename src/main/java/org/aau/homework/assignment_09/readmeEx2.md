Simulating CHORD with 32 peers, and 200 keys

ID Namespace: 8-bit (0-255), Finger Table Entries: 8


# --- Generated Peer IDs ---
7 9 10 31 35 41 49 53 56 71 74 76 88 132 150 151 163 165 168 179 182 195 214 224 226 227 228 229 231 239 240 242


# --- Generated Keys ---
* Peer 7 stores keys: [0, 1, 2, 5, 6, 243, 244, 245, 246, 248, 249, 250, 252]
* Peer 9 stores keys: [8, 9]
* Peer 10 stores keys: [10]
* Peer 31 stores keys: [11, 13, 14, 15, 16, 17, 18, 19, 22, 23, 24, 25, 26, 27, 28, 30, 31]
* Peer 35 stores keys: [32, 34, 35]
* Peer 41 stores keys: [36, 37, 38, 39, 41]
* Peer 49 stores keys: [42, 45, 47]
* Peer 53 stores keys: [50, 51, 52, 53]
* Peer 56 stores keys: [54, 55, 56]
* Peer 71 stores keys: [67, 69, 70, 57, 58, 59, 60, 62, 63]
* Peer 74 stores keys: [72, 73, 74]
* Peer 76 stores keys: [75, 76]
* Peer 88 stores keys: [80, 81, 82, 83, 84, 85, 86, 88, 78, 79]
* Peer 132 stores keys: [128, 129, 131, 132, 89, 90, 91, 92, 96, 98, 101, 102, 104, 107, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 120, 121, 122, 123, 124, 125, 126]
* Peer 150 stores keys: [133, 135, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150]
* Peer 151 stores keys: [151]
* Peer 163 stores keys: [160, 161, 162, 163, 152, 153, 154, 155, 156, 157, 158, 159]
* Peer 165 stores keys: [164, 165]
* Peer 168 stores keys: [166, 167, 168]
* Peer 179 stores keys: [176, 177, 178, 179, 169, 170, 173, 174, 175]
* Peer 182 stores keys: [181]
* Peer 195 stores keys: [193, 194, 195, 183, 184, 185, 186, 187, 189, 190, 191]
* Peer 214 stores keys: [196, 197, 198, 199, 200, 201, 202, 205, 206, 207, 208, 209, 210, 211, 213, 214]
* Peer 224 stores keys: [224, 215, 216, 217, 218, 220, 221, 222, 223]
* Peer 226 stores keys: [225, 226]
* Peer 227 stores keys: [227]
* Peer 228 stores keys: [228]
* Peer 229 stores keys: [229]
* Peer 231 stores keys: [230]
* Peer 239 stores keys: [233, 235, 236, 237, 239]
* Peer 240 stores keys: [240]
* Peer 242 stores keys: [241, 242]


> Initial Chord System Setup done, simulating Join

# --- Simulating Node Join ---
New Peer ID: 219

Entry Point Peer ID: 132

### 1. Initializing new node 219 (predecessor, successor):
   New Node  '219' asks 132 to find successor.
   
   Node 132 is searching for 219. (Finger Table: '[150, 150, 150, 150, 150, 165, 214, 7]')

   Node 214 is searching for 219. (Finger Table: '[224, 224, 224, 224, 231, 7, 31, 88]')

   New node 219 determined its successor: 224

   New node 219 determined its predecessor: 214

   Successor 224 updated its predecessor to 219

   Old predecessor 214 updated its successor to 219

   New node 219 is now part of the CHORD ring.

### 2. Initializing finger table for new node 219:
   Finger table for 219: [224, 224, 224, 227, 239, 7, 31, 132]

### 3. Updating other nodes' finger tables:
   Node 88 fingers updated: finger[7] for 216 (was 224 -> now 219);
   
   Node 151 fingers updated: finger[6] for 215 (was 224 -> now 219);

   Node 214 fingers updated: finger[0] for 215 (was 224 -> now 219); finger[1] for 216 (was 224 -> now 219); finger[2] for 218 (was 224 -> now 219);

### 4. Transferring keys to new node 219:
   Checking keys on node 224. Range for newNode 219 is (214, 219]
   
Keys moved to node 219: [215, 216, 217, 218]

>  --- Node Join Simulation Complete ---


# --- Final Peer IDs ---
7 9 10 31 35 41 49 53 56 71 74 76 88 132 150 151 163 165 168 179 182 195 214 219 224 226 227 228 229 231 239 240 242

# --- Final Keys Distribution ---
* Peer 7 stores keys: [0, 1, 2, 5, 6, 243, 244, 245, 246, 248, 249, 250, 252]
* Peer 9 stores keys: [8, 9]
* Peer 10 stores keys: [10]
* Peer 31 stores keys: [11, 13, 14, 15, 16, 17, 18, 19, 22, 23, 24, 25, 26, 27, 28, 30, 31]
* Peer 35 stores keys: [32, 34, 35]
* Peer 41 stores keys: [36, 37, 38, 39, 41]
* Peer 49 stores keys: [42, 45, 47]
* Peer 53 stores keys: [50, 51, 52, 53]
* Peer 56 stores keys: [54, 55, 56]
* Peer 71 stores keys: [67, 69, 70, 57, 58, 59, 60, 62, 63]
* Peer 74 stores keys: [72, 73, 74]
* Peer 76 stores keys: [75, 76]
* Peer 88 stores keys: [80, 81, 82, 83, 84, 85, 86, 88, 78, 79]
* Peer 132 stores keys: [128, 129, 131, 132, 89, 90, 91, 92, 96, 98, 101, 102, 104, 107, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 120, 121, 122, 123, 124, 125, 126]
* Peer 150 stores keys: [133, 135, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150]
* Peer 151 stores keys: [151]
* Peer 163 stores keys: [160, 161, 162, 163, 152, 153, 154, 155, 156, 157, 158, 159]
* Peer 165 stores keys: [164, 165]
* Peer 168 stores keys: [166, 167, 168]
* Peer 179 stores keys: [176, 177, 178, 179, 169, 170, 173, 174, 175]
* Peer 182 stores keys: [181]
* Peer 195 stores keys: [193, 194, 195, 183, 184, 185, 186, 187, 189, 190, 191]
* Peer 214 stores keys: [196, 197, 198, 199, 200, 201, 202, 205, 206, 207, 208, 209, 210, 211, 213, 214]
* Peer 219 stores keys: [215, 216, 217, 218]
* Peer 224 stores keys: [224, 220, 221, 222, 223]
* Peer 226 stores keys: [225, 226]
* Peer 227 stores keys: [227]
* Peer 228 stores keys: [228]
* Peer 229 stores keys: [229]
* Peer 231 stores keys: [230]
* Peer 239 stores keys: [233, 235, 236, 237, 239]
* Peer 240 stores keys: [240]
* Peer 242 stores keys: [241, 242]



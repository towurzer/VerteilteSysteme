# How to run

To start the server, just run main function of the FileServer class. As server directory serves the "out/directory" 
directory inside the project

The client can be started by running the main function in the Client class.
Connecting to the server works as follows:
1. Enter the IP address
2. Enter the port number 

If the connection is successful you will be prompted a "Connection successful" message.
Everything else is described as messages in the console.

# Tests

The code was tested with two laptops. The first one ran the server and client. The other acted only
as client. 

Both laptops could list all the files on the server, as well as downloading single files.

# Advantages/Disadvantages of RMI vs. Socket Implementation

## Advantages
- It is possible to work with objects and their methods instead of manually parsing the messages between server and client 
- Manages connections and threads automatically 
- Built-in exception handling with RemoteException

## Disadvantages
- More difficult to setup since a registry is required
- More difficult to setup a connection with a client on a different PC due to firewall or other security settings
- Potential problems with e.g. classloading


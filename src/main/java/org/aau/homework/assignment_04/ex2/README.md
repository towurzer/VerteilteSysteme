# How to run

To start the server, just run main function of the Server class.
The client can be started by running the main function in the Client class.

## Output of a sample session with 2 Users (output from the perspective of user 1)
Please enter host ip address:
127.0.0.1
Please enter port number:
8080
Connection successful!

----- Simple Document Store ----

Following commands are available:
1) "create" -> Opens document creator
2) "list" -> Lists all available documents
3) "find <title>" -> Finds all documents matching the given title
4) "get <id>" -> Retrieves the document with the given Id
5) "help" -> Print this message
6) "exit" -> Closes the application 
   Please enter desired action: 
   list 

---- All available documents ----
Hello World (ID: a264df39-332d-4bcf-9203-1ea04d15f928)
fhsjakdf (ID: 9fc74c04-7dbf-4701-a387-bc71ae6d26cd)
find Hello

Trying to find documents matching the given title: Hello...
-------------
ID: a264df39-332d-4bcf-9203-1ea04d15f928
Title: Hello World
Authors: Philipp Wolfger,  Tobias Wurzer
Created at: 2025-04-05T01:51:23.776496+02:00
--------------
Content:

Hello world is a programm
which programmers write when they are learning a new language

list

---- All available documents ----
Hello World (ID: a264df39-332d-4bcf-9203-1ea04d15f928)
fhsjakdf (ID: 9fc74c04-7dbf-4701-a387-bc71ae6d26cd)
Document by Client 2 (ID: ef2cffa6-0b38-434a-bf24-df5bf4a1ab6f)
get ef2cffa6-0b38-434a-bf24-df5bf4a1ab6f

Trying to find documents matching the given id: ef2cffa6-0b38-434a-bf24-df5bf4a1ab6f...
-------------
ID: ef2cffa6-0b38-434a-bf24-df5bf4a1ab6f
Title: Document by Client 2
Authors: Client 2
Created at: 2025-04-05T01:56:04.825054+02:00
--------------
Content:

This document was created by client 2 to test the connection.
Have a nice day!

find Client 2

Trying to find documents matching the given title: Client 2...
-------------
ID: ef2cffa6-0b38-434a-bf24-df5bf4a1ab6f
Title: Document by Client 2
Authors: Client 2
Created at: 2025-04-05T01:56:04.825054+02:00
--------------
Content:

This document was created by client 2 to test the connection.
Have a nice day!

create

Welcome to the Document Creator:
-------------------------
Please enter title of document:
Document by client 1
Please enter the names of the authors (seperated by ','):
Client 1
Please enter the content of the document now.
(Hint: It can be multiline, to submit please enter an empty row)
This is a document by client 1.
It also supports multiline content!

Thank you, creating document now...
Successfully created document (Available under ID ac244fbe-a4d6-41c0-a5a8-7d8ce2a93fa3)
list

---- All available documents ----
Hello World (ID: a264df39-332d-4bcf-9203-1ea04d15f928)
fhsjakdf (ID: 9fc74c04-7dbf-4701-a387-bc71ae6d26cd)
Document by Client 2 (ID: ef2cffa6-0b38-434a-bf24-df5bf4a1ab6f)
Document by client 1 (ID: ac244fbe-a4d6-41c0-a5a8-7d8ce2a93fa3)
get ac244fbe-a4d6-41c0-a5a8-7d8ce2a93fa3

Trying to find documents matching the given id: ac244fbe-a4d6-41c0-a5a8-7d8ce2a93fa3...
-------------
ID: ac244fbe-a4d6-41c0-a5a8-7d8ce2a93fa3
Title: Document by client 1
Authors: Client 1
Created at: 2025-04-05T02:00:41.240812+02:00
--------------
Content:

This is a document by client 1
It also supports multiline content!

exit

Shutdown application, goodbye!


## Output of a sample session with 2 Users (output from the perspective of user 2)
Please enter host ip address:
127.0.0.1
Please enter port number:
8080
Connection successful!

----- Simple Document Store ----

Following commands are available:
1) "create" -> Opens document creator
2) "list" -> Lists all available documents
3) "find <title>" -> Finds all documents matching the given title
4) "get <id>" -> Retrieves the document with the given Id
5) "help" -> Print this message
6) "exit" -> Closes the application
   Please enter desired action:
   create

Welcome to the Document Creator:
-------------------------
Please enter title of document:
Document by Client 2
Please enter the names of the authors (seperated by ','):
Client 2  
Please enter the content of the document now.
(Hint: It can be multiline, to submit please enter an empty row)
This document was created by client 2 to test the connection.
Have a nice day!

Thank you, creating document now...
Successfully created document (Available under ID ef2cffa6-0b38-434a-bf24-df5bf4a1ab6f)
list

---- All available documents ----
Hello World (ID: a264df39-332d-4bcf-9203-1ea04d15f928)
fhsjakdf (ID: 9fc74c04-7dbf-4701-a387-bc71ae6d26cd)
Document by Client 2 (ID: ef2cffa6-0b38-434a-bf24-df5bf4a1ab6f)
Document by client 1 (ID: ac244fbe-a4d6-41c0-a5a8-7d8ce2a93fa3)


Unknown command:
get ac244fbe-a4d6-41c0-a5a8-7d8ce2a93fa3

Trying to find documents matching the given id: ac244fbe-a4d6-41c0-a5a8-7d8ce2a93fa3...
-------------
ID: ac244fbe-a4d6-41c0-a5a8-7d8ce2a93fa3
Title: Document by client 1
Authors: Client 1
Created at: 2025-04-05T02:00:41.240812+02:00
--------------
Content:

This is a document by client 1
It also supports multiline content!

exit

Shutdown application, goodbye!

# How to run

To start the server, just run main function of the Server class.
The client can be started by running the main function in the Client class.

# Tests

The code was tested with two laptops. The first one ran the server and client. The other acted only
as client. The following paragraph document the output of a sample session

## Output of a sample session with 2 Users (output from the perspective of user 2)
Connection successful  
New user detected, subscribing to everything!  
Subscribing to topic everything....  
Successfully subscribed to everything  
  
----- News Sharing Platform ----

Following commands are available:
1) "subscribe <topic>" -> Subscribes you to the specified topic
2) "unsubscribe <topic>" -> Unsubscribes you from the specified topic
3) "info" -> tells you which topics you are subscribed to
4) "topics" -> lists all available topics
5) "publish" -> lets you publish an article
6) "display" -> displays all the articles you are subscribed to
7) "display <topic>" -> displays all the articles published to this topic
8) "help" -> Print this message
9) "exit" -> Closes the application  
Please enter desired action:  
publish




Welcome to the Article Editor:
-------------------------
Please enter the name the article should be published under:  
User 2  
Welcome User 2. Please enter your Article now.  
(Hint: It can be multiline, to submit please enter an empty row)  
This is the Article of User 2  


Perfect: Please do now enter all the keywords that would fit your article seperated by ','  
user2  
  
And that's it. Publishing your article now...  
Article published successfully  
Anything else? Enter here:  
fetch  

Fetching news you are subscribed to...  
News Articles:  
---------------------------  
Author: User 1  
Topics: user1, article, useless  
---Text---  
This is my beautifull article  
That spans two lines  
---------------------------  
  
---------------------------  
Author: User 1  
Topics: user1, short  
---Text---  
This is another beautifull article  
---------------------------  
  
---------------------------  
Author: User 2  
Topics: user2  
---Text---  
This is the Article  
of User 2  
---------------------------  
  
Anything else? Enter here:  
subscribe user1  
  
Subscribing to topic user1....  
Successfully subscribed to user1  
Anything else? Enter here:  
info  

Fetching info about you...  
You are subscribed to: user1, everything  
Anything else? Enter here:  
unsubscribe everything  
  
Unsubscribing from everything....  
Successfully unsubscribed from everything  
Anything else? Enter here:  
display u  

Fetching news with keyword u...  
News Articles:  
Anything else? Enter here:  
display user2  
  
Fetching news with keyword user2...  
News Articles:  
---------------------------  
Author: User 2  
Topics: user2  
---Text---  
This is the Article of User 2  
---------------------------  
  
Anything else? Enter here:  
display  

Fetching news you are subscribed to...  
News Articles:  
---------------------------  
Author: User 1  
Topics: user1, article, useless  
---Text---  
This is my beautifull article  
That spans two lines  
---------------------------  
  
---------------------------  
Author: User 1  
Topics: user1, short  
---Text---  
This is another beautifull article  
---------------------------  
  
Anything else? Enter here:  
exit  
  
Shutdown application, goodbye!  
  
Process finished with exit code 0  



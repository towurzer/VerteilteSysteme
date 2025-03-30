package homework.assignment_03.publish_subscribe_system.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface NewsPlatform extends Remote {
    boolean subscribe(UUID userID, String topic) throws RemoteException;

    boolean unsubscribe(UUID userID, String topic) throws RemoteException;

    void publish(String[] topic, UUID authorID, String author, String text) throws RemoteException;

    List<NewsArticle> getNews(UUID userID) throws RemoteException;

    List<NewsArticle> getNews(String topic) throws RemoteException;

    Set<String> getSubscriptions(UUID userID) throws RemoteException;

    List<String> getTopics() throws RemoteException;
}

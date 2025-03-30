package homework.assignment_03.publish_subscribe_system.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class NewsPlatformImpl implements NewsPlatform {
    protected HashMap<UUID, Set<String>> userSubscriptions = new HashMap<>();
    protected HashMap<String, List<NewsArticle>> articlesPerTopics = new HashMap<>();

    @Override
    public boolean subscribe(UUID userID, String topic) throws RemoteException {
        return userSubscriptions.computeIfAbsent(userID, k -> new HashSet<>()).add(topic);
    }

    @Override
    public boolean unsubscribe(UUID userID, String topic) throws RemoteException {
        return userSubscriptions.getOrDefault(userID, Collections.emptySet()).remove(topic);
    }

    @Override
    public void publish(String[] topics, UUID authorID, String author, String text) throws RemoteException {
        NewsArticle newNewsArticle = new NewsArticle(author, authorID, topics, text);

        for (String topic : topics) {
            articlesPerTopics.computeIfAbsent(topic, k -> new ArrayList<>()).add(newNewsArticle);
        }

        articlesPerTopics.computeIfAbsent("everything", k -> new ArrayList<>()).add(newNewsArticle);
    }

    @Override
    public List<NewsArticle> getNews(UUID userID) throws RemoteException {
        List<NewsArticle> articleList = new ArrayList<>();
        for (String topic : userSubscriptions.getOrDefault(userID, Collections.emptySet())) {
            articleList.addAll(getNews(topic));
        }
        return articleList;
    }

    @Override
    public List<NewsArticle> getNews(String topic) throws RemoteException {
        return articlesPerTopics.getOrDefault(topic, Collections.emptyList());
    }

    @Override
    public Set<String> getSubscriptions(UUID userID) throws RemoteException {
        return userSubscriptions.getOrDefault(userID, Collections.emptySet());
    }

    @Override
    public List<String> getTopics() throws RemoteException {
        return Optional.of(articlesPerTopics.keySet()).orElse(new HashSet<>()).stream().toList();
    }
}

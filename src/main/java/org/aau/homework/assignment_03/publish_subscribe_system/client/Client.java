package org.aau.homework.assignment_03.publish_subscribe_system.client;

import org.aau.homework.assignment_03.publish_subscribe_system.server.NewsArticle;
import org.aau.homework.assignment_03.publish_subscribe_system.server.NewsPlatform;
import org.aau.homework.assignment_03.publish_subscribe_system.server.NewsPlatform;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

public class Client {
    private static final String DEFAULTIP = "127.0.0.1";
    private static final int DEFAULTPORT = 8080;

    private static final UUID CLIENT_ID = UUID.randomUUID();


    public static void main(String[] args) {
        String hostIp = (args.length == 2) ? args[0] : DEFAULTIP;
        int port = (args.length == 2) ? Integer.parseInt(args[1]) : DEFAULTPORT;
        try (Scanner scanner = new Scanner(System.in)) {
            // ------------ Setup ---------------
            // Getting the registry
            //Registry registry = LocateRegistry.getRegistry(PORT);
            Registry registry = LocateRegistry.getRegistry(hostIp, port);

            // looking up the registry for the remote object
            NewsPlatform newsPlatform = (NewsPlatform) registry.lookup("newsPlatform");

            System.out.println("Connection successful");

            // ----------- actions ------------
            System.out.println("New user detected, subscribing to everything!");
            subscribe(newsPlatform, "everything");
            help();

            while (true) {
                String command = scanner.nextLine().trim();
                System.out.print("\n");
                if (command.equals("exit")) {
                    System.out.println("Shutdown application, goodbye!");
                    break;
                } else if (command.startsWith("subscribe")) {
                    String topic = command.substring("subscribe".length()).trim();
                    subscribe(newsPlatform, topic);
                } else if (command.startsWith("unsubscribe")) {
                    String topic = command.substring("unsubscribe".length()).trim();
                    unsubscribe(newsPlatform, topic);
                } else if (command.equals("info")) {
                    getInfo(newsPlatform);
                } else if (command.equals("topics")) {
                    listTopics(newsPlatform);
                } else if (command.equals("publish")) {
                    publishNewsArticle(newsPlatform, scanner);
                } else if (command.equals("display") || command.equals("fetch")) {
                    displayNews(newsPlatform);
                } else if (command.startsWith("display")) {
                    String topic = command.substring("display".length()).trim();
                    displayNews(newsPlatform, topic);
                } else if (command.equals("help")) {
                    help();
                } else {
                    System.out.println("Unknown command (To get more information input help)");
                    continue;
                }
                System.out.println("Anything else? Enter here: ");
            }
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void help() {
        System.out.println(
                """

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
                        Please enter desired action:"""
        );
    }

    private static void subscribe(NewsPlatform newsPlatform, String topic) throws RemoteException {
        System.out.printf("Subscribing to topic %s....\n", topic);
        //System.out.println(newsPlatform.subscribe(CLIENT_ID, topic) ? "Successfully subscribed to %s".formatted(topic) : "Failed subscription, you are already subscribed to %s".formatted(topic));
        if (newsPlatform.subscribe(CLIENT_ID, topic)) {
            System.out.printf("Successfully subscribed to %s\n", topic);
            return;
        }
        System.out.printf("Failed subscription, you are already subscribed to %s\n", topic);
    }

    private static void unsubscribe(NewsPlatform newsPlatform, String topic) throws RemoteException {
        System.out.printf("Unsubscribing from %s....\n", topic);
        if (newsPlatform.unsubscribe(CLIENT_ID, topic)) {
            System.out.printf("Successfully unsubscribed from %s\n", topic);
            return;
        }
        System.out.printf("Failed unsubscription, you are not subscribed to %s\n", topic);
    }

    private static void displayNews(NewsPlatform newsPlatform, String topic) throws RemoteException {
        System.out.printf("Fetching news with keyword %s...\n", topic);
        List<NewsArticle> newsArticleList = newsPlatform.getNews(topic);

        System.out.println("News Articles: ");
        newsArticleList.forEach(System.out::println);
    }

    private static void displayNews(NewsPlatform newsPlatform) throws RemoteException {
        System.out.println("Fetching news you are subscribed to...");
        List<NewsArticle> newsArticleList = newsPlatform.getNews(CLIENT_ID);

        System.out.println("News Articles: ");
        newsArticleList.forEach(System.out::println);
    }

    private static void publishNewsArticle(NewsPlatform newsPlatform, Scanner scanner) throws RemoteException {
        System.out.println("\n\n");
        System.out.println("Welcome to the Article Editor:\n-------------------------");
        System.out.println("Please enter the name the article should be published under:");
        String name = scanner.nextLine().trim();
        System.out.printf("""
                Welcome %s. Please enter your Article now.
                (Hint: It can be multiline, to submit please enter an empty row)
                """, name);
        String text = getText(scanner);
        System.out.println("\n Perfect: Please do now enter all the keywords that would fit your article seperated by ','");
        String[] keywords = scanner.nextLine().split(", *");

        System.out.println("\n And that's it. Publishing your article now...");
        newsPlatform.publish(keywords, CLIENT_ID, name, text);
        System.out.println("Article published successfully");

    }

    private static void getInfo(NewsPlatform newsPlatform) throws RemoteException {
        System.out.println("Fetching info about you...");

        Set<String> subscriptionset = newsPlatform.getSubscriptions(CLIENT_ID);
        System.out.printf("You are subscribed to: %s\n", String.join(", ", subscriptionset));
    }

    private static String getText(Scanner scanner) {
        StringBuilder article = new StringBuilder();
        String line;

        while (true) {
            line = scanner.nextLine();
            if (line.isEmpty()) {  // empty line -> done
                break;
            }
            article.append(line).append("\n"); // build article
        }
        return article.toString().trim(); // Remove trailing newline and return
    }

    private static void listTopics(NewsPlatform newsPlatform) throws RemoteException {
        System.out.println("Fetching all available topics...");
        System.out.println("----- Available Topics -------");
        newsPlatform.getTopics().forEach(System.out::println);
    }

}

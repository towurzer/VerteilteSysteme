package org.aau.homework.assignment_05.tobi.ex1;

import org.aau.homework.assignment_05.tobi.reusables.ApiClient;
import org.aau.homework.assignment_05.tobi.reusables.Article;

import java.util.List;
import java.util.Scanner;

public class NewsClient {

    public static void main(String[] args) {
        try (
                Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Welcome to NewsIo!\nPlease enter a topic you want to read more about:");
            String topic = scanner.nextLine();
            System.out.printf("--- Querying Articles for '%s' -----\n", topic);
            List<Article> articles = ApiClient.queryNewsDataForTopic(topic);

            if (articles.isEmpty())
                return;

            if (articles.size() == 1 && articles.get(0).isNull()) {
                System.out.println("No articles found :(");
                return;
            }

            for (Article article : articles)
                System.out.println(article);
        }
    }


}

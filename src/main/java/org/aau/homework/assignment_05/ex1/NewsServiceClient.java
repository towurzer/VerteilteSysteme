package org.aau.homework.assignment_05.ex1;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import org.aau.homework.assignment_05.util.NewsClient;

import java.util.Scanner;

public class NewsServiceClient {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in); Client client = ClientBuilder.newClient();) {
            System.out.println("""
                    Welcome to the News Service!
                    Please enter the desired topic:""");
            String topic = scanner.nextLine();
            System.out.printf("--- Found Articles containing topic '%s' -----%n", topic);
            NewsClient.getNewsArticlesForTopic(topic).forEach(System.out::println);
        }
    }
}

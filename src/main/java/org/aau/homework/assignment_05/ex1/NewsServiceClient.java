package org.aau.homework.assignment_05.ex1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.aau.homework.assignment_05.ex1.model.Article;

import java.util.Scanner;

public class NewsServiceClient {

    public static final String NEWS_SERVICE_URL = "https://newsdata.io/api/1";
    public static final String API_KEY = "pub_819205d3e3741aaea1c3d89f0469d91f0ab1c";

    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in); Client client = ClientBuilder.newClient();) {
            System.out.println("""
                Welcome to the News Service!
                Please enter the desired topic:""");
            String topic = scanner.nextLine();

            WebTarget webTarget = client.target(NEWS_SERVICE_URL)
                    .path("/latest")
                    .queryParam("apikey", API_KEY)
                    .queryParam("q", topic);

            JsonNode response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).get(JsonNode.class);

            ArrayNode results = (ArrayNode) response.get("results");

            System.out.printf("--- Found Articles containing topic '%s' -----%n", topic);
            for (JsonNode articleNode : results) {
                System.out.println(new Article(articleNode));
            }

        }
    }
}

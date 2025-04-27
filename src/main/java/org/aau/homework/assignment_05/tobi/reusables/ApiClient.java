package org.aau.homework.assignment_05.tobi.reusables;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

public class ApiClient {

    private static final String NEWS_SERVICE_URL = "https://newsdata.io/api/1";
    private static final String API_KEY = System.getenv("NEWSDATA_API_KEY");

    public static List<Article> queryNewsDataForTopic(String topic) {
        if (API_KEY == null || API_KEY.trim().isEmpty()) {
            System.err.println("No API key found");
            return new ArrayList<>();
        }

        try (Client client = ClientBuilder.newClient()) {
            WebTarget webTarget = client
                    .target(NEWS_SERVICE_URL)
                    .path("/news")
                    .queryParam("apikey", API_KEY)
                    .queryParam("q", topic)
                    .queryParam("language", "en");

            JsonNode response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).get(JsonNode.class);
            ArrayNode results = (ArrayNode) response.get("results");
            var articles = new ArrayList<Article>();
            results.forEach(article -> articles.add(new Article(article)));
            if (articles.isEmpty())
                articles.add(new Article(null, null, null));
            return articles;
        }
    }
}

package org.aau.homework.assignment_05.philipp.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

public class NewsClient {

    private static final String NEWS_SERVICE_URL = "https://newsdata.io/api/1";
    private static final String API_KEY = "pub_819205d3e3741aaea1c3d89f0469d91f0ab1c";

    public static List<Article> getNewsArticlesForTopic(String topic) {
        try (Client client = ClientBuilder.newClient()) {
            WebTarget webTarget = client.target(NEWS_SERVICE_URL)
                    .path("/latest")
                    .queryParam("apikey", API_KEY)
                    .queryParam("q", topic);

            JsonNode response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).get(JsonNode.class);
            ArrayNode results = (ArrayNode) response.get("results");
            var articles = new ArrayList<Article>();
            for (JsonNode articleNode : results) {
                articles.add(new Article(articleNode));
            }
            return articles;
        }
    }
}

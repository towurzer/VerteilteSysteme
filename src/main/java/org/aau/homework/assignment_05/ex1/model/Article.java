package org.aau.homework.assignment_05.ex1.model;

import com.fasterxml.jackson.databind.JsonNode;

public record Article(String title, String publishingDate, String link) {

    public Article(JsonNode article) {
        this(
            article.get("title").asText(),
            article.get("pubDate").asText(),
            article.get("link").asText()
        );
    }

    @Override
    public String toString() {
        return """
                Title: %s
                Published at: %s,
                Link: %s
                """.formatted(title, publishingDate, link);
    }
}

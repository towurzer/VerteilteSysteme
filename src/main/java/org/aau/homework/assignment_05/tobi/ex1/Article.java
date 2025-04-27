package org.aau.homework.assignment_05.tobi.ex1;

import com.fasterxml.jackson.databind.JsonNode;

public record Article (JsonNode article, String title, String publishingDate, String link){
    public Article(JsonNode article) {
        this(
                article, // Safe whole JSON node
                article.get("title").asText(), // safe specific things for easier query
                article.get("pubDate").asText(),
                article.get("link").asText()
        );
    }


    @Override
    public String toString() {
        return """
                --- '%s' ---
                published: %s,
                read more: %s
                --- --- --- ---
                """.formatted(title, publishingDate, link);
    }
}

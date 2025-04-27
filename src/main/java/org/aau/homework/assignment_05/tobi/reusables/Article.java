package org.aau.homework.assignment_05.tobi.reusables;

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

    public Article(String title, String publishingDate, String link){
        this (
                null,
                title,
                publishingDate,
                link
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

    public boolean isNull(){
        if(article == null && title == null && publishingDate == null && link == null)
            return true;
        return false;
    }
}

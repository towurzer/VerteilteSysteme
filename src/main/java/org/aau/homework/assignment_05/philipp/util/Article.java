package org.aau.homework.assignment_05.philipp.util;

import com.fasterxml.jackson.databind.JsonNode;

public record Article(String title, String publishingDate, String link) {

    public Article(JsonNode article) {
        this(
                article.get("title").asText(),
                article.get("pubDate").asText(),
                article.get("link").asText()
        );
    }

    public String toHTML() {
        return """
                <div style="border:1px solid black;border-radius:4px;padding:4px;margin:12px 4px">
                    <strong>Title</strong>
                    <br>
                    <p>%s</p>
                    <strong>Published at</strong>
                    <br>
                    <p>%s</p>
                    <a href="%s"><strong>Link</strong></a>
                </div>
                """.formatted(title, publishingDate, link);
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

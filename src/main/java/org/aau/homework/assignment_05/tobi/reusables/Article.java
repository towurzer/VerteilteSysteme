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
        if (article.isNull())
            return "Empty Article";


        return """
                --- '%s' ---
                published: %s,
                read more: %s
                --- --- --- ---
                """.formatted(title, publishingDate, link);
    }

    public String toHTML() {
        if (article.isNull())
            return """
                    <div style="border:1px solid black;border-radius:4px;padding:4px;margin:12px 4px">
                    <strong>No data found</strong>
                    </div>
                    """;

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

    public boolean isNull(){
        return article == null && title == null && publishingDate == null && link == null;
    }
}

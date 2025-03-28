package homework.assignment_03.publish_subscribe_system.server;

import java.io.Serializable;
import java.util.UUID;

public record NewsArticle(String author, UUID authorID, String[] topics, String text) implements Serializable {
    @Override
    public String toString() {
        return """
                ---------------------------
                Author: %s
                Topics: %s
                ---Text---
                %s
                ---------------------------
                """.formatted(author, String.join(", ", topics), text);
    }
}

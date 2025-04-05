package homework.assignment_04.ex2.api;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public record Document(UUID id, OffsetDateTime createdAt, String title, List<String> authors,
                       String content) implements Serializable {

    public Document {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }

        if (authors == null || authors.isEmpty()) {
            throw new IllegalArgumentException("Authors cannot be null or empty");
        }

        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
    }

    public Document(String title, List<String> authors, String content) {
        this(UUID.randomUUID(), OffsetDateTime.now(), title, authors, content);
    }

    @Override
    public String toString() {
        return """
                -------------
                ID: %s
                Title: %s
                Authors: %s
                Created at: %s
                --------------
                Content:
                                
                %s
                """.formatted(id, title, String.join(", ", authors), createdAt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME), content);
    }
}

package homework.assignment_04.ex2.server.service;

import homework.assignment_04.ex2.api.Document;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class DocumentStore {

    private final List<Document> documents = new CopyOnWriteArrayList<>();
    private static DocumentStore INSTANCE;

    private DocumentStore() {
    }

    public static DocumentStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DocumentStore();
        }
        return INSTANCE;
    }

    public synchronized void store(Document document) {
        documents.add(document);
    }

    public Document getById(UUID documentId) {
        return documents.stream()
                .filter(document -> document.id().equals(documentId))
                .findFirst()
                .orElse(null);
    }

    public List<Document> findByTitle(String titlePattern) {
        return documents.stream()
                .filter(document -> document.title().toLowerCase().contains(titlePattern.toLowerCase()))
                .toList();
    }

    public List<Document> findAll() {
        return documents;
    }
}

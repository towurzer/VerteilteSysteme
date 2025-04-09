package org.aau.homework.assignment_04.ex2.server.api.impl;

import org.aau.homework.assignment_04.ex2.api.Document;
import org.aau.homework.assignment_04.ex2.api.DocumentStoreApi;
import org.aau.homework.assignment_04.ex2.server.service.DocumentStore;
import org.aau.homework.assignment_04.ex2.server.service.DocumentStore;

import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public class DocumentStoreApiImpl implements DocumentStoreApi {

    private static final DocumentStore documentStore = DocumentStore.getInstance();

    @Override
    public synchronized UUID createDocument(String title, List<String> authors, String content) throws RemoteException {
        var document = new Document(title, authors, content);
        documentStore.store(document);
        return document.id();
    }

    @Override
    public synchronized Document getDocument(UUID documentId) throws RemoteException {
        return documentStore.getById(documentId);
    }

    @Override
    public synchronized List<Document> findDocumentByTitle(String titlePattern) throws RemoteException {
        return documentStore.findByTitle(titlePattern);
    }

    @Override
    public List<Document> listDocuments() throws RemoteException {
        return documentStore.findAll();
    }
}

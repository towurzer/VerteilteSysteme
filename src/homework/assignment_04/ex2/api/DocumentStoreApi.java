package homework.assignment_04.ex2.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface DocumentStoreApi extends Remote {
    String registeredName = "documentStoreApi";

    UUID createDocument(String title, List<String> authors, String content) throws RemoteException;
    Document getDocument(UUID documentId) throws RemoteException;
    List<Document> findDocumentByTitle(String title) throws RemoteException;
    List<Document> listDocuments() throws RemoteException;
}

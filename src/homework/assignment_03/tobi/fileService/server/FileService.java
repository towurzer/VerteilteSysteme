package homework.assignment_03.tobi.fileService.server;

import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface FileService extends Remote {
    List<String> listFiles() throws RemoteException;
    byte[] loadFile(String fileName) throws RemoteException, FileNotFoundException;
}

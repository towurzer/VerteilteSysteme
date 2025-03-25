package homework.assignment_03.ex1;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface FileSystem extends Remote {
    List<String> listFiles() throws RemoteException;

    String loadFile(String fileName) throws RemoteException;
}

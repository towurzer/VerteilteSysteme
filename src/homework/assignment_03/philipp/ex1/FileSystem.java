package homework.assignment_03.philipp.ex1;

import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface FileSystem extends Remote {

    /**
     *
     * @return Returns the list of names for all available files in the directory
     * @throws RemoteException
     */
    List<String> listFiles() throws RemoteException;

    /**
     *
     * @param fileName The case-sensitive name of file including the file type
     * @return If the file exists, returns its content
     * @throws RemoteException
     * @throws FileNotFoundException If no file with the given name could be found
     */
    byte[] loadFile(String fileName) throws RemoteException, FileNotFoundException;
}

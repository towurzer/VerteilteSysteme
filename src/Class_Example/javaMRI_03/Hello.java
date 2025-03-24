package Class_Example.javaMRI_03;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote {
    void printMSg() throws RemoteException;
}

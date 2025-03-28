package homework.assignment_03.publish_subscribe_system.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NewsPlattform extends Remote {
    boolean subscribe(String topic) throws RemoteException;
    boolean unsubscribe(String topic) throws RemoteException;
    void publish(String topic, String author, String text) throws RemoteException;
}

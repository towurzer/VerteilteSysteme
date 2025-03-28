package homework.assignment_03.publish_subscribe_system.server;

import java.rmi.RemoteException;

public class NewsPlattformImpl implements NewsPlattform {
    @Override
    public boolean subscribe(String topic) throws RemoteException {
        return false;
    }

    @Override
    public boolean unsubscribe(String topic) throws RemoteException {
        return false;
    }

    @Override
    public void publish(String topic, String author, String text) throws RemoteException {

    }
}

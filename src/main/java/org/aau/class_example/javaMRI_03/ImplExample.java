package org.aau.class_example.javaMRI_03;

import java.rmi.RemoteException;

public class ImplExample implements Hello{
    @Override
    public void printMSg() throws RemoteException {
        System.out.println("This is an example RMI program");
    }
}

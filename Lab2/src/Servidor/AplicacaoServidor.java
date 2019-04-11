/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

/**
 *
 * @author higor
 */
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class AplicacaoServidor {

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {

        int porta = 4444;
        InterfaceServidor server = new Servidor();
        InterfaceServidor stub = (InterfaceServidor) UnicastRemoteObject.exportObject(server, porta);

        Registry r = LocateRegistry.createRegistry(porta);
        r.rebind("InterfaceServidor", stub);
        System.out.println(r.REGISTRY_PORT);

    }

}

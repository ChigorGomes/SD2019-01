/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

/**
 *
 * @author higor
 */
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceCliente extends Remote {

    public void notificacao(String s) throws RemoteException;
}

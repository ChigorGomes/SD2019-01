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
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import Cliente.InterfaceCliente;

public interface InterfaceServidor extends Remote {

    public void insereMoeda(String moeda, String cotacao) throws RemoteException;

    public ArrayList<String[]> visualizaMoeda() throws RemoteException;

    public void removeMoeda(String moeda) throws RemoteException;

    public void alteraMoeda(String moeda, String alteracao) throws RemoteException;

    public void addCliente(InterfaceCliente cliente) throws RemoteException;

    public void removeCliente(InterfaceCliente cliente) throws RemoteException;

}

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
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.RemoteServer;
import java.util.ArrayList;
import Cliente.InterfaceCliente;

public class Servidor implements InterfaceServidor {

    private static final long serialVersionUID = 1L;
    private ArrayList<String[]> moedas = new ArrayList<String[]>();
    private ArrayList<InterfaceCliente> list = new ArrayList<InterfaceCliente>();

    protected Servidor() throws RemoteException, MalformedURLException, NotBoundException {
    }

    @Override
    public void insereMoeda(String moeda, String cotacao) throws RemoteException {

        for (int i = 0; i < moedas.size(); i++) {

            if (moedas.get(i)[0].equals(moeda)) {

                return;
            }
        }

        String[] aux = new String[2];

        aux[0] = moeda;
        aux[1] = cotacao;

        moedas.add(aux);

        notificaClientes("A moeda: " + moeda + " foi adicionada com cotação de: " + cotacao);

    }

    public ArrayList<String[]> visualizaMoeda() throws RemoteException {

        return moedas;
    }

    public void removeMoeda(String moeda) throws RemoteException {

        for (int i = 0; i < moedas.size(); i++) {

            if (moedas.get(i)[0].equals(moeda)) {

                moedas.remove(i);
            }
        }

        notificaClientes("A moeda: " + moeda + " foi removida");
    }

    public void alteraMoeda(String moeda, String alteracao) throws RemoteException {

        String aux2 = "";
        for (int i = 0; i < moedas.size(); i++) {

            if (moedas.get(i)[0].equals(moeda)) {

                aux2 = moedas.get(i)[1];
                String[] aux = new String[2];
                aux[0] = moeda;
                aux[1] = alteracao;

                moedas.set(i, aux);
            }
        }

        notificaClientes("A moeda: " + moeda + " sofreu alteração na cotação de: " + aux2 + " para: " + alteracao);
    }

    public void notificaClientes(String s) {

        for (int i = 0; i < list.size(); i++) {

            InterfaceCliente cliente = (InterfaceCliente) list.get(i);

            try {

                cliente.notificacao(s);
            } catch (RemoteException e) {

                System.out.println("Removendo o cliente: " + cliente + " por falta de comunicação");
                list.remove(cliente);
            }
        }

    }

    public static String clienteToString(InterfaceCliente cliente) {
        String all = cliente.toString();
        String[] all2 = all.split(":");
        return all2[2].substring(1) + ":" + all2[3].split("]")[0];
    }

    @Override
    public void addCliente(InterfaceCliente cliente) throws RemoteException {
        list.add(cliente);
        System.out.println("[" + list.size() + "] " + "Adicionando o cliente: " + clienteToString(cliente));

    }

    @Override
    public void removeCliente(InterfaceCliente cliente) throws RemoteException {

        System.out.println("Removendo o cliente: " + cliente);
        list.remove(cliente);

    }

}

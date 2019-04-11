/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author higor
 */
public class Servidor {

    public static void main(String[] args) throws IOException {
        long clientsAmount = 0;

        ServerSocket serverSocket = new ServerSocket(4444);
        System.out.println("Servidor executando porta 4444..");

        do {
            Socket socket = null;

            try {
                socket = serverSocket.accept();
                clientsAmount++;
            } catch (IOException ex) {
                break;
            }

            System.out.println("client: " + clientsAmount + socket.getRemoteSocketAddress());
            new SocketPatrimonio(socket).patrimonio();
        } while (true);
    }

}

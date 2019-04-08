/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author higor
 */
public class SocketPatrimonio {

    private final Socket socket;

    public SocketPatrimonio(Socket socket) {
        this.socket = socket;
    }

    public void patrimonio() throws IOException {
        DataInputStream leitura = new DataInputStream(socket.getInputStream());
        DataOutputStream saida = new DataOutputStream(socket.getOutputStream());

        double valorInvestimento = leitura.readDouble();
        double patrimonioDesejado = leitura.readDouble();
        int tempoInvestimento = leitura.readInt();
        double divisao = patrimonioDesejado / valorInvestimento;

        double inverso = (1.0 / tempoInvestimento);

        double taxaJurosMensais = (Math.pow(divisao, inverso)) - 1.0;
        saida.writeDouble(taxaJurosMensais);

        leitura.close();
        saida.close();
        socket.close();

    }

}

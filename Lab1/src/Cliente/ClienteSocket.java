/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author higor
 */
public class ClienteSocket {

    public double patrimonio(double valorInvestimento, Double patrimonioDesejado, int tempoInvestimento, String hostName, int porta) throws IOException {
        Socket socket = new Socket(hostName, porta);
        DataInputStream leitura = new DataInputStream(socket.getInputStream());
        DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
        saida.writeDouble(valorInvestimento);
        saida.writeDouble(patrimonioDesejado);
        saida.writeInt(tempoInvestimento);
        double taxaJurosMensais = leitura.readDouble();
        leitura.close();
        saida.close();
        socket.close();

        return taxaJurosMensais;
    }

}

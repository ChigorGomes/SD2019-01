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
import java.awt.EventQueue;
import java.awt.TextArea;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import Servidor.InterfaceServidor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class AplicacaoCliente extends UnicastRemoteObject implements InterfaceCliente {
    
    private static final long serialVersionUID = 1L;
    private JFrame frame;
    private JTextField Nome;
    private JTextField insereIP;
    private JTextField inserePORTA;
    private JTextField Cotacao;
    private TextArea mostraMoedas;
    public TextArea informativo;    
    static InterfaceServidor interfaceServidor = null;
    static AplicacaoCliente window = null;
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    window = new AplicacaoCliente();
                    window.frame.setVisible(true);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public static void conexao() {
        try {
            String IP = window.insereIP.getText();
            int porta = Integer.parseInt(window.inserePORTA.getText());
            
            Registry reg = LocateRegistry.getRegistry(IP, porta);
            interfaceServidor = (InterfaceServidor) reg.lookup("InterfaceServidor");
            interfaceServidor.addCliente(window);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public AplicacaoCliente() throws RemoteException {
        initialize();
    }
    
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(150, 150, 510, 620);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        JLabel lblIP = new JLabel("IP:");
        lblIP.setBounds(12, 26, 70, 15);
        frame.getContentPane().add(lblIP);
        
        insereIP = new JTextField();
        insereIP.setBounds(50, 24, 114, 19);
        frame.getContentPane().add(insereIP);
        insereIP.setColumns(10);
        
        JLabel lblPORTA = new JLabel("Porta:");
        lblPORTA.setBounds(180, 26, 70, 15);
        frame.getContentPane().add(lblPORTA);
        
        inserePORTA = new JTextField();
        inserePORTA.setBounds(230, 24, 80, 19);
        frame.getContentPane().add(inserePORTA);
        inserePORTA.setColumns(10);
        
        JButton btnConectarServidor = new JButton("Conectar");
        btnConectarServidor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conexao();
            }
        });
        btnConectarServidor.setBounds(370, 24, 110, 25);
        frame.getContentPane().add(btnConectarServidor);
        
        JLabel lblNome = new JLabel("Moeda:");
        lblNome.setBounds(12, 70, 70, 15);
        frame.getContentPane().add(lblNome);
        
        Nome = new JTextField();
        Nome.setBounds(75, 65, 160, 19);
        frame.getContentPane().add(Nome);
        Nome.setColumns(10);
        
        JLabel lblCotao = new JLabel("Cotação:");
        lblCotao.setBounds(12, 100, 70, 15);
        frame.getContentPane().add(lblCotao);
        
        Cotacao = new JTextField();
        Cotacao.setBounds(75, 100, 160, 19);
        frame.getContentPane().add(Cotacao);
        Cotacao.setColumns(10);
        
        informativo = new TextArea();        
        informativo.setBounds(12, 140, 325, 240);
        frame.getContentPane().add(informativo);
        
        JButton btnAdicionaMoeda = new JButton("Adicionar Moeda");
        btnAdicionaMoeda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    interfaceServidor.insereMoeda(Nome.getText().toString(), Cotacao.getText().toString());                    
                } catch (RemoteException e1) {                    
                    e1.printStackTrace();
                }
            }
        });
        btnAdicionaMoeda.setBounds(350, 140, 146, 25);
        frame.getContentPane().add(btnAdicionaMoeda);
        
        JButton btnAlteraCotao = new JButton("Alterar Cotação");
        btnAlteraCotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {                    
                    interfaceServidor.alteraMoeda(Nome.getText().toString(), Cotacao.getText().toString());                    
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnAlteraCotao.setBounds(350, 180, 146, 25);
        frame.getContentPane().add(btnAlteraCotao);
        
        JButton btnConsultaMoedas = new JButton("Consultar Moedas");
        btnConsultaMoedas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<String[]> aux = new ArrayList<String[]>();
                mostraMoedas.setText("");
                try {
                    aux = interfaceServidor.visualizaMoeda();
                    for (int i = 0; i < aux.size(); i++) {
                        mostraMoedas.append("Moeda: " + aux.get(i)[0] + " | Cotação: " + aux.get(i)[1] + "\n");
                    }
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnConsultaMoedas.setBounds(350, 220, 146, 25);
        frame.getContentPane().add(btnConsultaMoedas);
        
        JButton btnRemove = new JButton("Remover moeda");
        btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {                
                try {
                    interfaceServidor.removeMoeda(Nome.getText().toString());
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnRemove.setBounds(350, 260, 146, 25);
        frame.getContentPane().add(btnRemove);
        
        JLabel lblTelaDeAtualizao = new JLabel("Consultas");
        lblTelaDeAtualizao.setBounds(190, 390, 161, 15);
        frame.getContentPane().add(lblTelaDeAtualizao);
        
        mostraMoedas = new TextArea();
        mostraMoedas.setBounds(12, 420, 480, 130);
        frame.getContentPane().add(mostraMoedas);
    }
    
    @Override
    public void notificacao(String s) throws RemoteException {
        informativo.append(s + "\n");
    }
}

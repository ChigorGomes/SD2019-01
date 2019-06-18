package Classe;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Usuario  implements Serializable {
    private int idUsuario;
    private String nome;
    private String email;
    private String senha;
    private int idade;
    private int idRegiao;

    public Usuario(int idUsuario, String nome, String senha, int idRegiao) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.senha = senha;
        this.idRegiao = idRegiao;
    }

    public Usuario(int idUsuario, String nome, String email, String senha, int idade, int idRegiao) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.idade = idade;
        this.idRegiao = idRegiao;
    }
    public Usuario(String nome, String email, String senha, int idade, int idRegiao) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.idade = idade;
        this.idRegiao = idRegiao;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getIdRegiao() {
        return idRegiao;
    }

    public void setIdRegiao(int idRegiao) {
        this.idRegiao = idRegiao;
    }

    public static String convertPassMd5(String pass) {
        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }

}

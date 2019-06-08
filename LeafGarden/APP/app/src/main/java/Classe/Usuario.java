package Classe;

import java.io.Serializable;

public class Usuario  implements Serializable {
    private int idUsuario;
    private String nome;
    private String email;
    private String senha;
    private int idade;
    private int idRegiao;

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
}

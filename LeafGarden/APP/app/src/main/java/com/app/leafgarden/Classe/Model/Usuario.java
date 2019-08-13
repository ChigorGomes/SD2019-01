package com.app.leafgarden.Classe.Model;

public class Usuario  {
    private String nome;
    private String email;
    private String senha;
    private String idade;
    private String regiao;


    public  Usuario(){}
    public Usuario(String nome, String email, String idade, String regiao) {
        this.nome = nome;
        this.email = email;
        this.idade = idade;
        this.regiao = regiao;
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

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }
}

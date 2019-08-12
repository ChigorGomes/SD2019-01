package Classe;

import java.io.Serializable;

public class Planta implements Serializable {
    private int idPlanta;
    private String nomePlanta;
    private String descricao;
    private String localAdequado;
    private float tempAmbiente;
    private float umidadeAmbiente;
    private float tempSolo;
    private float umidadeSolo;
    private float luminosidade;
    private byte[] foto;

    public Planta(){}
    public Planta(int idPlanta, String nomePlanta, String descricao, String localAdequado, float tempAmbiente, float umidadeAmbiente, float tempSolo, float umidadeSolo, float luminosidade, byte[] foto) {
        this.idPlanta = idPlanta;
        this.nomePlanta = nomePlanta;
        this.descricao = descricao;
        this.localAdequado = localAdequado;
        this.tempAmbiente = tempAmbiente;
        this.umidadeAmbiente = umidadeAmbiente;
        this.tempSolo = tempSolo;
        this.umidadeSolo = umidadeSolo;
        this.luminosidade = luminosidade;
        this.foto = foto;
    }



    public Planta(String nomePlanta, String descricao, String localAdequado, float tempAmbiente, float umidadeAmbiente, float tempSolo, float umidadeSolo, float luminosidade) {
        this.nomePlanta = nomePlanta;
        this.descricao = descricao;
        this.localAdequado = localAdequado;
        this.tempAmbiente = tempAmbiente;
        this.umidadeAmbiente = umidadeAmbiente;
        this.tempSolo = tempSolo;
        this.umidadeSolo = umidadeSolo;
        this.luminosidade = luminosidade;
    }

    public Planta(String nomePlanta, byte[] foto) {
        this.nomePlanta = nomePlanta;
        this.foto = foto;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public int getIdPlanta() {
        return idPlanta;
    }

    public void setIdPlanta(int idPlanta) {
        this.idPlanta = idPlanta;
    }

    public String getNomePlanta() {
        return nomePlanta;
    }

    public void setNomePlanta(String nomePlanta) {
        this.nomePlanta = nomePlanta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalAdequado() {
        return localAdequado;
    }

    public void setLocalAdequado(String localAdequado) {
        this.localAdequado = localAdequado;
    }

    public float getTempAmbiente() {
        return tempAmbiente;
    }

    public void setTempAmbiente(float tempAmbiente) {
        this.tempAmbiente = tempAmbiente;
    }

    public float getUmidadeAmbiente() {
        return umidadeAmbiente;
    }

    public void setUmidadeAmbiente(float umidadeAmbiente) {
        this.umidadeAmbiente = umidadeAmbiente;
    }

    public float getTempSolo() {
        return tempSolo;
    }

    public void setTempSolo(float tempSolo) {
        this.tempSolo = tempSolo;
    }

    public float getUmidadeSolo() {
        return umidadeSolo;
    }

    public void setUmidadeSolo(float umidadeSolo) {
        this.umidadeSolo = umidadeSolo;
    }

    public float getLuminosidade() {
        return luminosidade;
    }

    public void setLuminosidade(float luminosidade) {
        this.luminosidade = luminosidade;
    }
}

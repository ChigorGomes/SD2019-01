package Classe;

import java.io.Serializable;

public class Planta implements Serializable {
    private String idPlanta;
    private String nomePlanta;
    private String descricao;
    private String localAdequado;
    private float tempAmbiente;
    private float umidadeAmbiente;
    private float tempSolo;
    private float umidadeSolo;
    private float luminosidade;
    private String imagemCaminho;
    private String imagemUrl;

    public Planta(){}

    public String getIdPlanta() {
        return idPlanta;
    }

    public void setIdPlanta(String idPlanta) {
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

    public String getImagemCaminho() {
        return imagemCaminho;
    }

    public void setImagemCaminho(String imagemCaminho) {
        this.imagemCaminho = imagemCaminho;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }
}

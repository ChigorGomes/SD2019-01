package Classe;

import java.io.Serializable;

public class Jardim implements Serializable {
    private int idJardim;
    private int idUsuario;
    private int idPlanta;
    private String nomePlanta;
    private float tempAmbiente;
    private float umidadeAmbiente;
    private float tempSolo;
    private float umidadeSolo;
    private float luminosidade;
    private byte[] foto;

//    public Jardim(int idJardim, int idUsuario, int idPlanta) {
//        this.idJardim = idJardim;
//        this.idUsuario = idUsuario;
//        this.idPlanta = idPlanta;
//    }

    public Jardim(int idJardim, int idUsuario, int idPlanta, String nomePlanta, float tempAmbiente, float umidadeAmbiente, float tempSolo, float umidadeSolo, float luminosidade, byte[] foto) {
        this.idJardim = idJardim;
        this.idUsuario = idUsuario;
        this.idPlanta = idPlanta;
        this.nomePlanta = nomePlanta;
        this.tempAmbiente = tempAmbiente;
        this.umidadeAmbiente = umidadeAmbiente;
        this.tempSolo = tempSolo;
        this.umidadeSolo = umidadeSolo;
        this.luminosidade = luminosidade;
        this.foto = foto;
    }

    public String getNomePlanta() {
        return nomePlanta;
    }

    public void setNomePlanta(String nomePlanta) {
        this.nomePlanta = nomePlanta;
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

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Jardim(int idUsuario, int idPlanta) {
        this.idUsuario = idUsuario;
        this.idPlanta = idPlanta;
    }

    public int getIdJardim() {
        return idJardim;
    }

    public void setIdJardim(int idJardim) {
        this.idJardim = idJardim;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPlanta() {
        return idPlanta;
    }

    public void setIdPlanta(int idPlanta) {
        this.idPlanta = idPlanta;
    }
}

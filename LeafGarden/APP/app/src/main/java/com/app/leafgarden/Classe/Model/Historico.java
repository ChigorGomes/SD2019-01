package com.app.leafgarden.Classe.Model;

public class Historico   {
    private String idsensor;
    private String horario;
    private float luminosidade;
    private float temperaturaambiente;
    private float temperaturasolo;
    private float umidadeambiente;
    private float umidadesolo;


    public Historico() {
    }

    public String getIdsensor() {
        return idsensor;
    }

    public void setIdsensor(String idsensor) {
        this.idsensor = idsensor;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public float getLuminosidade() {
        return luminosidade;
    }

    public void setLuminosidade(float luminosidade) {
        this.luminosidade = luminosidade;
    }

    public float getTemperaturaambiente() {
        return temperaturaambiente;
    }

    public void setTemperaturaambiente(float temperaturaambiente) {
        this.temperaturaambiente = temperaturaambiente;
    }

    public float getTemperaturasolo() {
        return temperaturasolo;
    }

    public void setTemperaturasolo(float temperaturasolo) {
        this.temperaturasolo = temperaturasolo;
    }

    public float getUmidadeambiente() {
        return umidadeambiente;
    }

    public void setUmidadeambiente(float umidadeambiente) {
        this.umidadeambiente = umidadeambiente;
    }

    public float getUmidadesolo() {
        return umidadesolo;
    }

    public void setUmidadesolo(float umidadesolo) {
        this.umidadesolo = umidadesolo;
    }
}

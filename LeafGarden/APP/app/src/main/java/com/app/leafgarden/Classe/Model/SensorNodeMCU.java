package com.app.leafgarden.Classe.Model;

public class SensorNodeMCU {
    public int luminosidade;
    public int temperaturaambiente;
    public int temperaturasolo;
    public int umidadeambiente;
    public int umidadesolo;


    public SensorNodeMCU() {
    }

    public SensorNodeMCU(int luminosidade, int temperaturaambiente, int temperaturasolo, int umidadeambiente, int umidadesolo) {
        this.luminosidade = luminosidade;
        this.temperaturaambiente = temperaturaambiente;
        this.temperaturasolo = temperaturasolo;
        this.umidadeambiente = umidadeambiente;
        this.umidadesolo = umidadesolo;
    }

    public int getLuminosidade() {
        return luminosidade;
    }

    public void setLuminosidade(int luminosidade) {
        this.luminosidade = luminosidade;
    }

    public int getTemperaturaambiente() {
        return temperaturaambiente;
    }

    public void setTemperaturaambiente(int temperaturaambiente) {
        this.temperaturaambiente = temperaturaambiente;
    }

    public int getTemperaturasolo() {
        return temperaturasolo;
    }

    public void setTemperaturasolo(int temperaturasolo) {
        this.temperaturasolo = temperaturasolo;
    }

    public int getUmidadeambiente() {
        return umidadeambiente;
    }

    public void setUmidadeambiente(int umidadeambiente) {
        this.umidadeambiente = umidadeambiente;
    }

    public int getUmidadesolo() {
        return umidadesolo;
    }

    public void setUmidadesolo(int umidadesolo) {
        this.umidadesolo = umidadesolo;
    }


}

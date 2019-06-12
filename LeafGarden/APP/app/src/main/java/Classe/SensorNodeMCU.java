package Classe;

public class SensorNodeMCU {
    public String luminosidade;
    public String temperaturaambiente;
    public String temperaturasolo;
    public String umidadeambiente;
    public String umidadesolo;
    public SensorNodeMCU(){}

    public SensorNodeMCU(String luminosidade, String temperaturaambiente, String temperaturasolo, String umidadeambiente, String umidadesolo) {
        this.luminosidade = luminosidade;
        this.temperaturaambiente = temperaturaambiente;
        this.temperaturasolo = temperaturasolo;
        this.umidadeambiente = umidadeambiente;
        this.umidadesolo = umidadesolo;
    }

    public String getLuminosidade() {
        return luminosidade;
    }

    public void setLuminosidade(String luminosidade) {
        this.luminosidade = luminosidade;
    }

    public String getTemperaturaambiente() {
        return temperaturaambiente;
    }

    public void setTemperaturaambiente(String temperaturaambiente) {
        this.temperaturaambiente = temperaturaambiente;
    }

    public String getTemperaturasolo() {
        return temperaturasolo;
    }

    public void setTemperaturasolo(String temperaturasolo) {
        this.temperaturasolo = temperaturasolo;
    }

    public String getUmidadeambiente() {
        return umidadeambiente;
    }

    public void setUmidadeambiente(String umidadeambiente) {
        this.umidadeambiente = umidadeambiente;
    }

    public String getUmidadesolo() {
        return umidadesolo;
    }

    public void setUmidadesolo(String umidadesolo) {
        this.umidadesolo = umidadesolo;
    }
}

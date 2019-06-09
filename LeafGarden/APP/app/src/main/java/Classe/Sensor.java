package Classe;

public class Sensor {
    public int luminosidade;
    public int temperatura;
    public int umidade;

    public  Sensor(){}

    public Sensor(int luminosidade, int temperatura, int umidade) {
        this.luminosidade = luminosidade;
        this.temperatura = temperatura;
        this.umidade = umidade;
    }

    public int getLuminosidade() {
        return luminosidade;
    }

    public void setLuminosidade(int luminosidade) {
        this.luminosidade = luminosidade;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public int getUmidade() {
        return umidade;
    }

    public void setUmidade(int umidade) {
        this.umidade = umidade;
    }
}

package Classe;

import java.io.Serializable;

public class Historico  implements Serializable {
    private int idHistorico;
    private String dataHorario;
    private String infoTemperatura;
    private String infoUmidade;
    private String infoLuminosidade;
    private String nomePlanta;

    public Historico() {
    }

    public Historico(int idHistorico, String dataHorario, String infoTemperatura, String infoUmidade, String infoLuminosidade, String nomePlanta) {
        this.idHistorico = idHistorico;
        this.dataHorario = dataHorario;
        this.infoTemperatura = infoTemperatura;
        this.infoUmidade = infoUmidade;
        this.infoLuminosidade = infoLuminosidade;
        this.nomePlanta = nomePlanta;
    }

    public Historico(String dataHorario, String infoTemperatura, String infoUmidade, String infoLuminosidade) {
        this.dataHorario = dataHorario;
        this.infoTemperatura = infoTemperatura;
        this.infoUmidade = infoUmidade;
        this.infoLuminosidade = infoLuminosidade;
    }

    public int getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(int idHistorico) {
        this.idHistorico = idHistorico;
    }

    public String getDataHorario() {
        return dataHorario;
    }

    public void setDataHorario(String dataHorario) {
        this.dataHorario = dataHorario;
    }

    public String getInfoTemperatura() {
        return infoTemperatura;
    }

    public void setInfoTemperatura(String infoTemperatura) {
        this.infoTemperatura = infoTemperatura;
    }

    public String getInfoUmidade() {
        return infoUmidade;
    }

    public void setInfoUmidade(String infoUmidade) {
        this.infoUmidade = infoUmidade;
    }

    public String getInfoLuminosidade() {
        return infoLuminosidade;
    }

    public void setInfoLuminosidade(String infoLuminosidade) {
        this.infoLuminosidade = infoLuminosidade;
    }
    public String getNomePlanta() {
        return nomePlanta;
    }

    public void setNomePlanta(String nomePlanta) {
        this.nomePlanta = nomePlanta;
    }


}

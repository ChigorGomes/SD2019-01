package Classe;

import java.io.Serializable;

public class Jardim implements Serializable {
    private String idJardim;
    private String idUsuario;
    private String idPlanta;



    public  Jardim(){}

    public String getIdJardim() {
        return idJardim;
    }

    public void setIdJardim(String idJardim) {
        this.idJardim = idJardim;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdPlanta() {
        return idPlanta;
    }

    public void setIdPlanta(String idPlanta) {
        this.idPlanta = idPlanta;
    }
}

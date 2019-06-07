package Classe;

import java.io.Serializable;

public class Jardim implements Serializable {
    private int idJardim;
    private int idUsuario;
    private int idPlanta;

    public Jardim(int idJardim, int idUsuario, int idPlanta) {
        this.idJardim = idJardim;
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

package sio.gestionmagazine.Model;

public class Magazine
{
    private int idMag;
    private String nomMag;
    private String nomSpe;

    public Magazine(int idMag, String nomMag, String nomSpe) {
        this.idMag = idMag;
        this.nomMag = nomMag;
        this.nomSpe = nomSpe;
    }

    public int getIdMag() {
        return idMag;
    }

    public String getNomMag() {
        return nomMag;
    }

    public String getNomSpe() {
        return nomSpe;
    }
}

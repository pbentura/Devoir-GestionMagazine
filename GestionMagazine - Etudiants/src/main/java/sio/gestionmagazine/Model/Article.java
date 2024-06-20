package sio.gestionmagazine.Model;

public class Article
{
    private int idArticle;
    private String titreArticle;
    private int nbPages;
    private String nomPigiste;

    public Article(int idArticle,String titreArticle, int nbPages, String nomPigiste) {
        this.idArticle = idArticle;
        this.titreArticle = titreArticle;
        this.nbPages = nbPages;
        this.nomPigiste = nomPigiste;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public String getTitreArticle() {
        return titreArticle;
    }

    public int getNbPages() {
        return nbPages;
    }

    public String getNomPigiste() {
        return nomPigiste;
    }
}

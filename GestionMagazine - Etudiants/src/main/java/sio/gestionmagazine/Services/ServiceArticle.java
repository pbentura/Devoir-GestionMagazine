package sio.gestionmagazine.Services;

import sio.gestionmagazine.Model.Article;
import sio.gestionmagazine.Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceArticle
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public ServiceArticle()
    {
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<Article> getAllArticlesbyMagazine(int idMag) throws SQLException {
        ArrayList<Article> lesArticles = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT article.idArticle, article.titreArticle, article.nbFeuillets, pigiste.nomPigiste\n" +
                "FROM article\n" +
                "JOIN pigiste ON article.numPig = pigiste.idPigiste\n" +
                "JOIN magazine ON article.numMag = magazine.idMag\n" +
                "WHERE magazine.idMag = "+idMag);
        rs = ps.executeQuery();

        while (rs.next())
        {
            lesArticles.add(new Article(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4)));
        }
        ps.close();
        rs.close();
        return lesArticles;
    }

    public double getMontantArticleById(int idArt) throws SQLException{
        double montant = 0;
        ps = cnx.prepareStatement("SELECT (article.nbFeuillets * pigiste.prixFeuillet) as montanArticle\n" +
                "FROM article\n" +
                "JOIN pigiste ON pigiste.idPigiste = article.numPig\n" +
                "WHERE article.idArticle = "+idArt);
        rs = ps.executeQuery();
        while(rs.next())
        {
            montant = rs.getDouble(1);
        }
        ps.close();
        rs.close();
        return montant;
    }

    public void ajouterArticle(int idArt, String titreArticle, int nbPages, String nomPig) throws SQLException
    {
        ps = cnx.prepareStatement("INSERT INTO article VALUES (?, ?, ?, ?)");
        ps.setInt(1, idArt);
        ps.setString(2, titreArticle);
        ps.setInt(3, nbPages);
        ps.setString(4, nomPig);

        ps.executeUpdate();
        ps.close();
    }


}

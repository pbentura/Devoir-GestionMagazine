package sio.gestionmagazine.Services;

import sio.gestionmagazine.Model.Magazine;
import sio.gestionmagazine.Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ServiceMagazine
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public ServiceMagazine()
    {
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<Magazine> getAllMagazines() throws SQLException {
        ArrayList<Magazine> lesMagazines = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT magazine.idMag, magazine.nomMag, specialite.nomSpe\n" +
                "FROM magazine\n" +
                "JOIN specialite on magazine.numSpecialite = specialite.idSpe");
        rs = ps.executeQuery();

        while (rs.next())
        {
            lesMagazines.add(new Magazine(rs.getInt(1), rs.getString(2), rs.getString(3)));
        }
        ps.close();
        rs.close();
        return lesMagazines;
    }

    public double getMontantMagazineById(int idMag) throws SQLException
    {
        double montant = 0;
        ps = cnx.prepareStatement("SELECT SUM(article.nbFeuillets * pigiste.prixFeuillet) AS montantTotal\n" +
                "FROM article\n" +
                "JOIN pigiste ON article.numPig = pigiste.idPigiste\n" +
                "JOIN magazine ON article.numMag = magazine.idMag\n" +
                "WHERE magazine.idMag = "+idMag+"\n" +
                "GROUP BY magazine.nomMag;\n");
        rs = ps.executeQuery();
        while(rs.next())
        {
            montant = rs.getDouble(1);
        }
        ps.close();
        rs.close();

        return montant;
    }


}

package sio.gestionmagazine.Services;

import sio.gestionmagazine.Model.Magazine;
import sio.gestionmagazine.Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServicePigiste
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public ServicePigiste()
    {
        cnx = ConnexionBDD.getCnx();
    }


    public ArrayList<String> getAllPigistesbyMagazine(int idMag) throws SQLException {
        ArrayList<String> lesPigistes = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT pigiste.nomPigiste\n" +
                "FROM magazine\n" +
                "JOIN specialite ON magazine.numSpecialite = specialite.idSpe\n" +
                "JOIN posseder ON specialite.idSpe = posseder.numSpe\n" +
                "JOIN pigiste ON posseder.numPig = pigiste.idPigiste\n" +
                "WHERE magazine.idMag = " + idMag);
        rs = ps.executeQuery();

        while (rs.next())
        {
            lesPigistes.add(rs.getString(1));
        }
        ps.close();
        rs.close();
        return lesPigistes;

    }
}

package classe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Create_class {

    public static Map<Integer, Line> linesMap = new HashMap<>();

    public static void fill_ligne(Statement statement, Map<Integer, Line> linesMap) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT id,name FROM Ligne;");
        while (resultSet.next()) {
            int id  = resultSet.getInt("id");
            String name = resultSet.getString("name");
            linesMap.put(id, new Line(name));
        }
        resultSet.close();
    }

    public static void fill_gare(Statement statement, Map<Integer, Line> linesMap) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT id,name,id_ligne,x,y FROM gare;");
        while (resultSet.next()) {
            int id  = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int id_ligne = resultSet.getInt("id_ligne");
            int x = resultSet.getInt("x");
            int y = resultSet.getInt("y");
            new Gare(id,name,linesMap.get(id_ligne),x,y);
        }
        resultSet.close();
    }

    public static void fill_voie(Statement statement, Map<Integer, Line> linesMap) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT c.id_Gare1, c.id_Gare2, temps,bidirectionnel,id_ligne FROM contient c, voie v where v.id=c.id_Voie and v.id_ligne!=0;");
        while (resultSet.next()) {
            int id_Gare1  = resultSet.getInt("id_Gare1");
            int id_Gare2  = resultSet.getInt("id_Gare2");
            int temps  = resultSet.getInt("temps");
            int bidirectionnel  = resultSet.getInt("bidirectionnel");
            int id_1  = resultSet.getInt("id_ligne");
            Line line_for_voie = linesMap.get(id_1);
            Gare gare1= line_for_voie.findGare_with_id(id_Gare1);
            Gare gare2= line_for_voie.findGare_with_id(id_Gare2);
            new Voie(gare1,gare2,line_for_voie,temps,bidirectionnel);
        }
        resultSet.close();
        String query = "SELECT " +
                "c.id_Gare1, g1.id_ligne AS id_Ligne_Gare1, " +
                "c.id_Gare2, g2.id_ligne AS id_Ligne_Gare2, " +
                "v.temps, v.bidirectionnel, v.id_ligne AS id_Ligne_Voie " +
                "FROM " +
                "contient c " +
                "JOIN Gare g1 ON c.id_Gare1 = g1.id " +
                "JOIN Gare g2 ON c.id_Gare2 = g2.id " +
                "JOIN Voie v ON c.id_Voie = v.id " +
                "WHERE v.id_ligne = 0";

        resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int idGare1 = resultSet.getInt("id_Gare1");
            int idLigneGare1 = resultSet.getInt("id_Ligne_Gare1");
            int idGare2 = resultSet.getInt("id_Gare2");
            int idLigneGare2 = resultSet.getInt("id_Ligne_Gare2");
            int temps = resultSet.getInt("temps");
            int bidirectionnel = resultSet.getInt("bidirectionnel");
            int idLigneVoie = resultSet.getInt("id_Ligne_Voie");

            Line line_for_gare1 = linesMap.get(idLigneGare1);
            Gare gare1= line_for_gare1.findGare_with_id(idGare1);
            Line line_for_gare2 = linesMap.get(idLigneGare2);
            Gare gare2= line_for_gare2.findGare_with_id(idGare2);
            Line line_for_voie = linesMap.get(idLigneVoie);
            new Voie(gare1,gare2,line_for_voie,temps,bidirectionnel);
        }
    }

    public static Map<Integer, Line> fill_all(String url, String user, String password) {
        try {
            Map<Integer, Line> linesMap = new HashMap<>();

            // Chargement du driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établissement de la connexion
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion réussie !");

            // Création d'une requête
            Statement statement = connection.createStatement();
            fill_ligne(statement, linesMap);
            fill_gare(statement, linesMap);
            fill_voie(statement, linesMap);

            statement.close();
            connection.close();
            return linesMap;

        } catch (ClassNotFoundException e) {
            System.out.println("Driver non trouvé !");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion !");
            e.printStackTrace();
        }
        return null;
    }

    public static void create_all_class(){
        String name_bdd = "locomotive";
        String url = "jdbc:mysql://localhost:3306/" + name_bdd;
        String user = "root";
        String password = "Romain_09";

        linesMap=fill_all(url, user, password);
        Fonction.setLinesMap(linesMap);
    }
}
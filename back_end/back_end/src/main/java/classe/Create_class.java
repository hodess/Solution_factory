package classe;

import org.example.Application;
import org.springframework.boot.SpringApplication;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Create_class {

    public static Map<String, Line> linesMap = new HashMap<>();

    public static void fill_ligne(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT id, name FROM Ligne;");
        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            linesMap.put(id, new Line(name));
        }
        resultSet.close();
    }

    public static void fill_gare(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT id,name,x,y,id_ligne,name_ligne FROM locomotive.lieu_with_individual_lines;");
        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            String name_ligne = resultSet.getString("name_ligne");
            String id_ligne = resultSet.getString("id_ligne");
            double x = resultSet.getDouble("x");
            double y = resultSet.getDouble("y");
            new Gare(id, name, linesMap.get(id_ligne), x, y);
        }
        resultSet.close();
    }

    public static void fill_voie(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT temps,id_Ligne,name_Gare1,id_Gare1,id_ligne_gare1,name_Gare2,id_Gare2,id_ligne_gare2 FROM locomotive.voie_with_gare_with_id where name_Ligne!=0 order by name_ligne;");
        while (resultSet.next()) {
            String id_Gare1 = resultSet.getString("id_Gare1");
            String id_Gare2 = resultSet.getString("id_Gare2");
            int temps = resultSet.getInt("temps");
            String id_1 = resultSet.getString("id_ligne");
            Line line_for_voie = linesMap.get(id_1);
            Gare gare1 = line_for_voie.findGare_with_id(id_Gare1);
            Gare gare2 = line_for_voie.findGare_with_id(id_Gare2);
            new Voie(gare1, gare2, line_for_voie, temps, 0);
        }
        resultSet.close();
        /*
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
            String idGare1 = resultSet.getString("id_Gare1");
            String idLigneGare1 = resultSet.getString("id_Ligne_Gare1");
            String idGare2 = resultSet.getString("id_Gare2");
            String idLigneGare2 = resultSet.getString("id_Ligne_Gare2");
            int temps = resultSet.getInt("temps");
            int bidirectionnel = resultSet.getInt("bidirectionnel");
            int idLigneVoie = resultSet.getInt("id_Ligne_Voie");

            Line line_for_gare1 = linesMap.get(idLigneGare1);
            Gare gare1 = line_for_gare1.findGare_with_id(idGare1);
            Line line_for_gare2 = linesMap.get(idLigneGare2);
            Gare gare2 = line_for_gare2.findGare_with_id(idGare2);
            Line line_for_voie = linesMap.get(idLigneVoie);
            new Voie(gare1, gare2, line_for_voie, temps, bidirectionnel);
        }*/
    }

    public static void fill_all(String url, String user, String password) {
        try {
            linesMap = new HashMap<>();

            // Chargement du driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établissement de la connexion
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion réussie !");

            // Création d'une requête
            Statement statement = connection.createStatement();
            fill_ligne(statement);
            fill_gare(statement);
            fill_voie(statement);

            statement.close();
            connection.close();
            return ;

        } catch (ClassNotFoundException e) {
            System.out.println("Driver non trouvé !");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion !");
            e.printStackTrace();
        }
        return;
    }

    public static void create_all_class() {
        Properties properties = new Properties();
        try (InputStream input = Create_class.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return;
            }

            // load a properties file from class path, inside static method
            properties.load(input);

            // get the property value and print it out
            String url = properties.getProperty("spring.datasource.url");
            String user = properties.getProperty("spring.datasource.username");
            String password = properties.getProperty("spring.datasource.password");

             fill_all(url, user, password);
            Fonction.setLinesMap(linesMap);
            ReturnGareWithOrder.setLinesMap(linesMap);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Properties properties = new Properties();
        try (InputStream input = Create_class.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return;
            }

            // load a properties file from class path, inside static method
            properties.load(input);

            // get the property value and print it out
            String url = properties.getProperty("spring.datasource.url");
            String user = properties.getProperty("spring.datasource.username");
            String password = properties.getProperty("spring.datasource.password");

            try {
                linesMap = new HashMap<>();

                // Chargement du driver JDBC
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Établissement de la connexion
                Connection connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connexion réussie !");

                // Création d'une requête
                Statement statement = connection.createStatement();
                fill_ligne(statement);
                fill_gare(statement);
                fill_voie(statement);

                for (Line l : linesMap.values()) {
                    System.out.println(l.getListeGare().size()  + "\t"+ l);
                }

                statement.close();
                connection.close();
                return ;

            } catch (ClassNotFoundException e) {
                System.out.println("Driver non trouvé !");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Erreur de connexion !");
                e.printStackTrace();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

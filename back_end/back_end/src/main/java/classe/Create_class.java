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
        ResultSet resultSet = statement.executeQuery("SELECT temps, id_Ligne, name_Ligne, name_Gare1, id_Gare1, id_ligne_gare1, name_Gare2, id_Gare2, id_ligne_gare2 FROM locomotive.voie_with_gare_with_id WHERE id_Ligne != 'IDFM:C01370' and id_ligne_gare1=id_ligne_gare2  ORDER BY name_Ligne LIMIT 2000;");
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

        String query = "SELECT temps, id_Ligne, name_Ligne, name_Gare1, id_Gare1, id_ligne_gare1, name_Gare2, id_Gare2, id_ligne_gare2 " +
               "FROM ( " +
               "    SELECT temps, id_Ligne, name_Ligne, name_Gare1, id_Gare1, id_ligne_gare1, name_Gare2, id_Gare2, id_ligne_gare2, " +
               "           ROW_NUMBER() OVER (PARTITION BY id_Ligne, name_Ligne, name_Gare1, id_Gare1, id_ligne_gare1, name_Gare2, id_Gare2, id_ligne_gare2 ORDER BY temps) as rn " +
               "    FROM locomotive.voie_with_gare_with_id " +
               "    WHERE id_Ligne = 'IDFM:C01370' AND id_ligne_gare1 != id_ligne_gare2 " +
               ") AS subquery " +
               "WHERE rn = 1 " +
               "ORDER BY name_Ligne " +
               "LIMIT 2000;";

        resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            String idGare1 = resultSet.getString("id_Gare1");
            String idLigneGare1 = resultSet.getString("id_ligne_gare1");
            String idGare2 = resultSet.getString("id_Gare2");
            String idLigneGare2 = resultSet.getString("id_ligne_gare2");
            int temps = resultSet.getInt("temps");
            String idLigneVoie = resultSet.getString("id_Ligne");

            Line line_for_gare1 = linesMap.get(idLigneGare1);
            Gare gare1 = line_for_gare1.findGare_with_id(idGare1);
            Line line_for_gare2 = linesMap.get(idLigneGare2);
            Gare gare2 = line_for_gare2.findGare_with_id(idGare2);
            Line line_for_voie = linesMap.get(idLigneVoie);
            new Voie(gare1, gare2, line_for_voie, temps, 0);
        }
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
                    System.out.println(l.getName());
                    for (Gare g : l.getListeGare()) {
                        System.out.println(g);
                    }
                    System.out.println("\n\n");
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GareDatabase {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/locomtive";
        String user = "root";
        String password = "Romain_09";

        try {
            // Chargement du driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établissement de la connexion
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion réussie !");

            // Création d'une requête
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM gares");

            // Traitement des résultats
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String ville = resultSet.getString("ville");
                int nombreDeQuais = resultSet.getInt("nombre_de_quais");

                System.out.println("Gare ID: " + id);
                System.out.println("Nom: " + nom);
                System.out.println("Ville: " + ville);
                System.out.println("Nombre de quais: " + nombreDeQuais);
                System.out.println();
            }

            // Fermeture de la connexion
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Driver non trouvé !");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion !");
            e.printStackTrace();
        }
    }
}

package classe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ReturnGareNoTraited {

    // Classe interne pour encapsuler les noms de gares et de lignes
    public static class LieuWithLine {
        private String name;
        private String nameLigne;

        public LieuWithLine(String name, String nameLigne) {
            this.name = name;
            this.nameLigne = nameLigne;
        }

        public String getName() {
            return name;
        }

        public String getNameLigne() {
            return nameLigne;
        }

        @Override
        public String toString() {
            return "LieuWithLine{" +
                    "name='" + name + '\'' +
                    ", nameLigne='" + nameLigne + '\'' +
                    '}';
        }
    }

    // Méthode pour récupérer les données de la base et retourner une liste d'objets LieuWithLine
    public static List<LieuWithLine> ReturnVue_lieu_with_line(Statement statement) throws SQLException {
        List<LieuWithLine> lieuxWithLines = new ArrayList<>();

        ResultSet resultSet = statement.executeQuery("SELECT name, name_ligne FROM lieu_with_line");
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String nameLigne = resultSet.getString("name_ligne");

            lieuxWithLines.add(new LieuWithLine(name, nameLigne));
        }

        return lieuxWithLines;
    }
    public static String FromListToJson(List<LieuWithLine> lieuWithLines) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(lieuWithLines);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Méthode pour charger les propriétés et appeler les fonctions
    public static String CallFunctions() {
        Properties properties = new Properties();
        try (InputStream input = ReturnGareNoTraited.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return null;
            }

            // Charger les propriétés
            properties.load(input);
            String url = properties.getProperty("spring.datasource.url");
            String user = properties.getProperty("spring.datasource.username");
            String password = properties.getProperty("spring.datasource.password");

            // Connexion à la base de données
            try (Connection connection = DriverManager.getConnection(url, user, password);
                 Statement statement = connection.createStatement()) {

                // Appel de la fonction ReturnVue_lieu_with_line
                List<LieuWithLine> lieuWithLines = ReturnVue_lieu_with_line(statement);

                // Affichage des résultats
                lieuWithLines.forEach(lieuWithLine -> System.out.println(lieuWithLine));
                return FromListToJson(lieuWithLines);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        CallFunctions();
    }
}

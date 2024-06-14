import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GareDatabase {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/locomotive";
        String user = "root";
        String password = "Romain_09";

        try {
            Map<Integer, Line> linesMap = new HashMap<>();
            // Chargement du driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établissement de la connexion
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion réussie !");

            // Création d'une requête
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id,name FROM Ligne;");

            // Traitement des résultats
            while (resultSet.next()) {
                int id  = resultSet.getInt("id");
                String name = resultSet.getString("name");
                linesMap.put(id, new Line(name));
            }
            System.out.println("Liste des lignes : " + linesMap);
            // Fermeture de la connexion
            resultSet.close();

            resultSet = statement.executeQuery("SELECT id,name,id_ligne FROM gare;");

            while (resultSet.next()) {
                int id  = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int id_ligne = resultSet.getInt("id_ligne");
                new Gare(id,name,linesMap.get(id_ligne));
            }

            // Fermeture de la connexion
            resultSet.close();

            for (Integer id : linesMap.keySet()) {
                System.out.println("lines: " +linesMap.get(id));
                System.out.println("Gares: " +linesMap.get(id).getListeGare());
            }

            resultSet = statement.executeQuery("SELECT c.id_Gare1, c.id_Gare2, temps,bidirectionnel,id_ligne FROM contient c, voie v where v.id=c.id_Voie and v.id_ligne!=0;");

            // Traitement des résultats
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

            System.out.println("\n");
            for (Integer id : linesMap.keySet()) {
                System.out.println("lines: " +linesMap.get(id));
                System.out.println("Gares: " +linesMap.get(id).getListeGare());
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




            statement.close();
            connection.close();

            String name_start = "Gare du Nord_4";
            String name_end = "Chatelet";

            System.out.println("\nYens\n");

            Gare start = null;
            Gare end = null;
            for (Integer id : linesMap.keySet()) {
                Line line = linesMap.get(id);
                Gare start_temp = line.findGare_with_name(name_start);
                if (start_temp != null) {
                    start = start_temp;
                }
                Gare end_temp = line.findGare_with_name(name_end);
                if (end_temp != null) {
                    end = end_temp;
                }
            }


            System.out.println("Start: " + start + "\nEnd: " + end);
            //crée filtreLine
            List<Line> filtreLine = new ArrayList<>();

            if (start != null && end != null) {

                YenKSP.Result result = YenKSP.yenKSP(start, end,3,filtreLine);
                if (result.chemins != null && result.temps != null) {
                    for (int i = 0; i < result.chemins.size(); i++) {
                        System.out.println("Chemin " + (i + 1) + ":");
                        for (Gare gare : result.chemins.get(i)) {
                            System.out.print(gare.getName() + " -> ");
                        }
                        System.out.println("\nDistance totale: " + result.temps.get(i));
                    }
                } else {
                    System.out.println("Aucun chemin trouvé.");
                }
            }




        } catch (ClassNotFoundException e) {
            System.out.println("Driver non trouvé !");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion !");
            e.printStackTrace();
        }
    }
}

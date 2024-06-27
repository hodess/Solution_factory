package classe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonConverter {

    // Fonction pour convertir List<List<Gare>> en JSON
    public static String convert_list_line(List<Line> ListGare) {
        System.out.println("Convert List<List<Gare>> to JSON");
        System.out.println(ListGare);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // Pour formater joliement le JSON

        try {
            return mapper.writeValueAsString(ListGare);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null; // Ou gérer l'erreur autrement selon le cas
        }
    }

    public static String convert_chemin(YenKSP.Result ListGare) {
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder jsonBuilder = new StringBuilder("{\"chemins\":");

        List<Map<String, Object>> chemins = new ArrayList<>();

        // Parcourir chaque chemin avec ses gares et temps correspondants
        for (int i = 0; i < ListGare.chemins.size(); i++) {
            List<Gare> gares = ListGare.chemins.get(i);
            int temps = ListGare.temps.get(i);

            // Utiliser une map pour regrouper les gares par ligne
            Map<String, Map<String, List<Map<String, Object>>>> lignesGares = new HashMap<>();

            // Regrouper les gares par ligne
            for (Gare gare : gares) {
                String nomLigne = gare.getNomLigne();
                if (!lignesGares.containsKey(nomLigne)) {
                    lignesGares.put(nomLigne, new HashMap<>());
                }
                if (!lignesGares.get(nomLigne).containsKey("Gare")) {
                    lignesGares.get(nomLigne).put("Gare", new ArrayList<>());
                }
                Map<String, Object> gareMap = new HashMap<>();
                gareMap.put("name", gare.getName());
                gareMap.put("coord", gare.getCoord());
                // Ajouter d'autres attributs si nécessaire
                lignesGares.get(nomLigne).get("Gare").add(gareMap);
            }

            // Construire l'objet pour ce chemin
            Map<String, Object> chemin = new HashMap<>();
            for (Map.Entry<String, Map<String, List<Map<String, Object>>>> entry : lignesGares.entrySet()) {
                String nomLigne = entry.getKey();
                Map<String, List<Map<String, Object>>> ligneMap = entry.getValue();
                chemin.put(nomLigne, ligneMap);
            }
            chemin.put("temps", temps);
            chemins.add(chemin);
        }

        try {
            jsonBuilder.append(mapper.writeValueAsString(chemins));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }
}

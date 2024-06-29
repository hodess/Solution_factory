package classe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


import java.util.*;

public class JsonConverter {
    public static final ObjectMapper objectMapper = new ObjectMapper();
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
            double distance = ListGare.distance.get(i);

            // Utiliser une LinkedHashMap pour préserver l'ordre des gares par ligne
            Map<String, Map<String, List<Map<String, Object>>>> lignesGares = new LinkedHashMap<>();

            // Regrouper les gares par ligne en préservant l'ordre
            for (Gare gare : gares) {
                String nomLigne = gare.getNomLigne();
                lignesGares.putIfAbsent(nomLigne, new LinkedHashMap<>());
                lignesGares.get(nomLigne).putIfAbsent("Gare", new ArrayList<>());
                Map<String, Object> gareMap = new HashMap<>();
                gareMap.put("name", gare.getName());
                gareMap.put("coord", gare.getCoord());
                // Ajouter d'autres attributs si nécessaire
                lignesGares.get(nomLigne).get("Gare").add(gareMap);
            }

            // Construire l'objet pour ce chemin
            Map<String, Object> chemin = new LinkedHashMap<>();
            for (Map.Entry<String, Map<String, List<Map<String, Object>>>> entry : lignesGares.entrySet()) {
                String nomLigne = entry.getKey();
                Map<String, List<Map<String, Object>>> ligneMap = entry.getValue();
                chemin.put(nomLigne, ligneMap);
            }
            chemin.put("temps", temps);
            chemin.put("distance", distance);
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

    public static String convertObjectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

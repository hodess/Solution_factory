package test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class main_personne {
    public static String main() {
        // Créer une instance de Personne
        Personne personne = new Personne("John Doe", 30);

        // Créer un ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(personne);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}

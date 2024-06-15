import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.Duration;
import java.time.Instant;

public class secondmain {
    public static void main(String[] args) {
        long seed = 87;

        Random random = new Random(seed);
        int nbGare = 10;  // Remplacez par le nombre de gares souhaité

        // Créer des lignes
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            lines.add(new Line(String.valueOf(i)));
        }

        // Créer des gares
        List<Gare> gares = new ArrayList<>();
        for (int i = 0; i < nbGare; i++) {
            gares.add(new Gare(i,"Gare " + i, lines.get(0)));
        }

        // Créer des voies aléatoires entre les gares
        for (int i = 0; i < nbGare * 2; i++) {
            Gare secGare = gares.get(random.nextInt(gares.size()));
            while (gares.get(i / 2).equals(secGare)) {  // Assurez-vous que start et end ne sont pas les mêmes
                secGare = gares.get(random.nextInt(gares.size()));
            }
            int randTemp = 60 + random.nextInt(241);
            Line randLigne = lines.get(random.nextInt(lines.size()));

            // Création d'une voie bidirectionnelle
            new Voie(gares.get(i / 2), secGare, randLigne, randTemp,1);
        }

        // Sélectionner des gares de départ et d'arrivée aléatoirement
        Gare start = gares.get(random.nextInt(gares.size()));
        Gare end = gares.get(random.nextInt(gares.size()));
        while (start.equals(end)) {  // Assurez-vous que start et end ne sont pas les mêmes
            end = gares.get(random.nextInt(gares.size()));
        }

        System.out.println("Départ: " + start.getName() + ", Arrivée: " + end.getName());

        // Appeler l'algorithme YenKSP
        List<Line> filtreLine = new ArrayList<>();
        List<Voie> filtreVoie = new ArrayList<>();
        YenKSP.Result result = YenKSP.yenKSP(start, end, 2,filtreVoie,filtreLine);  // Trouver les 3 plus courts chemins

        // Afficher le résultat
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
}


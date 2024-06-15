import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Créer des lignes
        Line line5 = new Line("5");
        Line line4 = new Line("4");
        Line line0 = new Line("0");

        // Créer des gares
        Gare gare1 = new Gare(1,"Gare du Nord_4", line4);
        Gare gare1_2 = new Gare(2,"Gare du Nord_5", line5);
        Gare gare2 = new Gare(3,"Gare de Lyon", line4);
        Gare gare3 = new Gare(4,"Gare de l'est", line5);
        Gare gare4 = new Gare(5,"Gare Montparnasse", line5);
        Gare gare7 = new Gare(6,"Chatelet", line5);

        // Créer des voies
        new Voie(gare1_2,gare1, line0, 60,1);
        new Voie(gare1, gare2, line4, 180,1);
        new Voie(gare3, gare4, line5, 120,1);
        new Voie(gare1_2, gare3, line5, 300,1);
        new Voie(gare4, gare7, line5, 180,1);
        new Voie(gare7, gare2, line4, 120,1);

        Voie voie=new Voie(gare2, gare7, line4, 120,1);

        // Points de départ et d'arrivée
        Gare start = gare1;
        Gare end = gare7;

        // Appeler l'algorithme de Dijkstra
        List<Voie> filtreVoie = new ArrayList<>();
        //filtreVoie.add(voie);
        List<Line> filtreLine = new ArrayList<>();
        YenKSP.Result result = YenKSP.yenKSP(start, end,3,filtreVoie,filtreLine);

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


        /*Dijkstra.Result result = Dijkstra.dijkstra(start, end, filtreVoie, filtreLine);
        //afficher le resultat
        if (result.chemin != null) {
            System.out.println("Chemin:");
            for (Gare gare : result.chemin) {
                System.out.print(gare.getName() + " -> ");
            }
            System.out.println("\nDistance totale: " + result.distance);
        } else {
            System.out.println("Aucun chemin trouvé.");
        }*/
    }
}
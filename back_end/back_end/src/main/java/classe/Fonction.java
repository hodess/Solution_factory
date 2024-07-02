package classe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fonction {
    public static Map<String, Line> linesMap = new HashMap<>();

    public static void setLinesMap(Map<String, Line> linesMap) {
        Fonction.linesMap = linesMap;
    }

    public static String find_chemin_start_end(String name_start, String name_end) {

        List<List<Gare>> gares = YenKSP.findGare(linesMap, name_start, name_end);
        List<Gare> start = gares.get(0);
        List<Gare> end = gares.get(1);

        if (start == null || end == null) {
            System.out.println("Gare de départ ou d'arrivée introuvable.");
            return null;
        }
        List<Line> filtreLine = new ArrayList<>();
        YenKSP.Result result = YenKSP.ret_yens(start, end, filtreLine);
        return JsonConverter.convert_chemin(result);
    }

    public static String all_gares(){
        List<Line> linesList = new ArrayList<>(linesMap.values());
        return JsonConverter.convert_list_line(linesList);
    }

    public static boolean main_connexite() {
        Create_class.create_all_class();
        String start = "Maison Blanche";
        String end = "Jussieu";

        List<List<Gare>> gares = YenKSP.findGare(linesMap, start, end);
        Gare start_gare = gares.get(0).get(0);
        return connexite.is_connected_dfs(start_gare,linesMap);
    }

    public static int deja_init(){
        return Gare.nbGare;
    }

    public static void clear_all() {
        for (Line line : linesMap.values()) {
            if (line.getListeGare() != null) {
                for (Gare gare : line.getListeGare()) {
                    if (gare.getVoie() != null) {
                        for (Voie voie : gare.getVoie()) {
                            voie.clear();
                        }
                    }
                    gare.clear();
                }
            }
            line.clear();
        }
        linesMap.clear(); // Optionally clear the map itself
        System.gc();
    }

    public static void main(String[] args) {
            Create_class.create_all_class();
            String start = "Aimé Césaire";
            String end = "Bibliothèque François Mitterrand";
            System.out.println(find_chemin_start_end(start, end));
        }
}

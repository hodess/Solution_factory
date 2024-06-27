package classe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fonction {
    public static Map<Integer, Line> linesMap = new HashMap<>();

    public static void setLinesMap(Map<Integer, Line> linesMap) {
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
        YenKSP.affichage(start, end, filtreLine);
        YenKSP.Result result = YenKSP.ret_yens(start, end, filtreLine);
        return JsonConverter.convert_chemin(result);
    }

    public static String all_gares(){
        List<Line> linesList = new ArrayList<>(linesMap.values());
        return JsonConverter.convert_list_line(linesList);
    }
}

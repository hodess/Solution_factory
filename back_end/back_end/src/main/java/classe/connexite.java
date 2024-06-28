package classe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class connexite {

    public static boolean is_connected_dfs(Gare start, Map<String, Line> linesMap) {
        List<String> lignesMetro = Arrays.asList(" 1"," 2", " 3", " 3B", " 4", " 5", " 6", " 7", " 7B", " 8", " 9", " 10", " 11", " 12", " 13", " 14" , " B"," A"," C"," D"," E" );
        List<Gare> visited = new ArrayList<>();
        List<Gare> stack = new ArrayList<>();
        stack.add(start);

        while (!stack.isEmpty()) {
            Gare current = stack.remove(stack.size() - 1);
            if (!visited.contains(current)) {
                visited.add(current);
                for (Voie voie : current.getVoie()) {
                    Gare next = voie.getGare1().equals(current) ? voie.getGare2() : voie.getGare1();
                    if (!lignesMetro.contains(next.getLigne().getName())) {
                        continue;
                    }
                    if (!visited.contains(next)) {
                        stack.add(next);
                    }
                }
            }
        }
        List<Gare> gares = new ArrayList<>();
        int count_element_gare=0;
        for (Line line : linesMap.values()) {
            if (lignesMetro.contains(line.getName())) {
                count_element_gare += line.getListeGare().size();
                gares.addAll(line.getListeGare());
            }
        }

        /*for (Gare gare : gares) {
            if (!visited.contains(gare)) {
                System.out.println("gare non visité: " + gare.getName());
            }
        }

        System.out.println("count_element_gare: " + count_element_gare + " visited.size(): " + visited.size());*/


        //On doit retirer 1 car Nanterre La Folie est une nouvelle gare donc elle est pas encore relié
        return visited.size() == (count_element_gare-1);
    }
}

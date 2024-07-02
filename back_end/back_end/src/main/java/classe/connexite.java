package classe;

import org.springframework.util.StopWatch;

import java.util.*;

public class connexite {

    public static boolean is_connected_dfs(Gare start, Map<String, Line> linesMap) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<String> lignesMetro = Arrays.asList(" 1"," 2", " 3", " 3B", " 4", " 5", " 6", " 7", " 7B", " 8", " 9", " 10", " 11", " 12", " 13", " 14" , " B"," A"," C"," D"," E" );
        List<Gare> visited = new ArrayList<>();
        List<Gare> stack = new ArrayList<>();
        stack.add(start);

        int maxDepth = 0;

        while (!stack.isEmpty()) {
            Gare current = stack.remove(stack.size() - 1);
            if (!visited.contains(current)) {
                visited.add(current);
                int currentDepth = stack.size();
                maxDepth = Math.max(maxDepth, currentDepth);
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
        System.out.println("Profondeur maximale atteinte pendant DFS : " + maxDepth);
        stopWatch.stop();
        System.out.println("Execution time of DFS: "+stopWatch.getTotalTimeMillis()+" ms");

        //On doit retirer 1 car Nanterre La Folie est une nouvelle gare donc elle est pas encore relié
        return visited.size() == (count_element_gare-1);
    }

    public static boolean is_connected_bfs(Gare start, Map<String, Line> linesMap) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<String> lignesMetro = Arrays.asList(" 1"," 2", " 3", " 3B", " 4", " 5", " 6", " 7", " 7B", " 8", " 9", " 10", " 11", " 12", " 13", " 14" , " B"," A"," C"," D"," E" );
        List<Gare> visited = new ArrayList<>();
        Queue<Gare> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);

        int maxWidth = 0;


        while (!queue.isEmpty()) {
            int currentWidth = queue.size();
            maxWidth = Math.max(maxWidth, currentWidth);
            Gare current = queue.poll();

            for (Voie voie : current.getVoie()) {
                Gare next = voie.getGare1().equals(current) ? voie.getGare2() : voie.getGare1();

                if (!lignesMetro.contains(next.getLigne().getName()) || visited.contains(next)) {
                    continue;
                }

                visited.add(next);
                queue.add(next);
            }
        }

        List<Gare> gares = new ArrayList<>();
        int count_element_gare = 0;
        for (Line line : linesMap.values()) {
            if (lignesMetro.contains(line.getName())) {
                count_element_gare += line.getListeGare().size();
                gares.addAll(line.getListeGare());
            }
        }

        System.out.println("Largeur maximale de la file d'attente pendant BFS : " + maxWidth);
        stopWatch.stop();
        System.out.println("Execution time of BFS : "+stopWatch.getTotalTimeMillis()+" ms");

        // Vérifie si toutes les stations accessibles via lignes de métro sont visitées
        return visited.size() == (count_element_gare-1);
    }

}

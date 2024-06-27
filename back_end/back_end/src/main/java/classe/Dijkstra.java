package classe;

import java.util.*;

public class Dijkstra {

    public static class Result {
        public List<Gare> chemin;
        public Integer distance;

        public Result(List<Gare> chemin, Integer distance) {
            this.chemin = chemin;
            this.distance = distance;
        }
    }

    public static Result dijkstra(Gare start, Gare end, List<Voie> filtreVoie, List<Line> filtreLine) {
        Set<Gare> T = new HashSet<>();
        Map<Gare, Integer> distance = new HashMap<>();
        Map<Gare, Gare> parent = new HashMap<>();

        T.add(start);
        distance.put(start, 0);
        parent.put(start, null);

        for (Voie lien : start.getVoie()) { // initialisation
            if (!filtreLine.contains(lien.getLigne()) && !filtreVoie.contains(lien)) {

                Gare voisin = lien.getOther(start);
                if((lien.getBidirectionnel()==1) || (lien.getBidirectionnel()==0 && lien.getGare1()==start && lien.getGare2()==voisin))
                {
                    distance.put(voisin, lien.getTemps());
                    parent.put(voisin, start);
                }
            }
        }

        while (T.size() < Voie.getNombreDElements()) { // boucle principale
            Gare t = null;
            int minDistance = Integer.MAX_VALUE;

            for (Map.Entry<Gare, Integer> entry : distance.entrySet()) {
                Gare gare = entry.getKey();
                int dist = entry.getValue();
                if (!T.contains(gare) && dist < minDistance) {
                    minDistance = dist;
                    t = gare;
                }
            }

            if (t == end) {
                break;
            } else if (t == null) {
                return new Result(null, null);
            }

            T.add(t);

            for (Voie lien : t.getVoie()) {
                Gare voisin = lien.getOther(t);
                if (!T.contains(voisin) && !filtreLine.contains(lien.getLigne()) && !filtreVoie.contains(lien)) {
                    int newDistance = distance.get(t) + lien.getTemps();
                    if (newDistance < distance.getOrDefault(voisin, Integer.MAX_VALUE)) {
                        if((lien.getBidirectionnel()==1) || (lien.getBidirectionnel()==0 && lien.getGare1()==t && lien.getGare2()==voisin))
                        {
                            distance.put(voisin, newDistance);
                            parent.put(voisin, t);
                        }

                        //System.out.println("Voie " + lien +" " +filtreVoie.contains(lien) + "  " + lien.getTemps() + "\n " + distance.get(voisin) + "  "+ distance.get(t) + " "+t.getName());
                    }
                }
            }
        }

        if (!distance.containsKey(end)) {
            return new Result(null, null);
        }

        List<Gare> chemin = new ArrayList<>();
        Gare current = end;
        while (current != null) {
            chemin.add(0, current);
            current = parent.get(current);
        }

        if (distance.get(end) == Integer.MAX_VALUE) {
            return new Result(null, null);
        }

        //System.out.println("Chemin: " + chemin + "\nDistance: " + distance.get(end)+"\n\n");

        return new Result(chemin, distance.get(end));
    }
}


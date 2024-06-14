import java.util.ArrayList;
import java.util.List;

public class YenKSP {
    public static class Result {
        public List<List<Gare>> chemins;
        public List<Integer> temps;

        public Result(List<List<Gare>> chemins, List<Integer> temps) {
            this.chemins = chemins;
            this.temps = temps;
        }
    }

    public static Result yenKSP(Gare start, Gare end, int k,List<Line> filtreLine) {
        List<List<Gare>> A = new ArrayList<>();
        List<Integer> tempsAll = new ArrayList<>();

        Dijkstra.Result dijkstraResult = Dijkstra.dijkstra(start, end, new ArrayList<>(), filtreLine);
        if (dijkstraResult.chemin == null) {
            return new Result(null, null);
        }
        A.add(dijkstraResult.chemin);
        tempsAll.add(dijkstraResult.distance);

        List<List<Gare>> B = new ArrayList<>();
        List<Integer> BTemp = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < A.get(i).size() - 1; j++) {
                Gare spurnode = A.get(i).get(j);
                List<Gare> rootpath = new ArrayList<>(A.get(i).subList(0, j + 1));

                List<Voie> filtreVoie = new ArrayList<>();
                for (int m = 0; m < A.size(); m++) {
                    for (int n = 0; n < A.get(m).size() - 1; n++) {
                        if (rootpath.contains(A.get(m).get(n))) {
                            Voie tempVoie = A.get(m).get(n).findVoie(A.get(m).get(n + 1));
                            if (!filtreVoie.contains(tempVoie)) {
                                filtreVoie.add(tempVoie);
                            }
                        }

                        if (A.get(m).get(n) == spurnode) {
                            Voie tempVoie = A.get(m).get(n).findVoie(A.get(m).get(n + 1));
                            if (!filtreVoie.contains(tempVoie)) {
                                filtreVoie.add(tempVoie);
                            }
                        }
                    }
                }

                /*System.out.println("FiltreVoie: " + filtreVoie);
                System.out.println("Rootpath: " + rootpath);
                System.out.println("Spurnode: " + spurnode);
                System.out.println();*/

                Dijkstra.Result spurResult = Dijkstra.dijkstra(spurnode, end, filtreVoie, filtreLine);
                if (spurResult.chemin == null) {
                    continue;
                }

                System.out.println("\n\n");

                List<Gare> totalPath = new ArrayList<>(rootpath);
                spurResult.chemin.remove(0);
                totalPath.addAll(spurResult.chemin);
                int totalTime = spurResult.distance + findTemps(rootpath);

                if (!B.contains(totalPath)) {
                    B.add(totalPath);
                    BTemp.add(totalTime);
                }
            }

            if (B.isEmpty()) {
                break;
            }

            int indexMinTime = 0;
            for (int m = 1; m < B.size(); m++) {
                if (BTemp.get(m) < BTemp.get(indexMinTime)) {
                    indexMinTime = m;
                }
            }

            A.add(B.get(indexMinTime));
            tempsAll.add(BTemp.get(indexMinTime));

            B.remove(indexMinTime);
            BTemp.remove(indexMinTime);
        }

        return new Result(A, tempsAll);
    }

    private static int findTemps(List<Gare> gares) {
        int totalTime = 0;
        for (int i = 0; i < gares.size() - 1; i++) {
            Voie voie = gares.get(i).findVoie(gares.get(i + 1));
            if (voie != null) {
                totalTime += voie.getTemps();
            }
        }
        return totalTime;
    }
}

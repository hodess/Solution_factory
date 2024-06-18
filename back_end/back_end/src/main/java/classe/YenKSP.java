package classe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YenKSP {
    public static class Result {
        public List<List<Gare>> chemins;
        public List<Integer> temps;

        public Result(List<List<Gare>> chemins, List<Integer> temps) {
            this.chemins = chemins;
            this.temps = temps;
        }

        public Result() {
            this.chemins = null;
            this.temps = null;
        }
    }

    public static Result yenKSP(Gare start, Gare end, int k,List<Voie> filtreVoie_before,List<Line> filtreLine) {
        List<List<Gare>> A = new ArrayList<>();
        List<Integer> tempsAll = new ArrayList<>();
        List<Voie> filtreVoie = new ArrayList<>();
        for (Voie voie : filtreVoie_before) {
            if (!filtreVoie.contains(voie)) {
                filtreVoie.add(voie);
            }
        }

        Dijkstra.Result dijkstraResult = Dijkstra.dijkstra(start, end, filtreVoie, filtreLine);
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

                filtreVoie = new ArrayList<>();
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
                for (Voie voie : filtreVoie_before) {
                    if (!filtreVoie.contains(voie)) {
                        filtreVoie.add(voie);
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

    public static List<List<Gare>> findGare(Map<Integer, Line> linesMap, String name_start,String name_end) {
        List<Gare> start = new ArrayList<>();
        List<Gare> end = new ArrayList<>();
        for (Integer id : linesMap.keySet()) {
            Line line = linesMap.get(id);
            Gare start_temp = line.findGare_with_name(name_start);
            if (start_temp != null) {
                start.add(start_temp);
            }
            Gare end_temp = line.findGare_with_name(name_end);
            if (end_temp != null) {
                end.add(end_temp);
            }
        }
        List<List<Gare>> gares = new ArrayList<>();
        gares.add(start);
        gares.add(end);
        return gares;
    }


    public static Result yenKSP_multiple_start_end(List<Gare> start, List<Gare> end, int k,List<Line> filtreLine){
        List<Voie> filtreVoie = new ArrayList<>();
        for (Voie voie_start : start.get(0).getVoie()) {
            //enlever toute les voie entre les start
            if(voie_start.getLigne().getName().equals("Ligne 0")){
                filtreVoie.add(voie_start);
            }
        }
        for (Voie voie_end : end.get(0).getVoie()) {
            //enlever toute les voie entre les end
            if(voie_end.getLigne().getName().equals("Ligne 0")){
                filtreVoie.add(voie_end);
            }
        }

        Result result_all = new Result();
        for (Gare gare_start : start) {
            for (Gare gare_end : end) {
                Result result = yenKSP(gare_start, gare_end, k,filtreVoie, filtreLine);

                if (result_all.chemins == null) {
                    result_all = result;
                } else {
                    if (result.chemins != null && result.temps != null){
                        result_all.chemins.addAll(result.chemins);
                        result_all.temps.addAll(result.temps);
                    }
                }
            }
        }

        //prendre juste les 5 meuilleur temps
        List<List<Gare>> chemin = new ArrayList<>();
        List<Integer> temps = new ArrayList<>();
        int i = 0;
        while (i < 5 && result_all.temps.size() > 0) {
            int indexMinTime = 0;
            for (int m = 1; m < result_all.temps.size(); m++) {
                if (result_all.temps.get(m) < result_all.temps.get(indexMinTime)) {
                    indexMinTime = m;
                }
            }
            chemin.add(result_all.chemins.get(indexMinTime));
            temps.add(result_all.temps.get(indexMinTime));
            result_all.chemins.remove(indexMinTime);
            result_all.temps.remove(indexMinTime);
            i++;
        }

        return new Result(chemin,temps);
    }

    public static Result ret_yens(List<Gare> start,List<Gare> end,List<Line> filtreLine){

        int nb_result = 4;

        System.out.println("\nYens\n");
        System.out.println("Start: " + start + "\nEnd: " + end);
        if (start != null && end != null) {

            return YenKSP.yenKSP_multiple_start_end(start, end,nb_result,filtreLine);

        }
        else{
            return new Result();
        }
    }

    public static void affichage(List<Gare> start,List<Gare> end,List<Line> filtreLine){

        int nb_result = 4;

        System.out.println("\nYens\n");
        System.out.println("Start: " + start + "\nEnd: " + end);
        if (start != null && end != null) {

            YenKSP.Result result = YenKSP.yenKSP_multiple_start_end(start, end,nb_result,filtreLine);
            if (result.chemins != null && result.temps != null) {
                for (int i = 0; i < result.chemins.size(); i++) {
                    System.out.println("Chemin " + (i + 1) + ":");
                    for (Gare gare : result.chemins.get(i)) {
                        System.out.print(gare.getName() +","+gare.getLigne().getName() + " -> ");
                    }
                    System.out.println("\nDistance totale: " + result.temps.get(i));
                }
            } else {
                System.out.println("Aucun chemin trouvé.");
            }
        }
    }
}

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
                            //System.out.println("Gare 1  : " + A.get(m).get(n) + " Gare 2  " + A.get(m).get(n + 1) + " " + tempVoie);
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
                //System.out.println("spurnode: " + spurnode.getName() + " " + spurnode.getLigne().getName() + ", next node : "+ A.get(i).get(j+1).getName() +" " + A.get(i).get(j+1).getLigne().getName()  +" \t voie : " +spurnode.findVoie(A.get(i).get(j+1)));
                //On regarde si on est deja passer par la voie 0 entre les deux gares
                if (spurnode.findVoie(A.get(i).get(j+1)).getLigne().getName().equals("0")){
                    for (Voie voie_start : A.get(i).get(j+1).getVoie()) {
                        //System.out.println("Voie 0  : "+ voie_start.getGare1().getName() + "  " + voie_start.getGare1().getLigne().getName() + "  " + voie_start.getGare2().getName() + voie_start.getGare2().getLigne().getName() + "\n");
                        if ((voie_start.getLigne().getName().equals("0")) && voie_start.getGare2() == A.get(i).get(j+1)) {
                            filtreVoie.add(voie_start);
                        }
                    }
                }

                if(j>0){
                    if (A.get(i).get(j-1).findVoie(spurnode).getLigne().getName().equals("0")){
                        for (Voie voie_start : spurnode.getVoie()) {
                            //System.out.println("Voie 0  : "+ voie_start.getGare1().getName() + "  " + voie_start.getGare1().getLigne().getName() + "  " + voie_start.getGare2().getName() + voie_start.getGare2().getLigne().getName() + "\n");
                            if (voie_start.getLigne().getName().equals("0")) {
                                filtreVoie.add(voie_start);
                            }
                        }
                    }
                    //System.out.println("spurnode: " + spurnode.getName() + " " + spurnode.getLigne().getName() + ", last node :" + A.get(i).get(j-1).getName()+ " "+ A.get(i).get(j-1).getLigne().getName() +" \t voie : " +A.get(i).get(j-1).findVoie(spurnode));
                }

                Dijkstra.Result spurResult = Dijkstra.dijkstra(spurnode, end, filtreVoie, filtreLine);

                if (spurResult.chemin == null) {
                    continue;
                }

                List<Gare> totalPath = new ArrayList<>(rootpath);
                spurResult.chemin.remove(0);
                totalPath.addAll(spurResult.chemin);
                int totalTime = spurResult.distance + findTemps(rootpath);

                //aficher B
                if (!B.contains(totalPath)) {
                    /*System.out.println("TotalPath: " + totalPath);
                    System.out.println("TotalTime: " + totalTime);*/
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

           /* System.out.println("B: " + B);
            System.out.println("BTemp: " + BTemp);
            System.out.println("A: " + A);
            System.out.println("TempsAll: " + tempsAll);
            System.out.println("A contient: " + !A.contains(B.get(indexMinTime)));
            if (!A.contains(B.get(indexMinTime))) {
                A.add(B.get(indexMinTime));
                tempsAll.add(BTemp.get(indexMinTime));
                System.out.println("B existe pas");
            }*/

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

    public static List<List<Gare>> findGare(Map<String, Line> linesMap, String name_start,String name_end) {
        List<Gare> start = new ArrayList<>();
        List<Gare> end = new ArrayList<>();
        for (String id : linesMap.keySet()) {
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


    public static Result yenKSP_multiple_start_end(List<Gare> start, List<Gare> end, int k,List<Line> filtreLine,int nb_result){
        List<Voie> filtreVoie = new ArrayList<>();
        //System.out.println("Start: " + start + "\nEnd: " + end);
        for (Gare gare_start : start) {
            for (Voie voie_start : gare_start.getVoie()) {
                //enlever toute les voie entre les start
                if (voie_start.getLigne().getName().equals("0")) {
                    filtreVoie.add(voie_start);
                }
            }
        }
        for (Gare gare_end : end) {
            for (Voie voie_end : gare_end.getVoie()) {
                //enlever toute les voie entre les end
                if (voie_end.getLigne().getName().equals("0")) {
                    filtreVoie.add(voie_end);
                }
            }
        }

        Result result_all = new Result();
        for (Gare gare_start : start) {
            for (Gare gare_end : end) {
                Result result = yenKSP(gare_start, gare_end, k,filtreVoie, filtreLine);
                //System.out.println("Result: " + result.chemins + " " + result.temps);

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
        while (i < nb_result+1 && result_all.temps.size() > 0) {
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

        int nb_result = 2;

        //System.out.println("\nYens\n");
        //System.out.println("Start: " + start + "\nEnd: " + end);
        if (start != null && end != null) {

            return YenKSP.yenKSP_multiple_start_end(start, end,nb_result,filtreLine,nb_result);

        }
        else{
            return new Result();
        }
    }

    public static void affichage(List<Gare> start,List<Gare> end,List<Line> filtreLine){

        int nb_result = 2;

        System.out.println("\nYens\n");
        System.out.println("Start: " + start + "\nEnd: " + end);
        if (start != null && end != null) {

            YenKSP.Result result = YenKSP.yenKSP_multiple_start_end(start, end,nb_result,filtreLine,nb_result);
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

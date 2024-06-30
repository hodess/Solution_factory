package classe;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


import static classe.ReturnGareNoTraited.FromListToJson;

public class ReturnGareWithOrder {

    public static boolean isLineOnedirectionnal(Line line,List<Gare> terminus) {
        for (Gare gare : line.getListeGare()){
            if(gare.getVoie().size()==2 && !terminus.contains(gare)){
                return true;
            }
        }
        return false;
    }


//    public static int HowManyGaresConnected(Gare gare){
//        List<Gare> gares = new ArrayList<>();
//        List<String> GareDone=new ArrayList<>();
//        GareDone.add(gare.getName());
//        for (Voie voie : gare.getVoie()) {
//            if (!GareDone.contains(voie.getGare1().getName()) || !GareDone.contains(voie.getGare2().getName())){
//                if(voie.getGare1().getName().equals(gare.getName())){
//                    GareDone.add(voie.getGare2().getName());
//                }else{
//                    GareDone.add(voie.getGare1().getName());
//                }
//            }
//        }
//        //Enlever la gare de base de gare done
//        GareDone.remove(gare.getName());
//        return GareDone.size();
//    }


    public static List<Gare> FindTerminus(List<Gare> gares){
        List<Gare> terminus = new ArrayList<>();
        for (Gare gare : gares) {
            int count = 0;
            List<String> GareDone=new ArrayList<>();
            GareDone.add(gare.getName());
            for (Voie voie : gare.getVoie()) {
                if (!GareDone.contains(voie.getGare1().getName()) || !GareDone.contains(voie.getGare2().getName())){
                    if(voie.getGare1().getName().equals(gare.getName())){
                        GareDone.add(voie.getGare2().getName());
                    }else{
                        GareDone.add(voie.getGare1().getName());
                    }
                    count++;
                }
            }
            if (count == 1) {
                terminus.add(gare);
            }
        }
//        System.out.println(terminus.size());
        return terminus;
    }

    private static Map<String, Line> linesMap;

    public static class LieuWithLineAndOrder {
        private String name;
        private String nameLigne;
        private int order;

        public LieuWithLineAndOrder(String name, String nameLigne, int order) {
            this.name = name;
            this.nameLigne = nameLigne;
            this.order = order;
        }

        public String getName() {
            return name;
        }

        public String getNameLigne() {
            return nameLigne;
        }

        public int getOrder() {
            return order;
        }



        @Override
        public String toString() {
            return "LieuWithLineAndOrder{" +
                    "name='" + name + '\'' +
                    ", nameLigne='" + nameLigne + '\'' +
                    ", order=" + order +
                    '}';
        }
    }
    public static HashMap<String,List<LieuWithLineAndOrder>> ReturnVue_lieu_with_line_and_order()  {
        HashMap<String,List<LieuWithLineAndOrder>> listFinalOfLines = new HashMap<>();
        for (Line line : linesMap.values()) {
            if (Arrays.asList("0", "A", "B", "C", "D", "E").contains(line.getName().strip())){
                continue;
            }
            List<LieuWithLineAndOrder> lieuxWithLinesAndOrder = new ArrayList<>();
            List<Gare> terminus = new ArrayList<>();
            terminus=ReturnGareWithOrder.FindTerminus(line.getListeGare());
            if(isLineOnedirectionnal(line,terminus) && terminus.size()>1){
                int i=0;
                for(Gare terminu :terminus){
                    i++;
                    lieuxWithLinesAndOrder.add(new LieuWithLineAndOrder(terminu.getName(),terminu.getLigne().getName(),1));
                    Gare current=terminu;
                    List<String> gareDone = new ArrayList<>();
                    gareDone.add(current.getName());
                    while (current==terminu || !terminus.contains(current) ) {
                        if((current.getName().equals("Porte d'Auteuil")) && terminu.getName().equals("Gare d'Austerlitz")){
                            break;
                        }
                        int VoieToTake ; // Initialisation par défaut à -1

                        if (!gareDone.contains(current.getVoie().get(0).getGare2().getName())) {

                                VoieToTake = 0;

                        } else if (!gareDone.contains(current.getVoie().get(1).getGare2().getName())) {

                                VoieToTake = 1;

                        } else if (!gareDone.contains(current.getVoie().get(2).getGare2().getName())) {

                                VoieToTake = 2;

                        } else {
                            VoieToTake = 3;
                        }
                        if(current.getName().equals("Javel") && terminu.getName().equals("Boulogne Pont de Saint-Cloud")){
                            VoieToTake=2;
                        }
                        System.out.println("current : "+current.getName()+" VoieToTake : "+VoieToTake+"line : "+line.getName());
                        gareDone.add(current.getVoie().get(VoieToTake).getGare2().getName());
                        current = current.getVoie().get(VoieToTake).getGare2();
                        lieuxWithLinesAndOrder.add(new LieuWithLineAndOrder(current.getName(), current.getLigne().getName(), lieuxWithLinesAndOrder.size() + 1));
                    }
                listFinalOfLines.put(line.getName()+"/"+i,lieuxWithLinesAndOrder);
                }
            }
            else{
                if(terminus.size()>=3){
                    System.out.println("Ligne "+line.getName());
                    //fourchette
                    List<Gare> terminusTraited= new ArrayList<>();
                    terminusTraited.add(terminus.get(0));
                    lieuxWithLinesAndOrder.add(new LieuWithLineAndOrder(terminus.get(0).getName(), terminus.get(0).getLigne().getName(), 1));
                    Gare current=terminus.get(0);
                    List<String> gareDone = new ArrayList<>();
                    gareDone.add(current.getName());
                    while (current==terminus.get(0) || !terminus.contains(current) ){
                        int VoieToTake ;
                        if (!gareDone.contains(current.getVoie().get(0).getGare2().getName())) {
                            VoieToTake = 0;
                        } else if (!gareDone.contains(current.getVoie().get(1).getGare2().getName())) {
                            VoieToTake = 1;
                        } else if (!gareDone.contains(current.getVoie().get(2).getGare2().getName())) {
                            VoieToTake = 2;
                        } else {
                            VoieToTake = 3;
                        }
                        gareDone.add(current.getVoie().get(VoieToTake).getGare2().getName());
                        current = current.getVoie().get(VoieToTake).getGare2();
                        System.out.println(current.getName());
                        lieuxWithLinesAndOrder.add(new LieuWithLineAndOrder(current.getName(), current.getLigne().getName(), lieuxWithLinesAndOrder.size() + 1));
                    }
                    listFinalOfLines.put(line.getName()+"/1",lieuxWithLinesAndOrder);
                    terminusTraited.add(current);
                    lieuxWithLinesAndOrder = new ArrayList<>();
                    Gare terminusNotDone=null;
                    for (Gare terminu : terminus){
                        if(!terminusTraited.contains(terminu)){
                            terminusNotDone=terminu;
                        }
                    }
                    current=terminusNotDone;
                    List<String> gareDoneInPart2 = new ArrayList<>();
                    gareDone.add(current.getName());
                    gareDoneInPart2.add(current.getName());
                    lieuxWithLinesAndOrder.add(new LieuWithLineAndOrder(current.getName(), current.getLigne().getName(), 1));
                    while(gareDone.size()<line.getListeGare().size()){
                        int VoieToTake ;

                        if (!gareDone.contains(current.getVoie().get(0).getGare2().getName())) {

                            VoieToTake = 0;

                        } else if (!gareDone.contains(current.getVoie().get(1).getGare2().getName())) {

                            VoieToTake = 1;

                        } else if (!gareDone.contains(current.getVoie().get(2).getGare2().getName())) {

                            VoieToTake = 2;

                        } else {
                            VoieToTake = 3;
                        }
                        System.out.println(current.getName());
                        gareDone.add(current.getVoie().get(VoieToTake).getGare2().getName());
                        gareDoneInPart2.add(current.getVoie().get(VoieToTake).getGare2().getName());
                        current = current.getVoie().get(VoieToTake).getGare2();
                        lieuxWithLinesAndOrder.add(new LieuWithLineAndOrder(current.getName(), current.getLigne().getName(), lieuxWithLinesAndOrder.size() + 1));;
                    }
                    //add to lieuwithlinethenext
                    int VoieToTake;
                    if (!gareDoneInPart2.contains(current.getVoie().get(0).getGare2().getName())) {

                        VoieToTake = 0;

                    } else if (!gareDoneInPart2.contains(current.getVoie().get(1).getGare2().getName())) {

                        VoieToTake = 1;

                    } else if (!gareDoneInPart2.contains(current.getVoie().get(2).getGare2().getName())) {

                        VoieToTake = 2;

                    } else {
                        VoieToTake = 3;
                    }
                    gareDoneInPart2.add(current.getVoie().get(VoieToTake).getGare2().getName());
                    current = current.getVoie().get(VoieToTake).getGare2();
                    lieuxWithLinesAndOrder.add(new LieuWithLineAndOrder(current.getName(), current.getLigne().getName(), lieuxWithLinesAndOrder.size() + 1));
                    listFinalOfLines.put(line.getName()+"/2",lieuxWithLinesAndOrder);
                }
//
                else{
            lieuxWithLinesAndOrder.add(new LieuWithLineAndOrder(terminus.get(0).getName(), terminus.get(0).getLigne().getName(), 1));
            Gare current=terminus.get(0);
            List<String> gareDone = new ArrayList<>();
            gareDone.add(current.getName());
            if(terminus.size()<3){
            while (line.getListeGare().size()> gareDone.size()) {
                int VoieToTake;
                if (!gareDone.contains(current.getVoie().get(0).getGare2().getName())) {
                    VoieToTake = 0;
//                    System.out.println(current.getVoie().get(0));
                } else {
                    if (!gareDone.contains(current.getVoie().get(1).getGare2().getName())) {
                        VoieToTake = 1;
//                        System.out.println(current.getVoie().get(1));
                    } else {
                        if (!gareDone.contains(current.getVoie().get(2).getGare2().getName())) {
                            VoieToTake = 2;
//                            System.out.println(current.getVoie().get(2));
                        } else {
                            VoieToTake = 3;
//                            System.out.println(current.getVoie().get(3));
                        }
                    }

                }

//                if (!current.getVoie().get(VoieToTake).getGare1().getName().equals(current.getName())) {
//                    current = current.getVoie().get(VoieToTake).getGare1();
//                    lieuxWithLinesAndOrder.add(new LieuWithLineAndOrder(current.getName(), current.getLigne().getName(), lieuxWithLinesAndOrder.size() + 1));
//                    gareDone.add(current.getName());
////                    System.out.println(current.getName());
//                } else {
                    current = current.getVoie().get(VoieToTake).getGare2();
                    lieuxWithLinesAndOrder.add(new LieuWithLineAndOrder(current.getName(), current.getLigne().getName(), lieuxWithLinesAndOrder.size() + 1));
                    gareDone.add(current.getName());
//                    System.out.println(current.getName());
//                }
            }
            listFinalOfLines.put(line.getName(),lieuxWithLinesAndOrder);
        }
        }
        }
        }
        return listFinalOfLines;
        }



    public static void setLinesMap(Map<String, Line> linesMap) {
        ReturnGareWithOrder.linesMap = linesMap;
    }

    public static HashMap<String,List<LieuWithLineAndOrder>> ReturnVue_lieu_with_line_and_order_() {
        Create_class.create_all_class();
        setLinesMap(linesMap);
        //Mettre en JSON
        return ReturnVue_lieu_with_line_and_order();
    }

    public static void main(String[] args) {
        Create_class.create_all_class();
        setLinesMap(linesMap);
        System.out.println(ReturnVue_lieu_with_line_and_order());
    }
}


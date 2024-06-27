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
    public static List<List<LieuWithLineAndOrder>> ReturnVue_lieu_with_line_and_order()  {
        List<List<LieuWithLineAndOrder>> listFinalOfLines = new ArrayList<>();
        for (Line line : linesMap.values()) {
            if (Arrays.asList("0", "A", "B", "C", "D", "E").contains(line.getName().strip())){
                continue;
            }
            List<LieuWithLineAndOrder> lieuxWithLinesAndOrder = new ArrayList<>();
            List<Gare> terminus = new ArrayList<>();
            for (Gare gare : line.getListeGare()) {
                if ((gare.getVoie()).size() == 2) {
                    terminus.add(gare);
                }
            }
//            System.out.println(terminus.size()+"à la ligne : "+line.getName());
            lieuxWithLinesAndOrder.add(new LieuWithLineAndOrder(terminus.get(0).getName(), terminus.get(0).getLigne().getName(), 1));
            Gare current=terminus.get(0);
            List<String> gareDone = new ArrayList<>();
            gareDone.add(current.getName());
            while (!terminus.contains(current) || current == terminus.get(0)) {
                int VoieToTake;
                if (!gareDone.contains(current.getVoie().get(0).getGare1().getName())) {
                    VoieToTake = 0;
//                    System.out.println(current.getVoie().get(0));
                } else {
                    if (!gareDone.contains(current.getVoie().get(1).getGare1().getName())) {
                        VoieToTake = 1;
//                        System.out.println(current.getVoie().get(1));
                    } else {
                        if (!gareDone.contains(current.getVoie().get(2).getGare1().getName())) {
                            VoieToTake = 2;
//                            System.out.println(current.getVoie().get(2));
                        } else {
                            VoieToTake = 3;
//                            System.out.println(current.getVoie().get(3));
                        }
                    }

                }
                if (!current.getVoie().get(VoieToTake).getGare1().getName().equals(current.getName())) {
                    current = current.getVoie().get(VoieToTake).getGare1();
                    lieuxWithLinesAndOrder.add(new LieuWithLineAndOrder(current.getName(), current.getLigne().getName(), lieuxWithLinesAndOrder.size() + 1));
                    gareDone.add(current.getName());
//                    System.out.println(current.getName());
                } else {
                    current = current.getVoie().get(VoieToTake).getGare2();
                    lieuxWithLinesAndOrder.add(new LieuWithLineAndOrder(current.getName(), current.getLigne().getName(), lieuxWithLinesAndOrder.size() + 1));
                    gareDone.add(current.getName());
//                    System.out.println(current.getName());
                }
            }
            listFinalOfLines.add(lieuxWithLinesAndOrder);
        }
        return listFinalOfLines;
        }



    public static void setLinesMap(Map<String, Line> linesMap) {
        ReturnGareWithOrder.linesMap = linesMap;
    }

    public static List<List<LieuWithLineAndOrder>> ReturnVue_lieu_with_line_and_order_() {
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


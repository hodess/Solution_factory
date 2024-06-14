import java.util.ArrayList;
import java.util.List;

public class Line {
    private String name;
    private List<Gare> listeGare;

    public Line(String name) {
        this.name = name;
        this.listeGare = new ArrayList<>();
    }

    public void addGare(Gare newGare) {
        if (!listeGare.contains(newGare)) {
            listeGare.add(newGare);
        }
    }

    public Gare findGare_with_id(int id) {
        for (Gare g : listeGare) {
            if (g.getId() == id) {
                return g;
            }
        }
        return null;
    }

    public Gare findGare_with_name(String name) {
        for (Gare g : listeGare) {
            if (g.getName().equals(name)) {
                return g;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder gares = new StringBuilder();
        for (Gare g : listeGare) {
            if (gares.length() > 0) gares.append(", ");
            gares.append(g.getName());
        }
        return String.format("Line(ligne=%s, liste_Gare=[%s])", name, gares.toString());
    }

    public String getName() {
        return name;
    }

    public List<Gare> getListeGare() {
        return listeGare;
    }
}

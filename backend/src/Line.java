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
}

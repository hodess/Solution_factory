import java.util.ArrayList;
import java.util.List;

public class Gare {
    private String name;
    private Line ligne;
    private int[] cord;
    private List<Voie> voie;

    public Gare(String name, Line ligne, int x, int y) {
        this.name = name;
        this.ligne = ligne;
        this.cord = new int[]{x, y};
        this.voie = new ArrayList<>();
        ligne.addGare(this);
    }

    public void addVoie(Voie newVoie) {
        voie.add(newVoie);
    }

    @Override
    public String toString() {
        return String.format("Gare(name=%s, ligne=%s, nb de voie=%d, coord=(%d, %d))",
                name, ligne.getName(), voie.size(), cord[0], cord[1]);
    }

    public List<Voie> getVoie() {
        return voie;
    }


    public Voie findVoie(Gare gare2) {
        for (Voie v : voie) {
            if (v.getOther(this) == gare2) {
                return v;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}

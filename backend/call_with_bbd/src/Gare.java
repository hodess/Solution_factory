import java.util.ArrayList;
import java.util.List;

public class Gare {
    private int id;
    private String name;
    private Line ligne;
    private List<Voie> voie;

    public Gare(int id,String name, Line ligne) {
        this.id=id;
        this.name = name;
        this.ligne = ligne;
        this.voie = new ArrayList<>();
        ligne.addGare(this);
    }

    public void addVoie(Voie newVoie) {
        voie.add(newVoie);
    }

    @Override
    public String toString() {
        return String.format(" Gare(id = %d, name=%s, ligne=%s, nb de voie=%d",
                id, name, ligne.getName(), voie.size());
    }

    public int getId() {
        return id;
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

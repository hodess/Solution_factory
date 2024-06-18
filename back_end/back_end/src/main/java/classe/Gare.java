package classe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Gare {
    private int id;
    private String name;
    private Line ligne;
    private List<Integer> coord;
    private List<Voie> voie;

    public Gare(int id,String name, Line ligne,int x,int y) {
        this.id=id;
        this.name = name;
        this.ligne = ligne;
        this.voie = new ArrayList<>();
        this.coord = new ArrayList<>();
        this.coord.add(x);
        this.coord.add(y);
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
    @JsonProperty("id")
    public int getId() {
        return id;
    }
    @JsonIgnore
    public List<Voie> getVoie() {
        return voie;
    }
    @JsonIgnore
    public Line getLigne() {
        return ligne;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }
    @JsonProperty("coord")
    public List<Integer> getCoord() {
        return coord;
    }

    public String getNomLigne(){
        return ligne.getName();
    }

    public Voie findVoie(Gare gare2) {
        for (Voie v : voie) {
            if (v.getOther(this) == gare2) {
                return v;
            }
        }
        return null;
    }


}

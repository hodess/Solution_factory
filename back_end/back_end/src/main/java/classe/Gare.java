package classe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Gare {
    private String id;
    private String name;
    private Line ligne;
    private List<Double> coord;
    private List<Voie> voie;

    public Gare(String id,String name, Line ligne,double x,double y) {
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
        return String.format(" Gare(id = %s, name=%s, ligne=%s, nb de voie=%d",
                id, name, ligne.getName(), voie.size());
    }
    @JsonProperty("id")
    public String getId() {
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
    public List<Double> getCoord() {
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

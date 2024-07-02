package classe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Gare {
    public static int nbGare = 0;
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
        nbGare++;
    }

    public void clear() {
        this.id = null;
        this.name = null;
        this.ligne = null;
        if (this.coord != null) {
            this.coord.clear();
        }
        if (this.voie != null) {
            this.voie.clear();
        }
        nbGare=0;
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

    public static int getNbGare() {
        return nbGare;
    }

    public double getDistance(Gare gare2) {
        double lat1 = this.coord.get(0);
        double lon1 = this.coord.get(1);
        double lat2 = gare2.getCoord().get(0);
        double lon2 = gare2.getCoord().get(1);

        // Radius of the Earth in km
        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }


    public Voie findVoie(Gare gare2) {
        for (Voie v : voie) {
            if (v.getGare2() == gare2) {
                return v;
            }
        }
        return null;
    }




}

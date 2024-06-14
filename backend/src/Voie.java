public class Voie {
    private static int nombreDElements = 0;

    private Gare gare1;
    private Gare gare2;
    private Line ligne;
    private int temps;
    private int bidirectionnel;

    public Voie(Gare gare1, Gare gare2, Line ligne, int temps, int bidirectionnel) {
        this.gare1 = gare1;
        this.gare2 = gare2;
        this.ligne = ligne;
        this.temps = temps;
        this.bidirectionnel = bidirectionnel;
        gare1.addVoie(this);
        gare2.addVoie(this);
        nombreDElements++;
    }
    public Gare getOther(Gare gare) {
        if (gare == gare1) {
            return gare2;
        } else if (gare == gare2) {
            return gare1;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("Voie(Gare1=%s, Gare2=%s, ligne=%s, temps=%d, Bidirectionnel=%d)",
                gare1.getName(), gare2.getName(), ligne.getName(), temps, bidirectionnel);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Voie other = (Voie) obj;
        if (gare1.equals(other.gare1) && gare2.equals(other.gare2) && ligne.equals(other.ligne) && temps == other.temps && bidirectionnel == other.bidirectionnel){
            return true;
        }
        if (gare1.equals(other.gare2) && gare2.equals(other.gare1) && ligne.equals(other.ligne) && temps == other.temps && bidirectionnel == other.bidirectionnel){
            return true;
        }

        return false;
    }

    public static int getNombreDElements() {
        return nombreDElements;
    }

    public Line getLigne() {
        return ligne;
    }

    public int getTemps() {
        return temps;
    }

}

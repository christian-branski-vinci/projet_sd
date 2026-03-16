public class Rue {

  double distance;
  String nom;
  Localisation origine;
  Localisation arrivee;

  public Rue(double distance, String nom,Localisation origine, Localisation arrivee) {
    this.distance = distance;
    this.nom = nom;
    this.origine = origine;
    this.arrivee = arrivee;
  }

  public double getDistance() {
    return distance;
  }

  public void setDistance(double distance) {
    this.distance = distance;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

    public Localisation getArrivee() {
        return arrivee;
    }

    public void setArrivee(Localisation arrivee) {
        this.arrivee = arrivee;
    }

    public Localisation getOrigine() {
        return origine;
    }

    public void setOrigine(Localisation origine) {
        this.origine = origine;
    }
}

public class Rue {

  double distance;
  String nom;

  Localisation destination;

  public Rue(Localisation destination, double distance, String nom) {
    this.destination = destination;
    this.distance = distance;
    this.nom = nom;
  }

  public Localisation getDestination() {
    return destination;
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

}

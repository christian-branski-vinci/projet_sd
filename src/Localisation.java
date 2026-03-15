public class Localisation {

  Long id;
  double latitude;
  double longitude;
  String nom;
  double altitude;

  public Localisation(Long id, double latitude, double longitude, String nom, double altitude) {
    this.id = id;
    this.latitude = latitude;
    this.longitude = longitude;
    this.nom = nom;
    this.altitude = altitude;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(Long latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(Long longitude) {
    this.longitude = longitude;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public double getAltitude() {
    return altitude;
  }

  public void setAltitude(double altitude) {
    this.altitude = altitude;
  }
}

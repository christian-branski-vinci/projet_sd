import java.io.File;
import java.util.*;

public class Graph {

	//ATTRIBUT ?
	//TODO

    Map<Long, Localisation> mapLocalisations;
    Map<Long, List<Rue>> mapRueAdjacentes;
    public Graph(String localisations, String roads) {
        mapRueAdjacentes = new HashMap<>();
        mapLocalisations = new HashMap<>();

        // Lire les noeuds
        try (Scanner sc = new Scanner(new File(localisations))) {
            sc.nextLine(); // sauter l'en-tête
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                Long id = Long.parseLong(parts[0]);
                String nom = parts[1];
                double lat = Double.parseDouble(parts[2]);
                double lon = Double.parseDouble(parts[3]);
                double alt = Double.parseDouble(parts[4]);
                Localisation loc = new Localisation(id, lat, lon, nom, alt);
                mapLocalisations.put(id, loc);
            }
        } catch (Exception e) { e.printStackTrace(); }

        // Lire les arcs
        try (Scanner sc = new Scanner(new File(roads))) {
            sc.nextLine(); // sauter l'en-tête
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                Long idOrigine = Long.parseLong(parts[0]);
                Long idArrivee = Long.parseLong(parts[1]);
                double dist = Double.parseDouble(parts[2]);
                String nom = parts[3];
                Localisation origine = mapLocalisations.get(idOrigine);
                Localisation arrivee = mapLocalisations.get(idArrivee);
                Rue rue = new Rue(dist,nom,origine,arrivee);
                mapRueAdjacentes.computeIfAbsent(idOrigine, k -> new ArrayList<>()).add(rue);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public Localisation[] determinerZoneInondee(long[] idsOrigin,double epsilon) {
        //TODO
        List<Localisation> visitees = new ArrayList<>();
        for (long l : idsOrigin) {
            Localisation depart = mapLocalisations.get(l);
            determinerZonneInondeeBis(depart,visitees,epsilon);
        }
        return visitees.toArray(new Localisation[0]);
    }

    private void determinerZonneInondeeBis(Localisation noeudCourant, List<Localisation> visitees, double epsilon){
        visitees.add(noeudCourant);
        for (Rue rue : mapRueAdjacentes.get(noeudCourant.getId())) {
            Localisation suivant = mapLocalisations.get(rue.getArrivee().getId());
            if(!visitees.contains(suivant) && suivant.getAltitude()<= noeudCourant.getAltitude() + epsilon){
                determinerZonneInondeeBis(suivant,visitees,epsilon);
            }
        }
    }

    public Deque<Localisation> trouverCheminLePlusCourtPourContournerLaZoneInondee(long idOrigin, long idDestination, Localisation[] floodedZone) {
		//TODO
        return null ;
    }

    public Map<Localisation,Double> determinerChronologieDeLaCrue(long[] idsOrigin, double vWaterInit, double k) {
        //TODO
        Map<Localisation, Double> chronologie = new HashMap<>();
        for (int i = 0; i < idsOrigin.length; i++) {
            determinerChronologieDeLaCrueBis(idsOrigin[i], vWaterInit, k, chronologie);
        }

        return chronologie;
    }

    public void determinerChronologieDeLaCrueBis(long idOrigin, double vWaterInit, double k, Map<Localisation, Double> map){

    }

    public Deque<Localisation> trouverCheminDEvacuationLePlusCourt(long idOrigin, long idEvacuation, double vVehicule, Map<Localisation,Double> tFlood) {
        //TODO
		return null ;
    }


}

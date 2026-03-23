import java.io.File;
import java.io.IOException;
import java.util.*;

public class Graph {

	//ATTRIBUT ?
	//TODO

    Map<Long, Localisation> mapLocalisations;
    Map<Long, List<Rue>> mapRueAdjacentes;
    public Graph(String localisations, String roads) {
        mapRueAdjacentes = new HashMap<>();
        mapLocalisations = new HashMap<>();
        lectureFichier(localisations,roads);
    }
    private void lectureFichier(String localisations, String roads) {
        // Lire les noeuds
        try (Scanner sc = new Scanner(new File(localisations))) {
            sc.nextLine();
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                Long id = Long.parseLong(parts[0]);
                String nom = parts[1];
                double lat = Double.parseDouble(parts[2]);
                double lon = Double.parseDouble(parts[3]);
                double alt = Double.parseDouble(parts[4]);
                Localisation loc = new Localisation(id, lat, lon, nom, alt);
                mapLocalisations.put(id, loc);
                mapRueAdjacentes.put(id, new ArrayList<>());
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier de localisations : " + localisations, e);
        }

        // Lire les arcs
        try (Scanner sc = new Scanner(new File(roads))) {
            sc.nextLine();
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                Long idOrigine = Long.parseLong(parts[0]);
                Long idArrivee = Long.parseLong(parts[1]);
                double dist = Double.parseDouble(parts[2]);
                String nom = parts[3];
                Localisation origine = mapLocalisations.get(idOrigine);
                Localisation arrivee = mapLocalisations.get(idArrivee);
                Rue rue = new Rue(dist, nom, origine, arrivee);
                mapRueAdjacentes.get(idOrigine).add(rue);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier des routes : " + roads, e);
        }
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
        Deque<Localisation> deque = new ArrayDeque<>();
        return deque ;
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
        Localisation localisationOrigin = mapLocalisations.get(idOrigin);
        if (map.containsKey(localisationOrigin)){
            return;
        }
        List<Rue> list = mapRueAdjacentes.get(idOrigin);
        double temps = Double.MAX_VALUE;
        Localisation localisationTempsDest= new Localisation(null,0,0,null,0);
        double vitesse= 0;
        for (Rue rue : list) {
            Localisation localisationDest = mapLocalisations.get(rue.arrivee.getId());
            double pente = (localisationOrigin.getAltitude() - localisationDest.getAltitude())/rue.getDistance();
            double vitesseEau= vWaterInit + (k * pente);
            double tempsRue = rue.getDistance()/vitesseEau;
            if (tempsRue<temps){
                temps= tempsRue;
                localisationTempsDest= localisationDest;
                vitesse= vitesseEau;
            }
        }
        if (localisationTempsDest.getId()==null){
            return;
        }
        map.put(localisationOrigin, temps);
        determinerChronologieDeLaCrueBis(localisationTempsDest.getId(), vitesse,k , map);

    }

    public Deque<Localisation> trouverCheminDEvacuationLePlusCourt(long idOrigin, long idEvacuation, double vVehicule, Map<Localisation,Double> tFlood) {
        //TODO
		return null ;
    }


}

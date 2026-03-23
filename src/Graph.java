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

        Set<Long> flooded = new HashSet<Long>();
        for (int i = 0; i < floodedZone.length; i++) {
            flooded.add(floodedZone[i].id);
        }

        Localisation o = mapLocalisations.get(idOrigin);
        Localisation d = mapLocalisations.get(idDestination);

        if (flooded.contains(o.id)){
            return null;
        }
        if(flooded.contains(d.id)){
            return null;
        }
        Set<Long> visited = new HashSet<Long>();
        Queue<Localisation> queue = new LinkedList<Localisation>();
        Map<Long , Localisation> pathHistory = new HashMap<>();

        queue.add(o);
        visited.add(idOrigin);
        pathHistory.put(idOrigin , null);


        while (!queue.isEmpty()) {
//            List<Rue> aAjouter = mapRueAdjacentes.get(path.pop().id);
            Localisation c = queue.poll();
            if(c.id == idDestination){
                break;
            }
            List<Rue> adjacentes = mapRueAdjacentes.get(c.id);
            for (Rue rue : adjacentes) {
                Localisation acoute = rue.arrivee;

//                if (!rue.arrivee.equals(d) || !flooded.contains(rue.arrivee)) {
//                    path.add(rue.arrivee);
//                }
                if(visited.contains(acoute.id)){
                    continue;
                }
                if(flooded.contains(acoute.id)){
                    continue;
                }
                visited.add(acoute.id);
                pathHistory.put(acoute.id , c);
                queue.add(acoute);
            }
        }
        if(!pathHistory.containsKey(idDestination)){
            return null;
        }
        Deque<Localisation> path = new LinkedList<>();
        Localisation etap = d;
        //barmigardim to map ke jahatesho baraks konim baraye har child parent esho bar migardonim
        while (etap != null){
            path.addFirst(etap);
            etap = pathHistory.get(etap.id);
        }
        return path;
    }

    public Map<Localisation, Double> determinerChronologieDeLaCrue(long[] idsOrigin, double vWaterInit, double k) {
        Map<Long, Double> distMap = new HashMap<>();
        Map<Long, Double> vitesseMap = new HashMap<>();
        Map<Long, Long> predecesseur = new HashMap<>();
        Map<Localisation, Double> chronologie = new HashMap<>();
        Set<Long> visites = new HashSet<>();

        TreeSet<long[]> pq = new TreeSet<>((a, b) -> {
            int cmp = Double.compare(a[1], b[1]);
            if (cmp != 0) return cmp;
            return Long.compare((long) a[0], b[0]);
        });

        //Init
        for (long id : idsOrigin) {
            distMap.put(id, 0.0);
            vitesseMap.put(id, vWaterInit);
            chronologie.put(mapLocalisations.get(id), 0.0);
            predecesseur.put(id, -1L);
            pq.add(new long[]{id, Double.doubleToLongBits(0.0)});
        }

        //Djikstra
        while (!pq.isEmpty()) {
            long[] entry = pq.first();
            pq.remove(entry);
            long idCourant = entry[0];
            double tCourant = Double.longBitsToDouble(entry[1]);

            //Vérification si un noeud finalisé n'est pas retraité
            if (!visites.add(idCourant)) continue;

            double vCourant = vitesseMap.get(idCourant);
            Localisation locCourant = mapLocalisations.get(idCourant);

            for (Rue rue : mapRueAdjacentes.get(idCourant)) {
                Localisation locArrivee = rue.getArrivee();
                long idArrivee = locArrivee.getId();

                if (visites.contains(idArrivee)) continue;

                double pente = (locCourant.getAltitude() - locArrivee.getAltitude()) / rue.getDistance();
                double vArrivee = vCourant + (k * pente);

                //Vérification si idArrivee est finalisé
                if (vArrivee <= 0) continue;

                double tArrivee = tCourant + rue.getDistance() / vArrivee;

                //Mise à jour voir si le chemin trouvé est meilleur
                if (tArrivee < distMap.getOrDefault(idArrivee, Double.MAX_VALUE)) {
                    // Retirer l'entrée avant maj
                    double tAncien = distMap.getOrDefault(idArrivee, Double.MAX_VALUE);
                    pq.removeIf(e -> e[0] == idArrivee && Double.longBitsToDouble(e[1]) == tAncien);

                    distMap.put(idArrivee, tArrivee);
                    vitesseMap.put(idArrivee, vArrivee);
                    chronologie.put(locArrivee, tArrivee);
                    predecesseur.put(idArrivee, idCourant);
                    pq.add(new long[]{idArrivee, Double.doubleToLongBits(tArrivee)});
                }
            }
        }

        Map<Localisation, Double> resultat = new LinkedHashMap<>();
        chronologie.entrySet().stream()
            .sorted(Map.Entry.comparingByValue())
            .forEach(e -> resultat.put(e.getKey(), e.getValue()));

        return resultat;
    }


    public Deque<Localisation> trouverCheminDEvacuationLePlusCourt(long idOrigin, long idEvacuation, double vVehicule, Map<Localisation,Double> tFlood) {
        //TODO
		return null ;
    }


}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

	//ATTRIBUT ?
	//TODO

    Map<Long, List<Rue>> adjacence = new HashMap<>();
    Map<Long, Localisation> noeuds= new HashMap<>();

    public Graph(String localisations, String roads) {
        noeuds = new HashMap<>();
        adjacence = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(localisations))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                long id = Long.parseLong(parts[0]);
                String nom = parts[1];
                double lat = Double.parseDouble(parts[2]);
                double lon = Double.parseDouble(parts[3]);
                double alt = Double.parseDouble(parts[4]);
                noeuds.put(id, new Localisation(id, lat, lon, nom, alt));
                adjacence.put(id, new ArrayList<>());
            }
        } catch (IOException e) { e.printStackTrace(); }

        try (BufferedReader br = new BufferedReader(new FileReader(roads))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                long source = Long.parseLong(parts[0]);
                long target = Long.parseLong(parts[1]);
                double dist = Double.parseDouble(parts[2]);
                String nom = parts[3];
                Localisation destination = noeuds.get(target);
                adjacence.get(source).add(new Rue(destination, dist, nom));
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public Localisation[] determinerZoneInondee(long[] idsOrigin,double epsilon) {
        //TODO

		return null ;
    }

    public Deque<Localisation> trouverCheminLePlusCourtPourContournerLaZoneInondee(long idOrigin, long idDestination, Localisation[] floodedZone) {
		//TODO
        return null ;
    }

    public Map<Localisation,Double> determinerChronologieDeLaCrue(long[] idsOrigin, double vWaterInit, double k) {
        //TODO
        return null ;
    }

    public Deque<Localisation> trouverCheminDEvacuationLePlusCourt(long idOrigin, long idEvacuation, double vVehicule, Map<Localisation,Double> tFlood) {
        //TODO
		return null ;
    }


}

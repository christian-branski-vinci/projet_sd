import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Graph {

	//ATTRIBUT ?
	//TODO

    Map<Localisation, Rue> mapRueAdjacentes;
    Map<Rue, Localisation> mapOrigineArrivee;

    public Graph(String localisations, String roads)  {
        //TODO
<<<<<<< HEAD

=======
        mapRueAdjacentes = new HashMap<>();
        mapOrigineArrivee = new HashMap<>();
>>>>>>> 65a0e28d02c2e3c377b264e6f317cef9866493f5
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

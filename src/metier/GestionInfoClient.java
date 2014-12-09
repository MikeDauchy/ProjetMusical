package metier;

import donnees.Client;
import donnees.reservations.Reservation;

public class GestionInfoClient {


	public void addPtFidelité (Client client, Reservation reservation){
		int nbPtFideliteClient = client.getPointFidelite();
		int nbHeure= reservation.getNbHeure();
		int nbPtFidelite = 5 * nbHeure;
		client.setPointFidelite( nbPtFideliteClient + nbPtFidelite);
	}

}

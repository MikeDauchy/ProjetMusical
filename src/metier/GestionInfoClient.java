package metier;

import donnees.Client;
import donnees.reservations.Reservation;

public class GestionInfoClient {


	public void addPtFidelité (Client client, Reservation reservation){
		int nbPtFideliteClient = client.getPointFidelite();
		int nbHeure= (((int)reservation.getDateFin().getTime() -(int) reservation.getDateDebut().getTime())/(60*60*60));
		int nbPtFidelite = 5 * nbHeure;
		client.setPointFidelite( nbPtFideliteClient + nbPtFidelite);
	}


}

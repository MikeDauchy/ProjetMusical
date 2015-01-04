package metier;

import donnees.Client;
import donnees.reservations.Reservation;

public class GestionInfoClient {


	public void addPtFidelite (Client client, Reservation reservation){
		int nbPtFideliteClient = client.getPointFidelite();
		int nbHeure= reservation.getNbHeure();
		int nbPtFidelite = 5 * nbHeure;
		client.setPointFidelite( nbPtFideliteClient + nbPtFidelite);
	}
	
	public void deletePtFidelite(Client client, int pointFidelite){
		int nbPtFideliteClient = client.getPointFidelite();
		client.setPointFidelite(nbPtFideliteClient - pointFidelite);
	}
	
	public boolean deuxHeuresGratuite(Client client){
		return client.getPointFidelite()>=150;
	}

}

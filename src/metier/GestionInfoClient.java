package metier;

import java.sql.SQLException;
import java.util.List;

import donnees.Client;
import donnees.Facture;
import donnees.Forfait;
import donnees.reservations.Reservation;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.donnes.ClientFactory;
import fabriques.donnes.FactureFactory;
import fabriques.donnes.ForfaitFactory;
import fabriques.donnes.ReservationFactory;

public class GestionInfoClient {

	public void addPtFidelite(Client client, Reservation reservation){
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
	
	public void supprimerClient(Client client) throws SQLException, ObjetInconnu{
		List<Reservation> listReservation = client.getListReservations();
		List<Facture> listFacture = client.getListFactures();
		List<Forfait> listForfaits = client.getListFofaits();
		
		for(Reservation reservation : listReservation){
			ReservationFactory.getInstance().supprimer(reservation);
		}
		
		for(Facture facture : listFacture){
			FactureFactory.getInstance().supprimer(facture);
		}
		
		for(Forfait forfait : listForfaits){
			ForfaitFactory.getInstance().supprimer(forfait);
		}
		
		ClientFactory.getInstance().supprimer(client);
		client = null;
	}
	
}

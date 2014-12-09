package metier;

import java.sql.SQLException;

import donnees.Client;
import donnees.Forfait;
import donnees.reservations.Reservation;
import exceptions.accesAuDonnees.ObjetInconnu;

public class GestionForfait {
	
	public void deductHeure(Reservation reservation , Forfait forfait){
		int nbHeureReservation = reservation.getNbHeure();
		int nbHeureForfait = forfait.getNbHeure() - nbHeureReservation;
		forfait.setNbHeure(nbHeureForfait);
	}


}

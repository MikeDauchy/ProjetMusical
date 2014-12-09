package metier;

import java.sql.SQLException;

import donnees.Facture;
import donnees.reservations.Reservation;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.donnes.FactureFactory;
import fabriques.donnes.ReservationFactory;

public class GestionReservation {

	public void supprimerReservationSalle(Reservation reservation) throws SQLException, ObjetInconnu{
		ReservationFactory.getInstance().supprimer(reservation);
		Facture facture = FactureFactory.getInstance().rechercherByIdFacture(reservation.getIdFacture());
		if(facture.getListReservations().isEmpty()){
			FactureFactory.getInstance().supprimer(facture);
		}
	}

}

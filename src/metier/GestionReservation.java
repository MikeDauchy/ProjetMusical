package metier;

import java.sql.SQLException;

import donnees.Facture;
import donnees.Forfait;
import donnees.reservations.Reservation;
import exceptions.accesAuDonnees.ObjetInconnu;
import exceptions.metier.ForfaitNbHeuresInsuffissantException;
import fabriques.donnes.FactureFactory;
import fabriques.donnes.ReservationFactory;

public class GestionReservation {
	GestionForfait gestForfait;

	public void supprimerReservationSalle(Reservation reservation) throws SQLException, ObjetInconnu{
		ReservationFactory.getInstance().supprimer(reservation);
		Facture facture = FactureFactory.getInstance().rechercherByIdFacture(reservation.getIdFacture());
		if(facture.getListReservations().isEmpty()){
			FactureFactory.getInstance().supprimer(facture);
		}
	}
	
	public void validReservation(Reservation reservation,Forfait forfait) throws ObjetInconnu, SQLException{
		if(!reservation.getFacture().isEstPaye()){
			if(forfait.getNbHeure() > reservation.getNbHeure() || forfait.getNbHeure() !=0){
				gestForfait.deductHeure(reservation ,forfait);
				reservation.getFacture().setEstPaye(true);
			}
			else{
				throw new ForfaitNbHeuresInsuffissantException("Nombre d'heure sur le forfait insuffisant");
			}
		}
	}

}

package metier;

import java.sql.SQLException;

import donnees.Facture;
import donnees.Forfait;
import donnees.reservations.Reservation;
import exceptions.accesAuDonnees.ObjetInconnu;
import exceptions.metier.ForfaitNbHeuresInsuffissantException;
import exceptions.metier.PointsFideliteInsuffisantException;
import fabriques.donnes.FactureFactory;
import fabriques.donnes.ReservationFactory;

public class GestionReservation {
	GestionForfait gestForfait;
	GestionInfoClient gestInfoClient;

	public void supprimerReservationSalle(Reservation reservation) throws SQLException, ObjetInconnu{
		ReservationFactory.getInstance().supprimer(reservation);
		Facture facture = FactureFactory.getInstance().rechercherByIdFacture(reservation.getIdFacture());
		if(facture.getListReservations().isEmpty()){
			FactureFactory.getInstance().supprimer(facture);
		}
	}

	public void paiementReservationCB(Reservation reservation) throws ObjetInconnu, SQLException{
		if(!reservation.getFacture().isEstPaye()){
			reservation.getFacture().setEstPaye(true);
		}
	}

	public void paiementReservationForfait(Reservation reservation,Forfait forfait) throws ObjetInconnu, SQLException{
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

	public void paiementReservationPtsFidelite(Reservation reservation) throws ObjetInconnu, SQLException, PointsFideliteInsuffisantException{
		if(!reservation.getFacture().isEstPaye()){
			if(gestInfoClient.deuxHeuresGratuite(reservation.getFacture().getClient()) && reservation.getNbHeure()<=2 ){
				reservation.getFacture().setEstPaye(true);
				gestInfoClient.deletePtFidelite(reservation.getFacture().getClient(), 150);
			}
			else{
				throw new PointsFideliteInsuffisantException("Vous ne pouvez pas utiliser ce type de paiement");
			}

		}
	}
}
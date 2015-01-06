package metier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JOptionPane;

import donnees.Client;
import donnees.Facture;
import donnees.Forfait;
import donnees.reservations.Reservation;
import donnees.salles.EnregistrementSalle;
import donnees.salles.MoyenneSalle;
import donnees.salles.PetiteSalle;
import donnees.salles.Salle;
import exceptions.accesAuDonnees.CreationObjetException;
import exceptions.accesAuDonnees.ObjetExistant;
import exceptions.accesAuDonnees.ObjetInconnu;
import exceptions.metier.ForfaitNbHeuresInsuffissantException;
import exceptions.metier.PointsFideliteInsuffisantException;
import fabriques.donnes.ClientFactory;
import fabriques.donnes.FactureFactory;
import fabriques.donnes.ForfaitFactory;
import fabriques.donnes.ReservationFactory;
import fabriques.donnes.SalleFactory;

public class GestionReservation {
	GestionForfait gestForfait = new GestionForfait();
	GestionInfoClient gestInfoClient = new GestionInfoClient();

	public void supprimerReservationSalle(Reservation reservation) throws SQLException, ObjetInconnu{
		ReservationFactory.getInstance().supprimer(reservation);
		Facture facture = FactureFactory.getInstance().rechercherByIdFacture(reservation.getIdFacture());
		if(facture.getListReservations().isEmpty()){
			FactureFactory.getInstance().supprimer(facture);
		}
	}

	public void paiementReservationCB(Reservation reservation) throws ObjetInconnu, SQLException{
		Facture facture =reservation.getFacture();
		Client client = reservation.getFacture().getClient();
		List<Reservation> lesReservation = facture.getListReservations();
		if(!facture.isEstPaye()){
			facture.setEstPaye(true);
			FactureFactory.getInstance().update(facture);
			for(Reservation r : lesReservation){
				gestInfoClient.addPtFidelite(client, r);
			}
			ClientFactory.getInstance().update(client);
		}
	}

	public void paiementReservationForfait(Reservation reservation,Forfait forfait) throws ObjetInconnu, SQLException{
		Facture facture =reservation.getFacture();
		Client client = reservation.getFacture().getClient();
		int nbHeure =0;
		if(!facture.isEstPaye()){
			List<Reservation> lesReservation = facture.getListReservations();
			for(Reservation r : lesReservation){
				nbHeure += r.getNbHeure();
			}
			if(forfait.getNbHeure() > nbHeure || forfait.getNbHeure() !=0){
				gestForfait.deductHeure(reservation ,forfait);
				facture.setEstPaye(true);
				for(Reservation r : lesReservation){
					gestInfoClient.addPtFidelite(client, r);
				}
				FactureFactory.getInstance().update(facture);
				ForfaitFactory.getInstance().update(forfait);
				ClientFactory.getInstance().update(client);
			}
			else{
				throw new ForfaitNbHeuresInsuffissantException("Nombre d'heure sur le forfait insuffisant");
			}
		}
	}

	public void paiementReservationPtsFidelite(Reservation reservation) throws ObjetInconnu, SQLException, PointsFideliteInsuffisantException{
		Facture facture =reservation.getFacture();
		if(!facture.isEstPaye()){
			if(gestInfoClient.deuxHeuresGratuite(reservation.getFacture().getClient()) && reservation.getNbHeure()<=2 ){
				facture.setEstPaye(true);
				gestInfoClient.deletePtFidelite(facture.getClient(), 150);
				FactureFactory.getInstance().update(facture);
			}
			else{
				throw new PointsFideliteInsuffisantException("Vous ne pouvez pas utiliser ce type de paiement");
			}

		}
	}
	
	
	public void reserver(Date dateDebut, Date dateFin, int nbHeure, Salle salle, Client client, boolean isRepeted, int nbRepeter) throws CreationObjetException, ObjetExistant, SQLException{
				
		Facture facture = FactureFactory.getInstance().creer(client.getIdClient(), false);
		
		
		if(isRepeted){
			//recherche si la reservation est compatible
			Date dateDebTemp = (Date) dateDebut.clone();
			Date dateFinTemp = (Date) dateFin.clone();
			for(int i = 0; i != nbRepeter; i++){
				try {
					ReservationFactory.getInstance().rechercherByIdSalleAndDates(salle.getIdSalle(), dateDebTemp, dateFinTemp);
				} catch (ObjetInconnu e) { //Resultat attendu, on a pas de reservation pour cette date}
				}
				dateDebTemp = ajouterUnJourDate(dateDebut);
				dateFinTemp = ajouterUnJourDate(dateFin);
			}
				
			
			for(int i = 0; i != nbRepeter; i++){
				ReservationFactory.getInstance().creer(facture.getIdFacture(), salle.getIdSalle(), nbHeure, dateDebut, dateFin);
				dateDebut = ajouterUnJourDate(dateDebut);
				dateFin = ajouterUnJourDate(dateFin);
			}
		}else{
			ReservationFactory.getInstance().creer(facture.getIdFacture(), salle.getIdSalle(), nbHeure, dateDebut, dateFin);
		}
		
	}
		
	private Date ajouterUnJourDate(Date date){
			GregorianCalendar calendar = new java.util.GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 7);
			date = calendar.getTime();
			return date;
	}
}
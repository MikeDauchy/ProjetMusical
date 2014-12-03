package tests.fabriques;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import donnees.reservations.Reservation;
import donnees.reservations.ReservationUneHeure;
import exceptions.accesAuDonnees.CreationObjetException;
import exceptions.accesAuDonnees.ObjetExistant;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.donnes.ReservationFactory;

public class ReservationFactoryTest {
	Reservation mokeReservation;
	List<Reservation> listMokeReservation = new ArrayList<Reservation>();
	Date dateDebut;
	Date dateFin;
	
	@Before
	public void init() {
		
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(2011,1,1,1,0);
		dateDebut = cal.getTime();
		
		cal.set(2011,1,1,2,0);
		dateFin = cal.getTime();
		
		mokeReservation = new ReservationUneHeure();
		mokeReservation.setIdFacture(-2);
		mokeReservation.setIdSalle(-10);
		mokeReservation.setNbHeure(1);
		mokeReservation.setDateDebut(dateDebut);
		mokeReservation.setDateFin(dateFin);
		
		listMokeReservation.add(mokeReservation);
	}

	/**
	 * simpleAdd
	 * @throws SQLException 
	 * @throws ObjetExistant 
	 * @throws CreationObjetException 
	 * @throws ObjetInconnu 
	 */
	@Test
	public void testGlobal() throws CreationObjetException, ObjetExistant, SQLException, ObjetInconnu {
		
		Reservation reservation = ReservationFactory.getInstance().creer(-2, -10, 1,dateDebut, dateFin);
		assertEquals(mokeReservation, reservation);
		
		assertEquals(ReservationFactory.getInstance().listerByIdFacture(reservation.getIdFacture()), listMokeReservation);
		
		assertEquals(ReservationFactory.getInstance().listerByDates(dateDebut, dateFin), listMokeReservation);
		
		assertEquals(ReservationFactory.getInstance().rechercherByIdSalleAndDates(reservation.getIdSalle(), dateDebut, dateFin), listMokeReservation);
		
		ReservationFactory.getInstance().supprimer(reservation);
		
		try{
			ReservationFactory.getInstance().rechercherByIdReservation(reservation.getIdReservation());
		}catch(ObjetInconnu e){
			//resultat attendu
			assert(true);
		}
	}
}

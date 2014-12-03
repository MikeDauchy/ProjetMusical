package donnees.reservations;

import java.sql.SQLException;

import exceptions.accesAuDonnees.ObjetInconnu;

public class ReservationUneHeure extends Reservation {

	@Override
	public double getPrix() throws ObjetInconnu, SQLException {
		return getSalle().getPrix();
	}

}

package donnees.reservations;

import java.sql.SQLException;

import exceptions.accesAuDonnees.ObjetInconnu;

public class ReservationDeuxHeures extends Reservation {

	@Override
	public double getPrix() throws ObjetInconnu, SQLException {
		double prixSalle = getSalle().getPrix();
		if (prixSalle == 7.0)
			return 10.0;
		if (prixSalle == 10.0)
			return 16.0;
		return 30.0;
	}

}

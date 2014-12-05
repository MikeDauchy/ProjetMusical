package affichage.reservations.panel.sallePanel;

import java.awt.Color;
import java.sql.SQLException;

import donnees.reservations.Reservation;
import exceptions.accesAuDonnees.ObjetInconnu;


public class EnregistrementSallePanel extends SallePanel{

	private static final long serialVersionUID = 1L;

	public EnregistrementSallePanel(Reservation reservation, int heure) throws ObjetInconnu, SQLException {
		super(reservation, heure);
		this.setBackground(new Color(26, 97, 189));
	}
}

package affichage.reservations.panel.sallePanel;

import java.awt.Color;
import java.sql.SQLException;
import java.util.Date;

import donnees.reservations.Reservation;
import donnees.salles.Salle;
import exceptions.accesAuDonnees.ObjetInconnu;


public class MoyenneSallePanel extends SallePanel{

	private static final long serialVersionUID = 1L;
	
	public MoyenneSallePanel(Reservation reservation, Date jourHeure) throws ObjetInconnu, SQLException {
		super(reservation, jourHeure, Salle.type.MOYENNE);
		this.setBackground(new Color(26, 135, 189));
	}

}

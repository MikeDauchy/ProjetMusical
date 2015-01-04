package affichage.consulter_reservations.panel.sallePanel;

import java.awt.Color;
import java.awt.Dialog;
import java.sql.SQLException;
import java.util.Date;

import donnees.reservations.Reservation;
import donnees.salles.Salle;
import exceptions.accesAuDonnees.ObjetInconnu;


public class MoyenneSallePanel extends SallePanel{

	private static final long serialVersionUID = 1L;
	
	public MoyenneSallePanel(Reservation reservation, Date jourHeure, Dialog dialog) throws ObjetInconnu, SQLException {
		super(reservation, jourHeure, Salle.type.MOYENNE, dialog);
		this.setBackground(new Color(26, 135, 189));
	}

}

package affichage.reservations.panel.sallePanel;

import java.awt.Color;
import java.sql.SQLException;
import java.util.Date;

import donnees.reservations.Reservation;
import exceptions.accesAuDonnees.ObjetInconnu;


public class PetiteSallePanel extends SallePanel{

	private static final long serialVersionUID = 1L;

	public PetiteSallePanel(Reservation reservation, Date jourHeure) throws ObjetInconnu, SQLException{
		super(reservation, jourHeure);
		this.setBackground(new Color(26, 162, 189));
	}
}

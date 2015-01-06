package affichage.consulter_reservations.panel.ReservationPanel;

import java.awt.Color;
import java.sql.SQLException;

import javax.swing.JPanel;

import donnees.reservations.Reservation;
import exceptions.accesAuDonnees.ObjetInconnu;

public class PanelReservation extends JPanel{

	public PanelReservation(Reservation reservation) throws ObjetInconnu, SQLException{
		switch(reservation.getSalle().getTypeSalle()){
			case PETITE:
				this.setBackground(new Color(26, 162, 189));
				break;
			case MOYENNE:
				this.setBackground(new Color(26, 135, 189));
				break;
			case ENREGISTREMENT:
				this.setBackground(new Color(26, 97, 189));
				break;
		}
	}
}

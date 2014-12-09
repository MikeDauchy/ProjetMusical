package affichage.reservations.panel.sallePanel;

import java.awt.Dialog;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JPanel;

import affichage.reservations.panel.ReservationPanel.NouvelleReservationPanel;
import affichage.reservations.panel.ReservationPanel.ReservationConfirmePanel;
import affichage.reservations.panel.ReservationPanel.ReservationNonConfirmePanel;
import donnees.reservations.Reservation;
import donnees.salles.Salle;
import exceptions.accesAuDonnees.ObjetInconnu;

public class SallePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	Reservation reservation;
	Date heure;
	boolean estPaye;
	Salle.type typeSalle;
	Dialog dialog;

	public SallePanel(Reservation reservation, Date jourHeure, Salle.type typeSalle, Dialog dialog) throws ObjetInconnu,
			SQLException {
		super();
		this.reservation = reservation;
		this.heure = jourHeure;
		this.addMouseListener(new Listner(this));
		this.typeSalle = typeSalle;
		this.dialog = dialog;

		if (reservation != null) {
			if (reservation.getFacture().isEstPaye()) {
				this.estPaye = true;
				this.add(new ReservationConfirmePanel(reservation));
			} else {
				this.add(new ReservationNonConfirmePanel(reservation));
			}
		}
	}

	class Listner implements MouseListener {

		SallePanel p;

		public Listner(SallePanel p) {
			super();
			this.p = p;

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (!p.estPaye) {
				this.p.removeAll();
				this.p.add(new NouvelleReservationPanel(reservation, heure, typeSalle));
				p.revalidate();
				p.dialog.pack();
				p.dialog.setLocationRelativeTo(null);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

}

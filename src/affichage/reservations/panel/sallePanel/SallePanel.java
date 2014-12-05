package affichage.reservations.panel.sallePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JPanel;

import affichage.reservations.panel.ReservationPanel.NouvelleReservationPanel;
import affichage.reservations.panel.ReservationPanel.ReservationConfirmePanel;
import affichage.reservations.panel.ReservationPanel.ReservationNonConfirmePanel;
import donnees.reservations.Reservation;
import exceptions.accesAuDonnees.ObjetInconnu;

public class SallePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	Reservation reservation;
	int heure;
	boolean estPaye;

	public SallePanel(Reservation reservation, int heure) throws ObjetInconnu,
			SQLException {
		super();
		this.reservation = reservation;
		this.heure = heure;
		this.addMouseListener(new Listner(this));

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
				this.p.add(new NouvelleReservationPanel(reservation, heure));
				p.validate();
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

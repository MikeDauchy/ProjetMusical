package affichage.consulter_reservations.panel.sallePanel;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JPanel;

import affichage.consulter_reservations.panel.ReservationPanel.NouvelleReservationPanel;
import affichage.consulter_reservations.panel.ReservationPanel.ReservationConfirmePanel;
import affichage.consulter_reservations.panel.ReservationPanel.ReservationNonConfirmePanel;
import donnees.reservations.Reservation;
import donnees.salles.Salle;
import exceptions.accesAuDonnees.ObjetInconnu;

public class SallePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Reservation reservation;
	private Date heure;
	private boolean estPaye;
	private Salle salle;
	private Dialog dialog;
	protected Color color;

	public SallePanel(Reservation reservation, Date jourHeure, Salle.type typeSalle, Dialog dialog, Salle salle) throws ObjetInconnu,
			SQLException {
		super();
		this.reservation = reservation;
		this.heure = jourHeure;
		this.addMouseListener(new Listner(this));
		this.salle = salle;
		this.dialog = dialog;
		this.setLayout(new GridLayout(1, 1));

		if (reservation != null) {
			if (reservation.getFacture().isEstPaye()) {
				this.estPaye = true;
				JPanel reservationConfPanel = new ReservationConfirmePanel(reservation, color);
				reservationConfPanel.setBackground(color);
				this.add(reservationConfPanel);
			} else {
				JPanel reservationNonConfPanel = new ReservationNonConfirmePanel(reservation, color);
				reservationNonConfPanel.setBackground(color);
				this.add(reservationNonConfPanel);
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
				try {
					JPanel reservationNouvelleConfPanel = new NouvelleReservationPanel(reservation, heure, salle, color);
					reservationNouvelleConfPanel.setBackground(p.color);
					this.p.add(reservationNouvelleConfPanel);
				} catch (ObjetInconnu | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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

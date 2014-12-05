package affichage.reservations.panel.ReservationPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import donnees.reservations.Reservation;

public class NouvelleReservationPanel extends JPanel{

	private JButton salleButton;
	private JButton supprimerSalleButton;
	
	public NouvelleReservationPanel(Reservation reservation, int heure) {
		
		this.setLayout(new  BorderLayout());
		
		salleButton = new JButton("entrer infos");
		supprimerSalleButton = new JButton("suppr");
		supprimerSalleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salleButton.setVisible(false);
				supprimerSalleButton.setVisible(false);
			}
		});
		this.add(salleButton, BorderLayout.CENTER);
		this.add(supprimerSalleButton, BorderLayout.NORTH);
	}
}

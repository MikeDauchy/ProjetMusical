package affichage.reservations.panel.ReservationPanel;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import donnees.Client;
import donnees.Facture;
import donnees.reservations.Reservation;
import exceptions.accesAuDonnees.ObjetInconnu;

public class ReservationConfirmePanel extends JPanel {

	private JButton salleButton;
	public ReservationConfirmePanel(final Reservation reservation) {
		
		this.setLayout(new  BorderLayout());
		
		salleButton = new JButton("voir");
		salleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Facture facture = reservation.getFacture();
					Client client = facture.getClient(); 
					JOptionPane.showMessageDialog(null, "Reservation payee appartenant au client "+client.getPrenom() +" "+client.getNom()+" pour un montant de "+facture.getMontant()+" euros");
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ObjetInconnu e1) {
					JOptionPane.showMessageDialog(null, "probleme d'acces a la donnee concernant le client");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		this.add(salleButton, BorderLayout.CENTER);
	}

}

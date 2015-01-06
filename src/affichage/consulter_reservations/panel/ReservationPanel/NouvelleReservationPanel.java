package affichage.consulter_reservations.panel.ReservationPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import donnees.Client;
import donnees.Facture;
import donnees.reservations.Reservation;
import donnees.salles.Salle;
import exceptions.accesAuDonnees.CreationObjetException;
import exceptions.accesAuDonnees.ObjetExistant;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.donnes.ClientFactory;
import fabriques.donnes.FactureFactory;
import fabriques.donnes.ReservationFactory;
import fabriques.donnes.SalleFactory;

public class NouvelleReservationPanel extends PanelReservation{

	private JButton salleButton;
	private JButton supprimerSalleButton;
	private Date dateDebut; 
	private Salle salle;
	private Reservation reservation;
	
	public NouvelleReservationPanel(Reservation reservation, Date dateDebut, Salle salle) throws ObjetInconnu, SQLException {
		super(reservation);
		this.setLayout(new  BorderLayout());
		
		this.dateDebut = dateDebut;
		this.salle = salle;
		this.reservation = reservation;
		
		supprimerSalleButton = new JButton("suppr");
		supprimerSalleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salleButton.setVisible(false);
				supprimerSalleButton.setVisible(false);
			}
		});
		salleButton = new JButton("entrer infos");
		salleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					affichageDialogue();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		this.add(salleButton, BorderLayout.CENTER);
		this.add(supprimerSalleButton, BorderLayout.NORTH);
	}
	
	private void affichageDialogue() throws SQLException{
		List<Client> listClient = ClientFactory.getInstance().lister();
		List<String> nomClient = new ArrayList<String>();
		for(Client client : listClient){
			nomClient.add(client.getNom());
		}
		String nomClientSelect = (String) JOptionPane.showInputDialog(this, 
		        "Quel est le client pour cette reservation ?",
		        "Choisissez le client",
		        JOptionPane.QUESTION_MESSAGE, 
		        null, 
		        nomClient.toArray(), 
		        nomClient.get(0));
		
		if(nomClientSelect != null){
			try {
				//TODO:tester qu'on recoit bien qu'un client
				Client client = ClientFactory.getInstance().rechercherByNom(nomClientSelect).get(0);
				Date dateFin = (Date)dateDebut.clone();
				dateFin.setHours(dateDebut.getHours()+1);
				Facture facture = FactureFactory.getInstance().creer(client.getIdClient(), false);
				this.reservation = ReservationFactory.getInstance().creer(facture.getIdFacture(), salle.getIdSalle(), 1, dateDebut, dateFin);
				
			} catch (CreationObjetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ObjetExistant e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ObjetInconnu e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.removeAll();
			try {
				this.add(new ReservationNonConfirmePanel(reservation));
			} catch (ObjetInconnu e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.revalidate();
		}
	}
}

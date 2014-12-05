package affichage.reservations.panel;

import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JPanel;

import donnees.reservations.Reservation;
import donnees.salles.EnregistrementSalle;
import donnees.salles.MoyenneSalle;
import donnees.salles.PetiteSalle;
import exceptions.accesAuDonnees.ObjetInconnu;
import affichage.reservations.panel.sallePanel.EnregistrementSallePanel;
import affichage.reservations.panel.sallePanel.MoyenneSallePanel;
import affichage.reservations.panel.sallePanel.PetiteSallePanel;

public class HeurePanel extends JPanel{

	JPanel petiteSallePanel;
	JPanel moyenneSallePanel;
	JPanel enregistrementSallePanel;
	
	public HeurePanel(List<Reservation> reservations, int heure) throws ObjetInconnu, SQLException {
		super();

	    this.setLayout(new GridLayout(1, 3));
	    
	    petiteSallePanel = new PetiteSallePanel(null, heure);
	    moyenneSallePanel = new MoyenneSallePanel(null, heure);
	    enregistrementSallePanel = new EnregistrementSallePanel(null, heure);
	    
	    for(Reservation reservation : reservations){
	    	if(reservation.getSalle().getClass().equals(PetiteSalle.class)){
	    		petiteSallePanel = new PetiteSallePanel(reservation, heure);
	    	}
	    	if(reservation.getSalle().getClass().equals(MoyenneSalle.class)){
	    		moyenneSallePanel = new MoyenneSallePanel(reservation, heure);
	    	}
	    	if(reservation.getSalle().getClass().equals(EnregistrementSalle.class)){
	    		enregistrementSallePanel = new EnregistrementSallePanel(reservation, heure);
	    	}
	    }
	    
	    add(petiteSallePanel);
	    add(moyenneSallePanel);
	    add(enregistrementSallePanel);
	}

}

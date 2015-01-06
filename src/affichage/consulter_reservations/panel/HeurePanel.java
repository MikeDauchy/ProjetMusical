package affichage.consulter_reservations.panel;

import java.awt.Dialog;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JPanel;

import affichage.consulter_reservations.panel.sallePanel.EnregistrementSallePanel;
import affichage.consulter_reservations.panel.sallePanel.MoyenneSallePanel;
import affichage.consulter_reservations.panel.sallePanel.PetiteSallePanel;
import donnees.reservations.Reservation;
import donnees.salles.Salle;
import exceptions.accesAuDonnees.ObjetInconnu;

public class HeurePanel extends JPanel{

	JPanel sallePanel;
	
	public HeurePanel(Reservation reservationsHeure, Date jourHeure, Dialog dialog, Salle salle) throws ObjetInconnu, SQLException {
		super();

	    this.setLayout(new GridLayout(1, 3));
	    
	    
	    switch(salle.getTypeSalle()){
	    	case PETITE:
	    		sallePanel = new PetiteSallePanel(reservationsHeure, jourHeure, dialog, salle);break;
	    	case MOYENNE:
	    		sallePanel = new MoyenneSallePanel(reservationsHeure, jourHeure, dialog, salle);break;
	    	case ENREGISTREMENT:
	    		sallePanel = new EnregistrementSallePanel(reservationsHeure, jourHeure, dialog, salle);break;
	    
	    }
	    add(sallePanel);
	}

}

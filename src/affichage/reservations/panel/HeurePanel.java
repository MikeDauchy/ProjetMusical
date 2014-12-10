package affichage.reservations.panel;

import java.awt.Dialog;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JPanel;

import affichage.reservations.panel.sallePanel.EnregistrementSallePanel;
import affichage.reservations.panel.sallePanel.MoyenneSallePanel;
import affichage.reservations.panel.sallePanel.PetiteSallePanel;
import donnees.reservations.Reservation;
import donnees.salles.Salle;
import exceptions.accesAuDonnees.ObjetInconnu;

public class HeurePanel extends JPanel{

	JPanel sallePanel;
	
	public HeurePanel(Reservation reservationsHeure, Date jourHeure, Dialog dialog, Salle.type typeSalle) throws ObjetInconnu, SQLException {
		super();

	    this.setLayout(new GridLayout(1, 3));
	    
	    
	    switch(typeSalle){
	    	case PETITE:
	    		sallePanel = new PetiteSallePanel(reservationsHeure, jourHeure, dialog);break;
	    	case MOYENNE:
	    		sallePanel = new MoyenneSallePanel(reservationsHeure, jourHeure, dialog);break;
	    	case ENREGISTREMENT:
	    		sallePanel = new EnregistrementSallePanel(reservationsHeure, jourHeure, dialog);break;
	    
	    }
	    add(sallePanel);
	}

}

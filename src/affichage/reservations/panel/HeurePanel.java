package affichage.reservations.panel;

import java.awt.GridLayout;

import javax.swing.JPanel;

import affichage.reservations.panel.sallePanel.EnregistrementSallePanel;
import affichage.reservations.panel.sallePanel.MoyenneSallePanel;
import affichage.reservations.panel.sallePanel.PetiteSallePanel;

public class HeurePanel extends JPanel{

	public HeurePanel() {
		super();

		//On définit le layout à utiliser sur le content pane
	    //Trois lignes sur deux colonnes
	    this.setLayout(new GridLayout(1, 3));
	    
	    JPanel petiteSallePanel = new PetiteSallePanel();
	    JPanel moyenneSallePanel = new MoyenneSallePanel();
	    JPanel enregistrementSallePanel = new EnregistrementSallePanel();
	    
	    //On ajoute le bouton au content pane de la JFrame
	    add(petiteSallePanel);
	    add(moyenneSallePanel);
	    add(enregistrementSallePanel);
	}

}

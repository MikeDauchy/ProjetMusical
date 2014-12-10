package affichage.informationsClient.jComboBox;


import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import donnees.salles.Salle;

public class ComboBoxSalle extends JPanel {
	private DefaultComboBoxModel comboSalles;
	private JComboBox combo;
	private String textComboBoxSalle;

	public ComboBoxSalle() {
		initialize();
	}

	private void initialize() {
		comboSalles = new DefaultComboBoxModel();
		comboSalles.addElement(Salle.type.PETITE.toString());
		comboSalles.addElement(Salle.type.MOYENNE.toString());
		combo = new JComboBox(comboSalles);
		add(combo);
	}
	
	
	public String getTextComboBoxSalle(){
		return combo.getSelectedItem().toString();
	}
	
	public void SelectItem(String value){
		combo.setSelectedItem(value);
	}

}

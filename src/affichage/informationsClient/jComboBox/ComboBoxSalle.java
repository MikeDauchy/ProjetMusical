package affichage.informationsClient.jComboBox;


import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ComboBoxSalle extends JPanel {
	private DefaultComboBoxModel comboSalles;
	private JComboBox combo;

	public ComboBoxSalle() {
		initialize();
	}

	private void initialize() {
		comboSalles = new DefaultComboBoxModel();
		comboSalles.addElement("Petite");
		comboSalles.addElement("Moyenne");
		combo = new JComboBox(comboSalles);
		add(combo);
	}

}

package affichage.informationsClient.jComboBox;


import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ComboBoxHeure extends JPanel {
	private DefaultComboBoxModel comboHeures;
	private JComboBox combo;

	public ComboBoxHeure() {
		initialize();
	}

	private void initialize() {
		comboHeures = new DefaultComboBoxModel();
		comboHeures.addElement("12h");
		comboHeures.addElement("24h");
		combo = new JComboBox(comboHeures);
		add(combo,BorderLayout.WEST);
	}

}

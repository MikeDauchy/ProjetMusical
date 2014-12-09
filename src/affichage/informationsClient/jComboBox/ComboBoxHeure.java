package affichage.informationsClient.jComboBox;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ComboBoxHeure extends JPanel{
	private DefaultComboBoxModel comboHeures;
	private JComboBox combo;
	private String textComboBoxHeure;

	public ComboBoxHeure() {
		initialize();
	}

	private void initialize() {
		comboHeures = new DefaultComboBoxModel();
		comboHeures.addElement("12");
		comboHeures.addElement("24");
		combo = new JComboBox(comboHeures);
		//combo.addActionListener(this);
		add(combo);
	}
	
	
	public int getTextComboBoxHeure(){
		return Integer.parseInt(textComboBoxHeure=combo.getSelectedItem().toString());
	}
	
	public void SelectItem(String value){
		combo.getSelectedItem().equals(value);
	}

}

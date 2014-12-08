package affichage.informationsClient.jComboBox;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ComboBoxHeure extends JPanel implements ActionListener {
	private DefaultComboBoxModel comboHeures;
	private JComboBox combo;
	private String textComboBoxHeure;

	public ComboBoxHeure() {
		initialize();
	}

	private void initialize() {
		comboHeures = new DefaultComboBoxModel();
		comboHeures.addElement("12h");
		comboHeures.addElement("24h");
		combo = new JComboBox(comboHeures);
		combo.addActionListener(this);
		add(combo);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object o= e.getSource();
		if (o==combo){
			textComboBoxHeure=combo.getSelectedItem().toString();
		}else{
			return;
		}
	}
	
	public String TextComboBoxHeure(){
		return textComboBoxHeure;
	}

}

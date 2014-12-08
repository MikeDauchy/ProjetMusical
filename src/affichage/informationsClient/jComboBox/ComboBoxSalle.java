package affichage.informationsClient.jComboBox;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ComboBoxSalle extends JPanel implements ActionListener {
	private DefaultComboBoxModel comboSalles;
	private JComboBox combo;
	private String textComboBoxSalle;

	public ComboBoxSalle() {
		initialize();
	}

	private void initialize() {
		comboSalles = new DefaultComboBoxModel();
		comboSalles.addElement("Petite");
		comboSalles.addElement("Moyenne");
		combo = new JComboBox(comboSalles);
		combo.addActionListener(this);
		add(combo);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object o= e.getSource();
		if (o==combo){
			textComboBoxSalle=combo.getSelectedItem().toString();
		}
	}
	
	public String TextComboBoxSalle(){
		return textComboBoxSalle;
	}

}

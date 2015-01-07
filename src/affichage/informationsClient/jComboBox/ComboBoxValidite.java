package affichage.informationsClient.jComboBox;


import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class ComboBoxValidite extends JPanel{
	private DefaultComboBoxModel comboValidite;
	private JComboBox combo;
	private String textComboBoxValidite;

	public ComboBoxValidite() {
		initialize();
	}

	private void initialize() {
		comboValidite = new DefaultComboBoxModel();
		comboValidite.addElement("3 mois");
		comboValidite.addElement("6 mois");
		combo = new JComboBox(comboValidite);
		add(combo);
	}


	public String getTextComboBoxHeure(){
		return textComboBoxValidite=combo.getSelectedItem().toString();
	}

	public void SelectItem(String value){
		combo.setSelectedItem(value);
	}

}

package affichage.jDialog;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class DialogueConsulterReservation extends JPanel{

	private static final long serialVersionUID = 1L;

	private Dialog dialog;
	private JTable tableau;

	JLabel labelTitre = new JLabel("Consultation des r�servations");

	Object[][] data={ 	
			{"8h",null,null,null,null,null, null},
			{"9h",null,null,null,null,null, null},
			{"10h",null,null,null,null,null, null},
			{"11h",null,null,null,null,null, null},
			{"12h",null,null,null,null,null, null},
			{"13h",null,null,null,null,null, null},
			{"14h",null,null,null,null,null, null},
			{"15h",null,null,null,null,null, null},
			{"16h",null,null,null,null,null, null},
			{"17h",null,null,null,null,null, null},
			{"18h",null,null,null,null,null, null},};
	
	String title[] = {"Horaires","Lundi" , "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"}; 


	public DialogueConsulterReservation(Dialog dialog) throws HeadlessException {
		super();
		
		this.dialog = dialog;
		
		this.tableau =new JTable(data,title);

		tableau.setRowHeight(50);
		
		labelTitre.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(labelTitre);
		add(new JScrollPane(tableau));
		

		setLayout( new BoxLayout(this, BoxLayout.Y_AXIS));

		labelTitre.setForeground(Color.blue);


		setVisible(true);
	}


}

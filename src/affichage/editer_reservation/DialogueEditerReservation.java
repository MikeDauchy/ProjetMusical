package affichage.editer_reservation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import donnees.Client;
import donnees.Facture;
import donnees.salles.EnregistrementSalle;
import donnees.salles.MoyenneSalle;
import donnees.salles.PetiteSalle;
import donnees.salles.Salle;
import exceptions.accesAuDonnees.CreationObjetException;
import exceptions.accesAuDonnees.ObjetExistant;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.donnes.ClientFactory;
import fabriques.donnes.FactureFactory;
import fabriques.donnes.ReservationFactory;
import fabriques.donnes.SalleFactory;

/**
 * @author Quentin
 *
 */
public class DialogueEditerReservation extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	//conteneur principaux
	private Dialog dialog;
	private JLabel labelTitre = new JLabel("Choisissez un horraire pour votre reservation", SwingConstants.CENTER);
	private JCheckBox checkboxRepeter = new JCheckBox("répéter ce crénaux chaque semaine");
	private JButton boutonValider = new  JButton("Valider");
	private JLabel labelValider = new JLabel("Reservation effectuée !", SwingConstants.CENTER);
	
	private Panel panelNorth = new Panel();
	private Panel panelCenter = new Panel();
	private Panel panelConseil = new Panel();
	private Panel panelSouth = new Panel();
	
	//Selection nombreHeure
	private Panel panelRadioButton = new Panel();
	private JRadioButton radioButtonOneHour = new JRadioButton("1h", true);
	private JRadioButton radioButtonTwoHours = new JRadioButton("2h");
	
	//Choix des heures
	private ButtonGroup group = new ButtonGroup();
	private Panel panelChoixHeures = new Panel();
	private JLabel labelChoixHeure = new JLabel("Selectionnez l'heure de début :");
	private Integer labelsHeures[] = { 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
	private JComboBox<Integer> ComboHoraire = new JComboBox<Integer>(labelsHeures);
	
	//Choix type salle
	private Panel panelChoixSalle = new Panel();
	private JLabel labelChoixSalle = new JLabel("Selectionnez le type de salle :");
	private String labelsSalle[] = { Salle.type.PETITE.toString(), Salle.type.MOYENNE.toString(), Salle.type.ENREGISTREMENT.toString()};
	private JComboBox<String> ComboSalle = new JComboBox<String>(labelsSalle);
	
	//Date picker
	private UtilDateModel model = new UtilDateModel();
	private Properties p = new Properties();
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	
	


	public DialogueEditerReservation(Dialog dialog) {
		super();
		this.dialog = dialog;
		this.setLayout(new BorderLayout());
		this.add(panelNorth, BorderLayout.NORTH);
		this.add(panelCenter, BorderLayout.CENTER);
		this.add(panelSouth, BorderLayout.SOUTH);
		
		panelSouth.setLayout(new GridLayout(2, 1));
		panelSouth.add(labelValider);
		panelSouth.add(boutonValider);
		labelValider.setForeground(Color.GREEN);
		labelValider.setVisible(false);
		
		group.add(radioButtonOneHour);
		group.add(radioButtonTwoHours);
		radioButtonOneHour.setMnemonic(KeyEvent.VK_C);
		radioButtonTwoHours.setMnemonic(KeyEvent.VK_M);
		
		panelNorth.add(labelTitre);
		panelNorth.setBackground(new Color(164, 211, 238));
		checkboxRepeter.setBackground(new Color(26, 162, 189));
		

		creationDatePicker();
		
		panelRadioButton.setLayout(new GridLayout(1, 3));
		panelRadioButton.add(new JLabel("Selectionner un nombre d'heure :"));
		panelRadioButton.add(radioButtonOneHour);
		panelRadioButton.add(radioButtonTwoHours);
		panelRadioButton.setBackground(new Color(26, 162, 189));
		radioButtonOneHour.setBackground(new Color(26, 162, 189));
		radioButtonTwoHours.setBackground(new Color(26, 162, 189));
		
		panelChoixHeures.setLayout(new GridLayout(1, 3));
		panelChoixHeures.add(labelChoixHeure);
		panelChoixHeures.add(ComboHoraire);
		panelChoixHeures.add(new JLabel(" Heure"));
		
		panelChoixSalle.setLayout(new GridLayout(1, 3));
		panelChoixSalle.add(labelChoixSalle);
		panelChoixSalle.add(ComboSalle);
		panelChoixSalle.add(new JLabel(" Salle"));
		
		panelConseil.setLayout(new GridLayout(2,2));
		panelConseil.add(new JLabel("Nous vous conseillons cette date :"));
		panelConseil.add(datePicker);
		panelConseil.add(new Label());
		panelConseil.add( new JLabel("Mais vous pouvez la modifier", SwingConstants.CENTER));
		
		panelCenter.setLayout(new GridLayout(6, 1));
		panelCenter.add(panelConseil);
		panelCenter.add(panelRadioButton);
		panelCenter.add(panelChoixHeures);
		panelCenter.add(panelChoixSalle);
		panelCenter.add(checkboxRepeter);
		
		boutonValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					pressValider();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		
		dialog.pack();
		dialog.setLocationRelativeTo(null);
	}
	
	private void creationDatePicker(){
		//model.setDate(20,04,2014);
		p.put("text.today", "Aujourd'hui");
		p.put("text.month", "Mois");
		p.put("text.year", "Année");
		datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
	}
	
	private void pressValider() throws SQLException{
		List<Client> listClient = ClientFactory.getInstance().lister();
		List<String> nomClient = new ArrayList<String>();
		for(Client client : listClient){
			nomClient.add(client.getNom());
		}
		String nomClientSelect = (String) JOptionPane.showInputDialog(this, 
		        "Quel est le client pour cette reservation ?",
		        "Choisissez le client",
		        JOptionPane.QUESTION_MESSAGE, 
		        null, 
		        nomClient.toArray(), 
		        nomClient.get(0));
		
		if(nomClientSelect != null){
			try {
				//TODO:tester qu'on recoit bien qu'un client
				Client client = ClientFactory.getInstance().rechercherByNom(nomClientSelect).get(0);
				
				Date dateDebut = model.getValue();
				dateDebut.setHours(labelsHeures[ComboHoraire.getSelectedIndex()]);
				dateDebut.setMinutes(0);
				dateDebut.setSeconds(0);
				Date dateFin = (Date)dateDebut.clone();
				int nbHeure;
				
				if(group.getSelection() == radioButtonOneHour){
					dateFin.setHours(dateDebut.getHours()+1);
					nbHeure = 1;
				}else{
					dateFin.setHours(dateDebut.getHours()+2);
					nbHeure = 2;
				}
				Salle salle = null;
				switch(Salle.type.valueOf(labelsSalle[ComboSalle.getSelectedIndex()])){
					case PETITE:
						salle = new PetiteSalle();
						salle.setTypeSalle(Salle.type.PETITE);break;
					case MOYENNE:
						salle = new MoyenneSalle();
						salle.setTypeSalle(Salle.type.MOYENNE);break;
					case ENREGISTREMENT:
						salle = new EnregistrementSalle();
						salle.setTypeSalle(Salle.type.ENREGISTREMENT);break;
				}
				
				
				Facture facture = FactureFactory.getInstance().creer(client.getIdClient(), false);
				salle  = SalleFactory.getInstance().rechercherByTypeSalle(salle.getTypeSalle());
				if(checkboxRepeter.isSelected()){
					for(int i = 0; i != 52; i++){
						ReservationFactory.getInstance().creer(facture.getIdFacture(), salle.getIdSalle(), nbHeure, dateDebut, dateFin);
						GregorianCalendar calendar = new java.util.GregorianCalendar();
						calendar.setTime(dateDebut);
						calendar.add(Calendar.DATE, 7);
						dateDebut = calendar.getTime();
						calendar = new java.util.GregorianCalendar();
						calendar.setTime(dateFin);
						calendar.add(Calendar.DATE, 7);
						dateFin = calendar.getTime();
					}
				}else{
					ReservationFactory.getInstance().creer(facture.getIdFacture(), salle.getIdSalle(), nbHeure, dateDebut, dateFin);
				}
				labelValider.setVisible(true);
				
			} catch (CreationObjetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ObjetExistant e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ObjetInconnu e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

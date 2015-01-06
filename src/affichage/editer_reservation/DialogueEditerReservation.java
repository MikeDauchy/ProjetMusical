package affichage.editer_reservation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import metier.GestionReservation;

import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.UtilDateModel;

import donnees.Client;
import donnees.salles.EnregistrementSalle;
import donnees.salles.MoyenneSalle;
import donnees.salles.PetiteSalle;
import donnees.salles.Salle;
import exceptions.accesAuDonnees.CreationObjetException;
import exceptions.accesAuDonnees.ObjetExistant;
import exceptions.accesAuDonnees.ObjetInconnu;
import exceptions.metier.SalleDejaReserveException;
import fabriques.donnes.ClientFactory;

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
	private JButton boutonValider = new  JButton("Valider");
	private JLabel labelMessage = new JLabel("", SwingConstants.CENTER);
	
	private Panel panelNorth = new Panel();
	private Panel panelCenter = new Panel();
	private Panel panelConseil = new Panel();
	private Panel panelSouth = new Panel();
	private Panel panelCenterColor = new Panel();
	
	//Selection nombreHeure
	private Panel panelRadioButton = new Panel();
	private JRadioButton radioButtonOneHour = new JRadioButton("1h", true);
	private JRadioButton radioButtonTwoHours = new JRadioButton("2h");
	
	//Choix des heures
	private ButtonGroup group = new ButtonGroup();
	private Panel panelChoixHeures = new Panel();
	private JLabel labelChoixHeure = new JLabel("Selectionnez l'heure de début :");
	private Integer labelsHeures[] = {9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
	private JComboBox<Integer> ComboHoraire = new JComboBox<Integer>(labelsHeures);
	
	//Choix type salle
	private Panel panelChoixSalle = new Panel();
	private JLabel labelChoixSalle = new JLabel("Selectionnez le type de salle :");
	private String labelsSalle[] = {Salle.type.PETITE.toString(), Salle.type.MOYENNE.toString(), Salle.type.ENREGISTREMENT.toString()};
	private JComboBox<String> ComboSalle = new JComboBox<String>(labelsSalle);
	
	//Date picker
	private UtilDateModel model = new UtilDateModel();
	private JDatePicker picker = new JDateComponentFactory().createJDatePicker();
	private JButton boutonConseiller = new JButton("Conseiller une date");
	private JLabel labelDatePicker = new JLabel("Vous pouvez modifier la date", SwingConstants.CENTER);
	
	//Choix repetion
	private JPanel panelRepetition = new JPanel();
	private JCheckBox checkboxRepeter = new JCheckBox("Répéter ce créneau chaque semaine");
	private JLabel labelRepetition = new JLabel("choisissez le nombre de répétitions", SwingConstants.CENTER);
	private JTextField textFieldRepetition = new JTextField();
	
	private Date dateModel = new Date();
	
	public DialogueEditerReservation(Dialog dialog) {
		super();
		this.dialog = dialog;
		this.setLayout(new BorderLayout());
		this.add(panelNorth, BorderLayout.NORTH);
		this.add(panelCenterColor, BorderLayout.CENTER);
		this.add(panelSouth, BorderLayout.SOUTH);
		
		panelSouth.setBackground(new  Color(52, 52, 52));
		panelSouth.setLayout(new GridLayout(2, 1));
		panelSouth.add(labelMessage);
		panelSouth.add(boutonValider);
		labelMessage.setVisible(false);
		
		group.add(radioButtonOneHour);
		group.add(radioButtonTwoHours);
		radioButtonOneHour.setMnemonic(KeyEvent.VK_C);
		radioButtonTwoHours.setMnemonic(KeyEvent.VK_M);
		
		panelNorth.add(labelTitre);
		panelNorth.setBackground(new Color(52, 52, 52));

		creationDatePicker(new Date());
		
		panelRadioButton.add(new JLabel("Selectionner un nombre d'heure :"));
		panelRadioButton.add(radioButtonOneHour);
		panelRadioButton.add(radioButtonTwoHours);
		panelRadioButton.setBackground(new  Color(26, 162, 189));
		radioButtonOneHour.setBackground(new   Color(26, 162, 189));
		radioButtonTwoHours.setBackground(new   Color(26, 162, 189));
		
		panelChoixHeures.setLayout(new GridLayout(1, 3));
		panelChoixHeures.add(labelChoixHeure);
		panelChoixHeures.add(ComboHoraire);
		panelChoixHeures.add(new JLabel(" Heure"));
		
		panelChoixSalle.setLayout(new GridLayout(1, 3));
		panelChoixSalle.add(labelChoixSalle);
		panelChoixSalle.add(ComboSalle);
		panelChoixSalle.add(new JLabel(" Salle"));
		
		panelConseil.setLayout(new GridLayout(2,2));
		panelConseil.add(boutonConseiller);
		panelConseil.add((JComponent) picker);
		panelConseil.add(new Label());
		panelConseil.add(labelDatePicker);
		
		panelRepetition.setBackground(new  Color(26, 162, 189));
		checkboxRepeter.setBackground(new Color(26, 162, 189));
		textFieldRepetition.setPreferredSize(new Dimension(25, 30));
		panelRepetition.add(checkboxRepeter);
		panelRepetition.add(labelRepetition);
		panelRepetition.add(textFieldRepetition);
		
		panelCenter.setLayout(new GridLayout(6, 1));
		panelCenter.add(panelRadioButton);
		panelCenter.add(panelChoixHeures);
		panelCenter.add(panelChoixSalle);
		panelCenter.add(panelRepetition);
		panelCenter.add(panelConseil);
		
		panelCenterColor.setBackground(new Color(205, 211, 215));
		panelCenterColor.setLayout(new GridLayout(1, 1));
		panelCenterColor.add(panelCenter);
		
		labelTitre.setForeground(Color.white);
		
		boutonValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					try {
						pressValider();
					} catch (CreationObjetException | ObjetInconnu e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		boutonConseiller.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pressConseiller();
			}
		});
		
		
		dialog.pack();
		dialog.setLocationRelativeTo(null);
	}
	
	private void creationDatePicker(Date d){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
	    picker.setTextEditable(true);
	    picker.setShowYearButtons(true);
	    picker.getModel().setYear(calendar.get(calendar.YEAR));
	    picker.getModel().setMonth(calendar.get(calendar.MONTH));
	    picker.getModel().setDay(calendar.get(calendar.DAY_OF_MONTH));
	    picker.getModel().setSelected(true);
		
	}
	
	private void pressConseiller(){
		
		int nbHeure;
		if(radioButtonOneHour.isSelected()){
			nbHeure = 1;
		}else{
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
		
		int nbRepetition = 1;
		if(checkboxRepeter.isSelected()){
			if(!textFieldRepetition.getText().isEmpty()){
				nbRepetition = Integer.parseInt(textFieldRepetition.getText());
			}
		}
		
		GestionReservation gestionReservation = new GestionReservation();
		try {
			Date dateConseille = gestionReservation.conseillerDateReservation(labelsHeures[ComboHoraire.getSelectedIndex()], nbHeure, salle.getTypeSalle(), nbRepetition);
			dateModel = dateConseille;
			creationDatePicker(dateConseille);
		} catch (SQLException | ObjetInconnu e) {
			labelMessage.setText("Tous les creneaux avec votre configuration sont pris");
			labelMessage.setForeground(Color.RED);
			e.printStackTrace();
		}
	}
	
	private void pressValider() throws ObjetInconnu, SQLException {
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
			Client client = null;
			try {
				client = ClientFactory.getInstance().rechercherByNom(nomClientSelect).get(0);
			} catch (ObjetInconnu e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(client != null){
				
				Date dateDebut = (model.getValue() == null)?dateModel: model.getValue();
				dateDebut.setHours(labelsHeures[ComboHoraire.getSelectedIndex()]);
				dateDebut.setMinutes(0);
				dateDebut.setSeconds(0);
				Date dateFin = (Date)dateDebut.clone();
				
				int nbHeure;
				if(radioButtonOneHour.isSelected()){
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
				
				int nbRepetition = 1;
				if(checkboxRepeter.isSelected()){
					if(!textFieldRepetition.getText().isEmpty()){
						nbRepetition = Integer.parseInt(textFieldRepetition.getText());
					}
				}
				
				GestionReservation gestionReservation = new GestionReservation();
				try {
					gestionReservation.reserverTypeSalle(dateDebut, dateFin, nbHeure, salle.getTypeSalle(), client, nbRepetition);
					labelMessage.setForeground(Color.GREEN);
					labelMessage.setText("Reservation effectuée !");
				} catch (SalleDejaReserveException | CreationObjetException | ObjetExistant | SQLException e) {
					labelMessage.setText("Le crenau choisis est déjà pris");
					labelMessage.setForeground(Color.RED);
					e.printStackTrace();
				}
				labelMessage.setVisible(true);
			}
		}
	}

}

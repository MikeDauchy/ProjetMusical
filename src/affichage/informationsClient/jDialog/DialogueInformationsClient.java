package affichage.informationsClient.jDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import metier.GestionInfoClient;
import metier.GestionReservation;
import affichage.informationsClient.jComboBox.ComboBoxHeure;
import affichage.informationsClient.jComboBox.ComboBoxSalle;
import affichage.informationsClient.jComboBox.ComboBoxValidite;
import affichage.informationsClient.listCellRenderer.ClientsListCellRenderer;
import affichage.informationsClient.listCellRenderer.ForfaitsListCellRenderer;
import affichage.informationsClient.listCellRenderer.ReservationsListCellRenderer;
import affichage.paiement.jDialog.DialoguePaiement;
import donnees.Client;
import donnees.Forfait;
import donnees.reservations.Reservation;
import donnees.salles.Salle;
import exceptions.accesAuDonnees.CreationObjetException;
import exceptions.accesAuDonnees.ObjetExistant;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.donnes.ClientFactory;
import fabriques.donnes.ForfaitFactory;
import fabriques.donnes.ReservationFactory;



public class DialogueInformationsClient extends JPanel implements ActionListener, ListSelectionListener{

	private static final long serialVersionUID = 1L;

	private GestionInfoClient gestclient = new GestionInfoClient();
	private Client clientSelectionne;
	private Reservation reservationSelectionne;
	private Forfait forfaitSelectionne;
	private Salle salle;


	private SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

	private JDialog dialog;
	private JDialog dialogPaiementReservation;

	// partie gauche de la fenetre : gestion des entrees
	//les listes des objets
	private DefaultListModel modelListClient = new DefaultListModel();
	private JList listClients = new JList(modelListClient);

	//les champs clients
	private JTextField fieldNom = new JTextField (20);
	private JTextField fieldPrenom = new JTextField(20);
	private JTextField fieldNumero = new JTextField(20);
	private JTextField fieldPtFidelite = new JTextField(20);

	// les boutons clients
	private JButton addC = new JButton ("Ajouter");
	private JButton deleteC = new JButton ("Supprimer");
	private JButton clearC = new JButton ("Clear");

	//partie droite de la fenetre : gestion des reservation
	//la liste des objets
	private DefaultListModel modelListReservation = new DefaultListModel();
	private JList listReservations = new JList(modelListReservation);
	private DefaultListModel modelListForfait = new DefaultListModel();
	private JList listForfaits = new JList(modelListForfait);

	//les champs réservations
	private JTextField fieldDateR = new JTextField (20);
	private JTextField fieldNbHeuresR = new JTextField (20);
	private JTextField fieldHoraire = new JTextField(20);
	private JTextField fieldSalleR = new JTextField(20);
	private JTextField fieldEtatR = new JTextField(20);

	// les boutons réservations
	private JButton confR = new JButton ("Payer");
	private JButton annulR = new JButton ("Annuler");

	//les champs forfaits
	private JTextField fieldNbHeuresDispo = new JTextField(20);
	private JTextField fieldDateDebutF = new JTextField (20);
	private JTextField fieldCreditF = new JTextField (20);
	private JTextField fieldDateFinF = new JTextField (20);
	private ComboBoxSalle comboBoxSalle = new ComboBoxSalle();
	private ComboBoxHeure comboBoxHeure = new ComboBoxHeure();
	private ComboBoxValidite comboBoxValidite = new ComboBoxValidite();


	// les boutons forfaits
	private JButton createF = new JButton ("Creer");

	//Entree entreeSelectionne;

	// constructeur
	public DialogueInformationsClient ( JDialog dialog) throws Exception{

		this.dialog = dialog;

		//On charge la JList Client
		List<Client> lesClients = ClientFactory.getInstance().lister();
		for(Client e : lesClients){
			modelListClient.addElement(e);
		}

		fieldPtFidelite.setEditable(false);

		//On met notre render Client
		ListCellRenderer maListClientsCellRenderer = new ClientsListCellRenderer();
		listClients.setCellRenderer(maListClientsCellRenderer);


		//construction du panel de gauche

		//construction des text field pour saisie ou affichage
		JLabel labelNom = new JLabel ("Nom :");
		JLabel labelPrenom = new JLabel ("Prenom : ");
		JLabel labelNumero = new JLabel ("Numéro : ");
		JLabel labelPtFidelite = new JLabel ("Points de fidélité : ");



		//on met les labels et les fields dans des panels
		JPanel lesLabelsC = new JPanel(new GridLayout (4,1));
		lesLabelsC.add (labelNom);
		lesLabelsC.add (labelPrenom);
		lesLabelsC.add(labelNumero);
		lesLabelsC.add(labelPtFidelite);
		lesLabelsC.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));

		JPanel lesFieldsC = new JPanel(new GridLayout (4,2));
		lesFieldsC.add (fieldNom);
		lesFieldsC.add (fieldPrenom);
		lesFieldsC.add(fieldNumero);
		lesFieldsC.add(fieldPtFidelite);
		lesFieldsC.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));

		// construction d'un panel pour mettre les 2 boutons
		JPanel pboutonsC = new JPanel (new GridLayout (1,0));
		pboutonsC.add (addC);
		pboutonsC.add (deleteC);
		pboutonsC.add (clearC);
		pboutonsC.setBorder(BorderFactory.createEmptyBorder(70,10,10,10));


		JPanel formC = new JPanel (new BorderLayout());
		formC.add (lesLabelsC, BorderLayout.WEST);
		formC.add (lesFieldsC, BorderLayout.EAST);
		formC.add (pboutonsC, BorderLayout.SOUTH);


		JScrollPane listEntreesScrollPane = new JScrollPane (listClients );
		listEntreesScrollPane.setPreferredSize (new Dimension (200,200));
		listEntreesScrollPane.setMinimumSize (new Dimension (200,200));
		listEntreesScrollPane.setBorder (BorderFactory.createTitledBorder (
				BorderFactory.createLineBorder (Color.cyan),
				"Clients"));
		listClients .setSelectionMode (ListSelectionModel.SINGLE_SELECTION);

		//panel de gauche
		JPanel panelGauche=new JPanel();
		panelGauche.setLayout(new BoxLayout(panelGauche, BoxLayout.Y_AXIS));

		// on ajoute la liste et le formuleire
		listEntreesScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelGauche.add(listEntreesScrollPane);
		formC.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelGauche.add (formC);

		//construction du panel de droite

		//construction des text field réservations pour saisie ou affichage
		JLabel labelDateR = new JLabel ("Date : ");
		JLabel labelExpirationR = new JLabel ("Nombre d'heures : ");
		JLabel labelHoraireR = new JLabel ("Horaire : ");
		JLabel labelSalleR = new JLabel ("Salle : ");
		JLabel labelEtat = new JLabel ("Etat : ");

		//mise en place de l'interdiction d'ecriture dans les jtextfield de reservations
		fieldDateR.setEditable(false);
		fieldNbHeuresR.setEditable(false);
		fieldHoraire.setEditable(false);
		fieldSalleR.setEditable(false);
		fieldEtatR.setEditable(false);

		//Construction des text field forfait pour saisie ou affichage
		JLabel labelNbHeuresDispo = new JLabel ("Nombre d'heures : ");
		JLabel labelSalleF = new JLabel ("Salle : ");
		JLabel labelValiditeF =  new JLabel("Validite : ");
		JLabel labelCreditF = new JLabel("Credit : ");
		JLabel labelDateDebutF = new JLabel ("Date début : ");
		JLabel labelDateFinF = new JLabel ("Date fin : ");


		fieldNbHeuresDispo.setEditable(false);
		fieldDateDebutF.setEditable(false);
		fieldCreditF.setEditable(false);
		fieldDateFinF.setEditable(false);

		//on met les labels et les fields réservations dans des panels
		JPanel lesLabelsR = new JPanel(new GridLayout (5,2));
		lesLabelsR.add (labelDateR);
		lesLabelsR.add (labelExpirationR);
		lesLabelsR.add (labelHoraireR);
		lesLabelsR.add (labelSalleR);
		lesLabelsR.add (labelEtat);
		lesLabelsR.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));
		JPanel lesFieldsR = new JPanel(new GridLayout (5,2));
		lesFieldsR.add (fieldDateR);
		lesFieldsR.add(fieldNbHeuresR);
		lesFieldsR.add(fieldHoraire);
		lesFieldsR.add (fieldSalleR);
		lesFieldsR.add(fieldEtatR);
		lesFieldsR.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));

		//on met les labels et les fields forfaits dans des panels
		JPanel lesLabelsF = new JPanel(new GridLayout (6,2));
		lesLabelsF.add (labelNbHeuresDispo);
		lesLabelsF.add(labelSalleF);
		lesLabelsF.add(labelValiditeF);
		lesLabelsF.add (labelCreditF);
		lesLabelsF.add (labelDateDebutF);
		lesLabelsF.add (labelDateFinF);
		lesLabelsF.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));
		JPanel lesFieldsF = new JPanel(new GridLayout (6,2));
		lesFieldsF.add(comboBoxHeure);
		lesFieldsF.add(comboBoxSalle);
		lesFieldsF.add(comboBoxValidite);
		lesFieldsF.add (fieldCreditF);
		lesFieldsF.add(fieldDateDebutF);
		lesFieldsF.add(fieldDateFinF);
		lesFieldsF.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));

		// construction d'un panel pour mettre les 2 boutons r�servation
		JPanel pboutonsR = new JPanel (new GridLayout (1,0));
		pboutonsR.add (confR);
		pboutonsR.add (annulR);
		pboutonsR.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		JPanel formR = new JPanel (new BorderLayout());
		formR.add (lesLabelsR, BorderLayout.WEST);
		formR.add (lesFieldsR, BorderLayout.EAST);
		formR.add (pboutonsR, BorderLayout.SOUTH);

		//construction d'un panel pour mettre les 2 boutons forfait
		JPanel pboutonsF = new JPanel(new GridLayout(1,0));
		pboutonsF.add (createF);
		pboutonsF.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		JPanel formF = new JPanel (new BorderLayout());
		formF.add (lesLabelsF, BorderLayout.WEST);
		formF.add (lesFieldsF, BorderLayout.EAST);
		formF.add (pboutonsF, BorderLayout.SOUTH);

		// Ajout ScrollPane reservations
		JScrollPane listReservationsScrollPane = new JScrollPane (listReservations);
		listReservationsScrollPane.setPreferredSize (new Dimension (100,100));
		listReservationsScrollPane.setMinimumSize (new Dimension (200,200));
		listReservationsScrollPane.setBorder (BorderFactory.createTitledBorder (
				BorderFactory.createLineBorder (Color.cyan),
				"Réservations"));
		listReservations.setSelectionMode (ListSelectionModel.SINGLE_SELECTION);

		//Ajout ScrollPane forfaits
		JScrollPane listForfaitsScrollPane = new JScrollPane (listForfaits);
		listForfaitsScrollPane.setPreferredSize (new Dimension (100,100));
		listForfaitsScrollPane.setMinimumSize (new Dimension (100,100));
		listForfaitsScrollPane.setBorder (BorderFactory.createTitledBorder (
				BorderFactory.createLineBorder (Color.cyan),
				"Forfaits"));
		listForfaits.setSelectionMode (ListSelectionModel.SINGLE_SELECTION);

		//panel de droite
		JPanel panelDroite=new JPanel();
		panelDroite.setLayout(new BoxLayout(panelDroite, BoxLayout.Y_AXIS));

		// on ajoute la liste et le formulaire
		listReservationsScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		listForfaitsScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

		panelDroite.add(listReservationsScrollPane);
		panelDroite.add(formR);
		panelDroite.add(listForfaitsScrollPane);
		panelDroite.add(formF);


		//on assemble gauche et droite
		setLayout (new BorderLayout());
		add (panelGauche, BorderLayout.WEST);
		add (panelDroite, BorderLayout.EAST);

		// Ajout des action listener
		addC.addActionListener(this);
		deleteC.addActionListener(this);
		clearC.addActionListener(this);
		confR.addActionListener(this);
		annulR.addActionListener(this);
		createF.addActionListener(this);

		// Ajout listSelectionListener
		listClients.addListSelectionListener(this);
		listReservations.addListSelectionListener(this);
		listForfaits.addListSelectionListener(this);

		setVisible(true);
		dialog.setResizable(false);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o= e.getSource();
		if(o == addC ) {

			if((!(fieldNom.getText().length()==0 || fieldPrenom.getText().length()==0 || fieldNumero.getText().length()==0)) && fieldNom.getText().matches("[a-zA-Z]*") && fieldPrenom.getText().matches("[a-zA-Z]*")){
				if(fieldNumero.getText().matches("[0-9]*")&& fieldNumero.getText().length()==10){
					try{
						Client client = ClientFactory.getInstance().creer(fieldNom.getText(), fieldPrenom.getText(),fieldNumero.getText(),0);
						modelListClient.addElement(client);
					}catch(ObjetExistant exception){
						JOptionPane.showMessageDialog(dialog, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
					} catch (CreationObjetException e1) {
						JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
				else{
					JOptionPane.showMessageDialog(dialog, "Veuillez mettre une chaine d'une longueur de 10 chiffres pour le numéro", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(dialog, "Veuillez vérifier la validité des champs", "Erreur", JOptionPane.ERROR_MESSAGE);
			}

		} else if(o== deleteC) {
			// Supprimer client
			try {
				gestclient.supprimerClient(clientSelectionne);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);

			} catch (ObjetInconnu e1) {
				JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		} else if(o== clearC) {
			// Nettoyer information fields client
			ClearField();
		} else if(o== confR) {
			try {
				//JDialog pour le paiements des reservations non validé
				dialogPaiementReservation = new JDialog (dialog, "Paiement facture",true);
				dialogPaiementReservation.getContentPane().add(new DialoguePaiement(dialogPaiementReservation,reservationSelectionne), BorderLayout.CENTER);
				dialogPaiementReservation.setLocationRelativeTo(dialog);
				dialogPaiementReservation.pack();
				dialogPaiementReservation.setVisible(true);
				loadListReservation();
				loadListForfaits();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			} catch (ObjetInconnu e1) {
				JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			}

		} else if(o== annulR) {
			// Annuler réservation
			try {
				ReservationFactory.getInstance().supprimer(reservationSelectionne);
				modelListReservation.removeElement(reservationSelectionne);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			} catch (ObjetInconnu e1) {
				JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}else if(o== createF) {
			// Creation forfait
			Date dateDebut=null;
			Date dateFin=null;
			Calendar cal = GregorianCalendar.getInstance();
			dateDebut = cal.getTime();
			if (comboBoxValidite.getTextComboBoxHeure().equals("3 mois")){
				cal.add(Calendar.MONTH, 3);

			}else if(comboBoxValidite.getTextComboBoxHeure().equals("6 mois")){
				cal.add(Calendar.MONTH, 6);
			}
			dateFin = cal.getTime();
			try {
				Forfait forfait =ForfaitFactory.getInstance().creer(clientSelectionne.getIdClient(),comboBoxHeure.getTextComboBoxHeure(), dateDebut, dateFin, 0.0, Salle.type.valueOf(comboBoxSalle.getTextComboBoxSalle()));
				modelListForfait.addElement(forfait);
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			} catch (CreationObjetException e1) {
				JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			} catch (ObjetExistant e1) {
				JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}else {
			return;
		}

	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		Object o= event.getSource();
		if (o == listClients){
			// Gestion de la JList Clients
			ClearReservation();
			ClearForfaits();
			// On récupère le client selectionné
			Client client = (Client) listClients.getSelectedValue();
			clientSelectionne = client;
			fieldNom.setText(client.getNom());
			fieldPrenom.setText(client.getPrenom());
			fieldNumero.setText(client.getNumTel());
			fieldPtFidelite.setText(Integer.toString((client.getPointFidelite())));

			loadListReservation();
			loadListForfaits();

		}else if(o==listReservations){
			// Gestion de la JList reservations

			// On récupère la reservation selectionné
			Reservation reservation = (Reservation) listReservations.getSelectedValue();

			//Si il y a une reservation
			if(reservation != null){
				// On active le bouton d'annulation
				annulR.setEnabled(true);
				// On récupère la réservation selectionné
				reservationSelectionne = reservation;
				fieldDateR.setText(formatter.format(reservation.getDateDebut()));
				fieldNbHeuresR.setText(Integer.toString(reservation.getNbHeure()));
				fieldHoraire.setText(Integer.toString(reservation.getDateDebut().getHours())+"h -> "+Integer.toString(reservation.getDateFin().getHours())+"h" );
				try {
					salle = reservation.getSalle();
				} catch (ObjetInconnu e) {
					JOptionPane.showMessageDialog(dialog, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(dialog, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				fieldSalleR.setText(salle.getDescription());

				try {
					if (reservation.getFacture().isEstPaye()) {
						fieldEtatR.setText("Validé");
						confR.setEnabled(false);
					} else {
						fieldEtatR.setText("Non validé");
						confR.setEnabled(true);
					}
				} catch (ObjetInconnu e) {
					JOptionPane.showMessageDialog(dialog, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(dialog, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
				}

			}
		} else if(o== listForfaits) {
			// Gestion de la JList forfaits

			// On récupère le forfait selectionné
			Forfait forfait = (Forfait) listForfaits.getSelectedValue();
			if(forfait !=null){
				forfaitSelectionne = forfait;
				comboBoxHeure.SelectItem(Integer.toString(forfaitSelectionne.getNbHeure()));
				comboBoxSalle.SelectItem(forfaitSelectionne.getTypeSalle().toString());
				fieldCreditF.setText(Integer.toString(forfaitSelectionne.getNbHeure())+"h");
				fieldDateDebutF.setText(formatter.format(forfaitSelectionne.getDateDebut()));
				fieldDateFinF.setText(formatter.format(forfaitSelectionne.getDateFin()));
			}
		}else {
			return;
		}
	}

	public void ClearField(){
		fieldNom.setText("");
		fieldPrenom.setText("");
		fieldNumero.setText("");
		fieldPtFidelite.setText("");
		ClearReservation();
		ClearForfaits();
	}

	public void ClearReservation(){
		// On vide les JList reservations
		listReservations.removeAll();
		modelListReservation.removeAllElements();
		// On vide les JTextField reservations
		fieldDateR.setText("");
		fieldNbHeuresR.setText("");
		fieldHoraire.setText("");
		fieldSalleR.setText("");
		fieldEtatR.setText("");
		// On désactive le bouton de paiement et annulation
		confR.setEnabled(false);
		annulR.setEnabled(false);
	}


	public void ClearForfaits(){
		// On vide les JList forfaits
		listForfaits.removeAll();
		modelListForfait.removeAllElements();
		// On vide les JTextField forfaits
		fieldCreditF.setText("");
		fieldDateDebutF.setText("");
		fieldDateFinF.setText("");
		fieldNbHeuresDispo.setText("");
	}

	public void loadListReservation(){
		//On nettoie la Jlist et les JtextFields Réservations
		ClearReservation();
		
		try {
			//On charge la JList Réservations
			List<Reservation> lesReservations = clientSelectionne.getListReservations();
			for(Reservation e : lesReservations){
				modelListReservation.addElement(e);
			}
		} catch (ObjetInconnu e1) {
			JOptionPane.showMessageDialog(dialog, "Aucune réservation associé à ce client", "Information", JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		}
		//On remplit le renderer Réservations
		ListCellRenderer maListReservationsCellRenderer = new ReservationsListCellRenderer();
		listReservations.setCellRenderer(maListReservationsCellRenderer);
	}

	public void loadListForfaits(){
		//On nettoie la Jlist et les JtextFields Forfaits
		ClearForfaits();
		try{
			//On charge la JList Forfaits
			List<Forfait> lesForfaits = clientSelectionne.getListFofaits();
			for(Forfait e : lesForfaits){
				modelListForfait.addElement(e);
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		} catch (ObjetInconnu e1) {
			JOptionPane.showMessageDialog(dialog, "Aucune forfait associé à ce client", "Information", JOptionPane.INFORMATION_MESSAGE);
		}

		//On remplit le renderer Forfait
		ListCellRenderer maListForfaitsCellRenderer = new ForfaitsListCellRenderer();
		listForfaits.setCellRenderer(maListForfaitsCellRenderer);
	}

}

package affichage.informationsClient.jDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
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

import affichage.informationsClient.jComboBox.ComboBoxHeure;
import affichage.informationsClient.jComboBox.ComboBoxSalle;
import affichage.informationsClient.jComboBox.ComboBoxValidite;
import affichage.informationsClient.listCellRenderer.ClientsListCellRenderer;
import affichage.informationsClient.listCellRenderer.ForfaitsListCellRenderer;
import affichage.informationsClient.listCellRenderer.ReservationsListCellRenderer;
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

	Client clientSelectionne;
	Reservation reservationSelectionne;
	Forfait forfaitSelectionne;
	Salle salle;
	Calendar cal = GregorianCalendar.getInstance();

	SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

	JDialog dialog;

	// partie gauche de la fenetre : gestion des entrees
	//les listes des objets
	DefaultListModel modelListClient = new DefaultListModel();
	JList listClients = new JList(modelListClient);



	//les champs clients
	JTextField fieldNom = new JTextField (20);
	JTextField fieldPrenom = new JTextField(20);
	JTextField fieldNumero = new JTextField(20);
	JTextField fieldPtFidelite = new JTextField(20);

	// les boutons clients
	JButton addC = new JButton ("Ajouter");
	JButton deleteC = new JButton ("Supprimer");
	JButton clearC = new JButton ("Clear");

	//partie droite de la fenetre : gestion des reservation
	//la liste des objets
	DefaultListModel modelListReservation = new DefaultListModel();
	JList listReservations = new JList(modelListReservation);
	DefaultListModel modelListForfait = new DefaultListModel();
	JList listForfaits = new JList(modelListForfait);

	//les champs r�ervations
	JTextField fieldDateR = new JTextField (20);
	JTextField fieldExpirationR = new JTextField (20);
	JTextField fieldHoraire = new JTextField(20);
	JTextField fieldSalleR = new JTextField(20);
	JTextField fieldEtatR = new JTextField(20);

	// les boutons r�servations
	JButton confR = new JButton ("Valider");
	JButton annulR = new JButton ("Annuler");

	//les champs forfaits
	JTextField fieldNbHeuresDispo = new JTextField(20);
	JTextField fieldDateDebutF = new JTextField (20);
	JTextField fieldCreditF = new JTextField (20);
	JTextField fieldDateFinF = new JTextField (20);
	ComboBoxSalle comboBoxSalle = new ComboBoxSalle();
	ComboBoxHeure comboBoxHeure = new ComboBoxHeure();
	ComboBoxValidite comboBoxValidite = new ComboBoxValidite();


	// les boutons forfaits
	JButton createF = new JButton ("Creer");
	JButton updateF = new JButton ("Modifier");

	//Entree entreeSelectionne;

	// constructeur
	public DialogueInformationsClient ( JDialog dialog) throws Exception{

		this.dialog = dialog;

		//On charge la JList Client
		List<Client> lesClients = ClientFactory.getInstance().lister();
		for(Client e : lesClients){
			modelListClient.addElement(e);
		}

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
				"Liste des clients"));
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
		JLabel labelExpirationR = new JLabel ("Expiration : ");
		JLabel labelHoraireR = new JLabel ("Horaire : ");
		JLabel labelSalleR = new JLabel ("Salle : ");
		JLabel labelEtat = new JLabel ("Etat : ");

		//mise en place de l'interdiction d'ecriture dans les jtextfield de reservations
		fieldDateR.setEditable(false);
		fieldExpirationR.setEditable(false);
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

		//on met les labels et les fields r�servations dans des panels
		JPanel lesLabelsR = new JPanel(new GridLayout (5,2));
		lesLabelsR.add (labelDateR);
		lesLabelsR.add (labelExpirationR);
		lesLabelsR.add (labelHoraireR);
		lesLabelsR.add (labelSalleR);
		lesLabelsR.add (labelEtat);
		lesLabelsR.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));
		JPanel lesFieldsR = new JPanel(new GridLayout (5,2));
		lesFieldsR.add (fieldDateR);
		lesFieldsR.add(fieldExpirationR);
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
		pboutonsF.add (updateF);
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
		listReservations.setSelectionMode (ListSelectionModel.SINGLE_SELECTION);

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
		updateF.addActionListener(this);

		// Ajout listSelectionListener
		listClients.addListSelectionListener(this);
		listReservations.addListSelectionListener(this);
		listForfaits.addListSelectionListener(this);


		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o= e.getSource();
		if(o == addC ) {
			try{
				Client client = ClientFactory.getInstance().creer(fieldNom.getText(), fieldPrenom.getText(),fieldNumero.getText(),Integer.parseInt(fieldPtFidelite.getText()));
				modelListClient.addElement(client);
			}catch(ObjetExistant exception){
				JOptionPane.showMessageDialog(dialog, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			} catch (CreationObjetException e1) {
				JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			}


		} else if(o== deleteC) {
			// Supprimer client
			try {
				modelListClient.removeElement(clientSelectionne);
				ClientFactory.getInstance().supprimer(clientSelectionne);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			} catch (ObjetInconnu e1) {
				JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		} else if(o== clearC) {
			// Nettoyer information fields client
			ClearField();
		} else if(o== confR) {
			// Confirm réservation

		} else if(o== annulR) {
			// Annuler réservation
			try {
				ReservationFactory.getInstance().supprimer(reservationSelectionne);
				modelListReservation.removeElement(reservationSelectionne);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			} catch (ObjetInconnu e1) {
				JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}else if(o== createF) {
			// Creation forfait
			Date dateDebut;
			Date dateFin;
			dateDebut = cal.getTime();

			cal.add(Calendar.MONTH, 3);
			dateFin = cal.getTime();
			try {
				Forfait forfait =ForfaitFactory.getInstance().creer(clientSelectionne.getIdClient(),comboBoxHeure.getTextComboBoxHeure(), dateDebut, dateFin, 0.0, Salle.type.valueOf(comboBoxSalle.getTextComboBoxSalle()));
				modelListForfait.addElement(forfait);
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CreationObjetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ObjetExistant e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(o== updateF) {
			// Mise à jour du forfait
			try {
				modelListForfait.removeElement(forfaitSelectionne);
				forfaitSelectionne.setIdClient(forfaitSelectionne.getIdClient());
				forfaitSelectionne.setDateDebut(formatter.parse(fieldDateDebutF.getText()));
				forfaitSelectionne.setDateDebut(formatter.parse(fieldDateDebutF.getText()));
				forfaitSelectionne.setNbHeure(comboBoxHeure.getTextComboBoxHeure());
				ForfaitFactory.getInstance().update(forfaitSelectionne);
				modelListForfait.addElement(forfaitSelectionne);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			} catch (ObjetInconnu e1) {
				JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
			ClearForfait();
			// On récupère le client selectionné
			Client client = (Client) listClients.getSelectedValue();
			clientSelectionne = client;
			fieldNom.setText(client.getNom());
			fieldPrenom.setText(client.getPrenom());
			fieldNumero.setText(client.getNumTel());
			fieldPtFidelite.setText(Integer.toString((client.getPointFidelite())));

			try {
				//On charge la JList reservations
				List<Reservation> lesReservations = clientSelectionne.getListReservations();
				for(Reservation e : lesReservations){
					modelListReservation.addElement(e);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.out.println("sql");

			} catch (ObjetInconnu e1) {
				// TODO Auto-generated catch block
				//				System.out.println(e1.getMessage());
			}
			try{
				//On charge la JList Forfaits
				List<Forfait> lesForfaits = clientSelectionne.getListFofaits();
				for(Forfait e : lesForfaits){
					modelListForfait.addElement(e);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.out.println("sql");

			} catch (ObjetInconnu e1) {
				// TODO Auto-generated catch block
				//				System.out.println(e1.getMessage());
			}


			//On remplit le renderer Reservation
			ListCellRenderer maListReservationsCellRenderer = new ReservationsListCellRenderer();
			listReservations.setCellRenderer(maListReservationsCellRenderer);

			//On remplit le renderer Reservation
			ListCellRenderer maListForfaitsCellRenderer = new ForfaitsListCellRenderer();
			listForfaits.setCellRenderer(maListForfaitsCellRenderer);
		}else if(o==listReservations){
			// Gestion de la JList reservations

			// On récupère la reservation selectionné
			Reservation reservation = (Reservation) listReservations.getSelectedValue();

			//Si il y a une reservation
			if(reservation != null){
				reservationSelectionne = reservation;
				fieldDateR.setText(formatter.format(reservation.getDateDebut()));
				fieldExpirationR.setText(formatter.format(reservation.getDateFin()));
				fieldHoraire.setText(Integer.toString(reservation.getDateDebut().getHours())+"h -> "+Integer.toString(reservation.getDateFin().getHours())+"h" );
				try {
					salle = reservation.getSalle();
				} catch (ObjetInconnu e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fieldSalleR.setText(salle.getDescription());

				try {
					if (reservation.getFacture().isEstPaye()) {
						fieldEtatR.setText("Validé");
					} else {
						fieldEtatR.setText("Non validé");;
					}
				} catch (ObjetInconnu e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
		fieldDateR.setText("");
		fieldExpirationR.setText("");
		fieldHoraire.setText("");
		fieldSalleR.setText("");
		fieldEtatR.setText("");
		fieldCreditF.setText("");
		fieldDateDebutF.setText("");
		fieldDateFinF.setText("");
		fieldNbHeuresDispo.setText("");
	}

	public void ClearReservation(){
		// On vide les JList reservations
		listReservations.removeAll();
		modelListReservation.removeAllElements();
		// On vide les JTextField reservations
		fieldDateR.setText("");
		fieldExpirationR.setText("");
		fieldHoraire.setText("");
		fieldSalleR.setText("");
		fieldEtatR.setText("");
	}

	public void ClearForfait(){
		// On vide les JList forfaits
		listForfaits.removeAll();
		modelListForfait.removeAllElements();
		// On vide les JTextField forfaits
		fieldCreditF.setText("");
		fieldDateDebutF.setText("");
		fieldDateFinF.setText("");
		fieldNbHeuresDispo.setText("");
	}

}

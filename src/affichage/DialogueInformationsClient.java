package affichage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;



public class DialogueInformationsClient extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	JDialog dialog;

	// partie gauche de la fenetre : gestion des entrees
	//les listes des objets
	DefaultListModel modelListEntree = new DefaultListModel();
	JList listClients = new JList (modelListEntree);


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

	JList listReservations =new JList();
	JList listForfaits =new JList();
	
	//les champs réervations
	JTextField fieldDate = new JTextField (20);
	JTextField fieldHoraire = new JTextField(20);
	JTextField fieldSalle = new JTextField(20);
	JTextField fieldEtat = new JTextField(20);

	// les boutons réservations
	JButton confR = new JButton ("Valider");
	JButton annulR = new JButton ("Annuler");
	
	//les champs forfaits
	JTextField fieldID = new JTextField (20);
	JTextField fieldNbHeuresDispo = new JTextField(20);

	// les boutons forfaits
	JButton createF = new JButton ("Creer");
	JButton updateF = new JButton ("Modifier");

	//Entree entreeSelectionne;

	// constructeur
	public DialogueInformationsClient ( JDialog dialog) throws Exception{

		this.dialog = dialog;

		//construction du panel de gauche

		//construction des text field pour saisie ou affichage
		JLabel labelNom = new JLabel ("Nom :");
		JLabel labelPrenom = new JLabel ("Prenom : ");
		JLabel labelNumero = new JLabel ("Numéro : ");
		JLabel labelPtFidelite = new JLabel ("Points de fidélité : ");
		
		

		//on met les labels et les fields dans des panels
		JPanel lesLabelsC = new JPanel(new GridLayout (0,1));
		lesLabelsC.add (labelNom);
		lesLabelsC.add (labelPrenom);
		lesLabelsC.add(labelNumero);
		lesLabelsC.add(labelPtFidelite);
		lesLabelsC.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));
		
		JPanel lesFieldsC = new JPanel(new GridLayout (0,1));
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
		JLabel labelDate = new JLabel ("Date :");
		JLabel labelHoraire = new JLabel ("Horaire :");
		JLabel labelSalle = new JLabel ("Salle : ");
		JLabel labelEtat = new JLabel ("Etat : ");

		//Construction des text field forfait pour saisie ou affichage
		JLabel labelID = new JLabel("ID : ");
		JLabel labelNbHeuresDispo = new JLabel ("Nombre d'heures : ");

		//on met les labels et les fields réservations dans des panels
		JPanel lesLabelsR = new JPanel(new GridLayout (0,1));
		lesLabelsR.add (labelDate);
		lesLabelsR.add (labelHoraire);
		lesLabelsR.add (labelSalle);
		lesLabelsR.add (labelEtat);
		lesLabelsR.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		JPanel lesFieldsR = new JPanel(new GridLayout (0,1));
		lesFieldsR.add (fieldDate);
		lesFieldsR.add(fieldHoraire);
		lesFieldsR.add (fieldSalle);
		lesFieldsR.add(fieldEtat);
		lesFieldsR.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));

		//on met les labels et les fields forfaits dans des panels
		JPanel lesLabelsF = new JPanel(new GridLayout (0,1));
		lesLabelsF.add (labelID);
		lesLabelsF.add (labelNbHeuresDispo);
		lesLabelsF.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));
		JPanel lesFieldsF = new JPanel(new GridLayout (0,1));
		lesFieldsF.add (fieldID);
		lesFieldsF.add(fieldNbHeuresDispo);
		lesFieldsF.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));
		
		// construction d'un panel pour mettre les 2 boutons réservation
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
			
		// TODO: Ajouter labels + fields pour la gestion des forfaits
		
		//on assemble gauche et droite
		setLayout (new BorderLayout());
		add (panelGauche, BorderLayout.WEST);
		add (panelDroite, BorderLayout.EAST);
		setVisible(true);

}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o= e.getSource();
		if(o == addC ) {
			// Ajouter client
		} else if(o== deleteC) {
			// Supprimer client
		} else if(o== clearC) {
			// Nettoyer info fields client
		} else if(o== confR) {
			// Confirm réservation
		} else if(o== annulR) {
			// Annuler réservation
		}else {
			return;
		}
		
	}

}

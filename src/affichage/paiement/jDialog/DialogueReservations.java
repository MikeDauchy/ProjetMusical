package affichage.paiement.jDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import affichage.informationsClient.listCellRenderer.ReservationsListCellRenderer;
import donnees.Client;
import donnees.Facture;
import donnees.reservations.Reservation;
import donnees.salles.Salle;
import exceptions.accesAuDonnees.ObjetInconnu;




public class DialogueReservations extends JPanel implements ListSelectionListener{


	Facture facture;
	Reservation reservationSelectionne;
	Salle salle;


	SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

	JDialog dialog;
	JDialog dialogPaiementReservation;

	
	//partie droite de la fenetre : gestion des reservation
	//la liste des objets
	DefaultListModel modelListReservation = new DefaultListModel();
	JList listReservations = new JList(modelListReservation);

	//les champs réservations
	JTextField fieldDateR = new JTextField (20);
	JTextField fieldNbHeuresR = new JTextField (20);
	JTextField fieldHoraire = new JTextField(20);
	JTextField fieldSalleR = new JTextField(20);
	JTextField fieldEtatR = new JTextField(20);



	// constructeur
	public DialogueReservations (JDialog dialog,Facture facture){
		this.facture=facture;
		
		// On charge l'ensemble des reservations de la facture du client
		loadListReservation(facture);


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

		JPanel formR = new JPanel (new BorderLayout());
		formR.add (lesLabelsR, BorderLayout.WEST);
		formR.add (lesFieldsR, BorderLayout.EAST);

	
		// Ajout ScrollPane reservations
		JScrollPane listReservationsScrollPane = new JScrollPane (listReservations);
		listReservationsScrollPane.setPreferredSize (new Dimension (100,100));
		listReservationsScrollPane.setMinimumSize (new Dimension (200,200));
		listReservationsScrollPane.setBorder (BorderFactory.createTitledBorder (
				BorderFactory.createLineBorder (Color.cyan),
				"Réservations"));
		listReservations.setSelectionMode (ListSelectionModel.SINGLE_SELECTION);

		
		//panel de droite
		JPanel panelDroite=new JPanel();
		panelDroite.setLayout(new BoxLayout(panelDroite, BoxLayout.Y_AXIS));

		// on ajoute la liste et le formulaire
		listReservationsScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelDroite.add(listReservationsScrollPane);
		panelDroite.add(formR);


		//on assemble gauche et droite
		setLayout (new BorderLayout());
		add (panelDroite);
		

		// Ajout listSelectionListener
		listReservations.addListSelectionListener(this);

		setVisible(true);

	}


	@Override
	public void valueChanged(ListSelectionEvent event) {
		Object o= event.getSource();
		if(o==listReservations){
			// Gestion de la JList reservations

			// On récupère la reservation selectionné
			Reservation reservation = (Reservation) listReservations.getSelectedValue();

			//Si il y a une reservation
			if(reservation != null){
				// On récupère la réservation selectionné
				reservationSelectionne = reservation;
				fieldDateR.setText(formatter.format(reservation.getDateDebut()));
				fieldNbHeuresR.setText(Integer.toString(reservation.getNbHeure()));
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
						fieldEtatR.setText("Non validé");
					}
				} catch (ObjetInconnu e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}else {
			return;
		}
	}


	public void loadListReservation(Facture facture){
		try {
			//On charge la JList Réservations
			List<Reservation> lesReservations = facture.getListReservations();
			for(Reservation e : lesReservations){
				modelListReservation.addElement(e);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//On remplit le renderer Réservations
		ListCellRenderer maListReservationsCellRenderer = new ReservationsListCellRenderer();
		listReservations.setCellRenderer(maListReservationsCellRenderer);
	}

	

}

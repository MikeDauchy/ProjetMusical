package affichage.paiement.jDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

import metier.GestionReservation;
import donnees.Client;
import donnees.Forfait;
import donnees.reservations.Reservation;
import exceptions.accesAuDonnees.ObjetInconnu;
import affichage.informationsClient.listCellRenderer.ForfaitsListCellRenderer;

public class DialogueForfaits extends JPanel implements ActionListener, ListSelectionListener{

	private Client client;
	private Reservation reservation;
	private GestionReservation gestReservation = new GestionReservation();
	private JDialog dialog;
	private DefaultListModel modelListForfait = new DefaultListModel();
	private JList listForfaits = new JList(modelListForfait);
	private Forfait forfaitSelectionne;
	private SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

	//les champs forfaits
	private JTextField fieldDateDebutF = new JTextField (20);
	private JTextField fieldCreditF = new JTextField (20);
	private JTextField fieldDateFinF = new JTextField (20);
	private JTextField fieldSalleF = new JTextField(20);


	// les boutons forfaits
	private JButton validF = new JButton ("Valider");
	private JButton annulF = new JButton ("Annuler");

	public DialogueForfaits(JDialog dialog,Client client,Reservation reservation){

		this.client=client;
		this.reservation = reservation;
		this.dialog= dialog;

		// On charge l'ensemble des forfaits du client
		loadListForfaits(client);

		//Construction des text field forfait pour saisie ou affichage
		JLabel labelSalleF = new JLabel ("Salle : ");
		JLabel labelCreditF = new JLabel("Credit : ");
		JLabel labelDateDebutF = new JLabel ("Date début : ");
		JLabel labelDateFinF = new JLabel ("Date fin : ");

		fieldSalleF.setEditable(false);
		fieldDateDebutF.setEditable(false);
		fieldCreditF.setEditable(false);
		fieldDateFinF.setEditable(false);

		//on met les labels et les fields forfaits dans des panels
		JPanel lesLabelsF = new JPanel(new GridLayout (6,2));
		lesLabelsF.add(labelSalleF);
		lesLabelsF.add (labelCreditF);
		lesLabelsF.add (labelDateDebutF);
		lesLabelsF.add (labelDateFinF);
		lesLabelsF.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));
		JPanel lesFieldsF = new JPanel(new GridLayout (6,2));
		lesFieldsF.add(fieldSalleF);
		lesFieldsF.add (fieldCreditF);
		lesFieldsF.add(fieldDateDebutF);
		lesFieldsF.add(fieldDateFinF);
		lesFieldsF.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));

		//construction d'un panel pour mettre les 2 boutons forfait
		JPanel pboutonsF = new JPanel(new GridLayout(1,0));
		pboutonsF.add (validF);
		pboutonsF.add(annulF);
		pboutonsF.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		JPanel formF = new JPanel (new BorderLayout());
		formF.add (lesLabelsF, BorderLayout.WEST);
		formF.add (lesFieldsF, BorderLayout.EAST);
		formF.add (pboutonsF, BorderLayout.SOUTH);

		//Ajout ScrollPane forfaits
		JScrollPane listForfaitsScrollPane = new JScrollPane (listForfaits);
		listForfaitsScrollPane.setPreferredSize (new Dimension (100,100));
		listForfaitsScrollPane.setMinimumSize (new Dimension (100,100));
		listForfaitsScrollPane.setBorder (BorderFactory.createTitledBorder (
				BorderFactory.createLineBorder (Color.cyan),
				"Forfaits"));
		listForfaits.setSelectionMode (ListSelectionModel.SINGLE_SELECTION);

		listForfaitsScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

		//panel de droite
		JPanel panelDroite=new JPanel();
		panelDroite.setLayout(new BoxLayout(panelDroite, BoxLayout.Y_AXIS));

		panelDroite.add(listForfaitsScrollPane);
		panelDroite.add(formF);

		//on assemble gauche et droite
		setLayout (new BorderLayout());

		add (panelDroite);

		validF.addActionListener(this);
		annulF.addActionListener(this);
		validF.setEnabled(false);

		listForfaits.addListSelectionListener(this);

		setVisible(true);
		dialog.setResizable(false);

	}



	@Override
	public void actionPerformed(ActionEvent e) {
		Object o= e.getSource();
		if(o == validF ) {
			Forfait forfait = (Forfait) listForfaits.getSelectedValue();
			try {
				if(forfait.getTypeSalle() != reservation.getSalle().getTypeSalle()){
					JOptionPane.showMessageDialog(dialog, "Le type du forfait ne correspond pas au type de la salle", "Erreur", JOptionPane.ERROR_MESSAGE);
				}else{
					gestReservation.paiementReservationForfait(reservation, forfait);
					dialog.setVisible(false);
				}
			} catch (ObjetInconnu e1) {
				JOptionPane.showMessageDialog(dialog, "Aucun forfait(s) disponible", "Erreur", JOptionPane.ERROR_MESSAGE);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(dialog, "Erreur SQL", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}else if (o==annulF){
			dialog.setVisible(false);
		}else{
			return;
		}

	}


	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		Forfait forfait = (Forfait) listForfaits.getSelectedValue();
		if(forfait !=null){
			validF.setEnabled(true);
			forfaitSelectionne = forfait;
			fieldSalleF.setText(forfaitSelectionne.getTypeSalle().toString());
			fieldCreditF.setText(Integer.toString(forfaitSelectionne.getNbHeure())+"h");
			fieldDateDebutF.setText(formatter.format(forfaitSelectionne.getDateDebut()));
			fieldDateFinF.setText(formatter.format(forfaitSelectionne.getDateFin()));
		}

	}
	
	public void loadListForfaits(Client client){
		//On nettoie la Jlist et les JtextFields Forfaits
		try{
			//On charge la JList Forfaits
			List<Forfait> lesForfaits = client.getListFofaits();
			for(Forfait e : lesForfaits){
				modelListForfait.addElement(e);
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(dialog, "Erreur SQL", "Erreur", JOptionPane.ERROR_MESSAGE);
		} catch (ObjetInconnu e1) {
			JOptionPane.showMessageDialog(dialog, "Aucun forfait(s) disponible", "Erreur", JOptionPane.ERROR_MESSAGE);
		}

		//On remplit le renderer Forfait
		ListCellRenderer maListForfaitsCellRenderer = new ForfaitsListCellRenderer();
		listForfaits.setCellRenderer(maListForfaitsCellRenderer);
	}

}

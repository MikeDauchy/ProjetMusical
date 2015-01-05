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
import affichage.informationsClient.jComboBox.ComboBoxHeure;
import affichage.informationsClient.jComboBox.ComboBoxSalle;
import affichage.informationsClient.jComboBox.ComboBoxValidite;
import affichage.informationsClient.listCellRenderer.ForfaitsListCellRenderer;

public class DialogueForfaits extends JPanel implements ActionListener, ListSelectionListener{

	Client client;
	Reservation reservation;
	GestionReservation gestReservation = new GestionReservation();
	JDialog dialog;
	DefaultListModel modelListForfait = new DefaultListModel();
	JList listForfaits = new JList(modelListForfait);
	Forfait forfaitSelectionne;
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

	//les champs forfaits
	JTextField fieldNbHeuresDispo = new JTextField(20);
	JTextField fieldDateDebutF = new JTextField (20);
	JTextField fieldCreditF = new JTextField (20);
	JTextField fieldDateFinF = new JTextField (20);
	ComboBoxSalle comboBoxSalle = new ComboBoxSalle();
	ComboBoxHeure comboBoxHeure = new ComboBoxHeure();
	ComboBoxValidite comboBoxValidite = new ComboBoxValidite();

	// les boutons forfaits
	JButton validF = new JButton ("Valider");

	public DialogueForfaits(JDialog dialog,Client client,Reservation reservation){

		this.client=client;
		this.reservation = reservation;
		this.dialog= dialog;

		try {
			List<Forfait> lesForfaits = this.client.getListFofaits();
			for(Forfait e : lesForfaits){
				modelListForfait.addElement(e);
			}
		} catch (ObjetInconnu e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ListCellRenderer maListForfaitsCellRenderer = new ForfaitsListCellRenderer();
		listForfaits.setCellRenderer(maListForfaitsCellRenderer);

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

		//construction d'un panel pour mettre les 2 boutons forfait
		JPanel pboutonsF = new JPanel(new GridLayout(1,0));
		pboutonsF.add (validF);
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

		listForfaits.addListSelectionListener(this);

		setVisible(true);

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
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else{
			return;
		}

	}


	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		Forfait forfait = (Forfait) listForfaits.getSelectedValue();
		if(forfait !=null){
			forfaitSelectionne = forfait;
			comboBoxHeure.SelectItem(Integer.toString(forfaitSelectionne.getNbHeure()));
			comboBoxSalle.SelectItem(forfaitSelectionne.getTypeSalle().toString());
			fieldCreditF.setText(Integer.toString(forfaitSelectionne.getNbHeure())+"h");
			fieldDateDebutF.setText(formatter.format(forfaitSelectionne.getDateDebut()));
			fieldDateFinF.setText(formatter.format(forfaitSelectionne.getDateFin()));
		}

	}

}

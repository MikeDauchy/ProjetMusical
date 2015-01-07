package affichage.paiement.jDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import metier.GestionReservation;
import affichage.consulter_reservations.panel.PanelAgenda;
import donnees.Client;
import donnees.reservations.Reservation;
import exceptions.accesAuDonnees.ObjetInconnu;
import exceptions.metier.PointsFideliteInsuffisantException;
import fabriques.donnes.ReservationFactory;


public class DialoguePaiement extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	private Dialog dialog;
	private JDialog dialogPaiementForfaits;
	private JDialog dialogDetailsFacture;
	private JCheckBox paiementCB, paiementForfait, paiementPtFidelite;
	private JButton validPaiement = new JButton("Valider");
	private JButton detailFacture = new JButton("Détails");
	private Reservation reservation;
	private GestionReservation gestReserv = new GestionReservation();


	public DialoguePaiement(Dialog dialog, Reservation reservation) throws SQLException, ObjetInconnu {
		super();
		this.dialog = dialog;
		setLayout( new BorderLayout());
		this.reservation=reservation;
		JLabel labelDateR = new JLabel ("Vous allez régler l'ensemble des réservations associé à la facture de cet réservation : ");
		
		JPanel detailsF = new JPanel (new BorderLayout());
		detailsF.add (labelDateR, BorderLayout.WEST);
		detailsF.add (detailFacture, BorderLayout.EAST);
		detailsF.setBackground(new  Color(26, 162, 189));
		
		//Type de Paiement
		JPanel panelPaiement = new JPanel();
		panelPaiement.setBorder(BorderFactory.createTitledBorder("Types de paiement"));
		panelPaiement.setPreferredSize(new Dimension(440, 60));
		paiementCB = new JCheckBox("Carte Bleu");
		paiementForfait = new JCheckBox("Forfait");
		paiementPtFidelite = new JCheckBox("Points fidelité");
		ButtonGroup bg = new ButtonGroup();
		bg.add(paiementCB);
		bg.add(paiementForfait);
		bg.add(paiementPtFidelite);
		panelPaiement.add(paiementCB);
		panelPaiement.add(paiementForfait);
		panelPaiement.add(paiementPtFidelite);

		JPanel content = new JPanel(new BorderLayout());
		content.add(detailsF,BorderLayout.NORTH);
		content.add(panelPaiement,BorderLayout.CENTER);
		content.add(validPaiement,BorderLayout.SOUTH);

		add(content, BorderLayout.CENTER);

		//Ajout de l'action Listener sur le bouton Valider
		validPaiement.addActionListener(this);
		detailFacture.addActionListener(this);
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setResizable(false);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object o=e.getSource();
		if(o == validPaiement){
			if(paiementCB.isSelected()){
				try {
					gestReserv.paiementReservationCB(reservation);
					dialog.setVisible(false);
				} catch (ObjetInconnu e1) {
					JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);

				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(dialog, "Erreur SQL", "Erreur", JOptionPane.ERROR_MESSAGE);
				}

			}else if(paiementForfait.isSelected()){
				//JDialog pour le paiements des reservations non validé
				dialogPaiementForfaits = new JDialog (dialog, "Choix du forfait",true);
				try {
					dialogPaiementForfaits.getContentPane().add(new DialogueForfaits(dialogPaiementForfaits,reservation.getFacture().getClient(),reservation), BorderLayout.CENTER);
					dialogPaiementForfaits.setLocationRelativeTo(dialog);
					dialogPaiementForfaits.pack();
					dialogPaiementForfaits.setVisible(true);
					dialog.setVisible(false);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(dialog, "Erreur SQL", "Erreur", JOptionPane.ERROR_MESSAGE);
				} catch (ObjetInconnu e1) {
					JOptionPane.showMessageDialog(dialog, "Aucun forfait(s) disponible", "Erreur", JOptionPane.ERROR_MESSAGE);

				}

			}else if(paiementPtFidelite.isSelected()){
				try {
					gestReserv.paiementReservationPtsFidelite(reservation);
					dialog.setVisible(false);
				} catch (ObjetInconnu e1) {
					JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(dialog, "Erreur SQL", "Erreur", JOptionPane.ERROR_MESSAGE);
				} catch (PointsFideliteInsuffisantException e1) {
					JOptionPane.showMessageDialog(dialog, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);

				}
			}
		}else if(o==detailFacture){

				try {
					dialogDetailsFacture = new JDialog (dialog, "Liste des réservations",true);
					dialogDetailsFacture.getContentPane().add(new DialogueReservations(dialogDetailsFacture,reservation.getFacture()));
					dialogDetailsFacture.setLocationRelativeTo(dialog);
					dialogDetailsFacture.pack();
					dialogDetailsFacture.setVisible(true);
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
}

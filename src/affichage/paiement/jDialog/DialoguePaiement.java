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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import affichage.consulter_reservations.panel.PanelAgenda;
import donnees.reservations.Reservation;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.donnes.ReservationFactory;


public class DialoguePaiement extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	private Dialog dialog;
	private JCheckBox paiementCB, paiementForfait, paiementPtFidelite;
	private JButton validPaiement = new JButton("Valider");


	public DialoguePaiement(Dialog dialog) {
		super();
		this.dialog = dialog;
		setLayout( new BorderLayout());


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
		content.add(panelPaiement,BorderLayout.NORTH);
		content.add(validPaiement,BorderLayout.SOUTH);

		add(content, BorderLayout.CENTER);

		//Ajout de l'action Listener sur le bouton Valider
		validPaiement.addActionListener(this);
		dialog.pack();
		dialog.setLocationRelativeTo(null);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object o=e.getSource();
		if(o == validPaiement){
			if(paiementCB.isSelected()){

			}else if(paiementForfait.isSelected()){

			}else if(paiementPtFidelite.isSelected()){

			}else{ 
				return;
			}
		}else{
			return;
		}
	}
}

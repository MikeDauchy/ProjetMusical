package affichage.consulter_reservations;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import affichage.consulter_reservations.panel.PanelAgenda;
import donnees.reservations.Reservation;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.donnes.ReservationFactory;


public class DialogueConsulterReservation extends JPanel{

	private static final long serialVersionUID = 1L;

	private Dialog dialog;

	JButton btnJourPrec = new JButton("Jour précédent");
	JButton btnJourSuiv = new JButton("Jour suivant");
	
	JLabel labelTitre = new JLabel("Consultation des reservations", SwingConstants.CENTER);
	JPanel panelEnTete = new JPanel();
	JPanel panelContenu = new JPanel();
	JPanel panelAgenda;
	
	DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL,
			Locale.getDefault());
	
	Calendar dateDeReference;

	public DialogueConsulterReservation(Dialog dialog) throws HeadlessException, SQLException, ObjetInconnu {
		super();
		this.dialog = dialog;
		setLayout( new BorderLayout());
		
		btnJourPrec.setBackground(new Color(194, 194, 194));
		btnJourSuiv.setBackground(new Color(194, 194, 194));
		
		Date dateRef = new Date();
		dateRef.setHours(1);
		dateRef.setMinutes(0);
		dateRef.setSeconds(0);
		dateDeReference = new java.util.GregorianCalendar();
		dateDeReference.setTime(dateRef);
		
		//creation de l'entete
		creationListenerBtnPrec();
		creationListenerBtnSuiv();
		labelTitre.setText(dateFormat.format(dateDeReference.getTime()));
		labelTitre.setForeground(Color.white);
		panelEnTete.setLayout(new GridLayout(1, 3));
		panelEnTete.add(btnJourPrec);
		panelEnTete.add(labelTitre);
		panelEnTete.add(btnJourSuiv);
		panelEnTete.setBackground(new Color(52, 52, 52));
		//
		add(panelEnTete, BorderLayout.NORTH);
		
		
		
		//Chargement du contenu du planning
		Calendar dateRefPlusUn = new java.util.GregorianCalendar();
		dateRefPlusUn.setTime(dateRef);
		dateRefPlusUn.add(Calendar.DATE, 1);
				
		List<Reservation> listReservation;
		try {
			listReservation = ReservationFactory.getInstance().listerByDates(dateDeReference.getTime(), dateRefPlusUn.getTime());
		} catch (ObjetInconnu e) {
			listReservation = new ArrayList<Reservation>();
		}
		panelAgenda = new PanelAgenda(listReservation, (Calendar)dateDeReference.clone(), dialog);
		panelContenu.add(panelAgenda);
		panelContenu.setBackground(new Color(52, 52, 52));
		//
		add(panelContenu, BorderLayout.CENTER);
		

		dialog.pack();
		dialog.setLocationRelativeTo(null);
	}
	
	private void recompacter(){
		this.revalidate();
		panelContenu.revalidate();
		dialog.pack();
		dialog.setLocationRelativeTo(null);
	}
	
	private void creationListenerBtnPrec(){
		btnJourPrec.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ajouterJourAgenda(-1);
			}
		});
	}
	
	private void creationListenerBtnSuiv(){
		btnJourSuiv.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ajouterJourAgenda(1);
			}
		});
	}
	
	private void ajouterJourAgenda(int nbJour){
		//Chargement du contenu du planning
		dateDeReference.add(Calendar.DATE, nbJour*1);
		
		Calendar lundi = (Calendar)dateDeReference.clone();
		
		Calendar dimanche = (Calendar)dateDeReference.clone();
		dimanche.add(Calendar.DATE, 1);
				
		List<Reservation> listReservation = null;
		try {
			listReservation = ReservationFactory.getInstance().listerByDates(lundi.getTime(), dimanche.getTime());
		} catch (ObjetInconnu oIe) {
			listReservation = new ArrayList<Reservation>();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			panelContenu.removeAll();
			panelAgenda = new PanelAgenda(listReservation, lundi, dialog);
			panelContenu.add(panelAgenda);
		} catch (ObjetInconnu e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//
		labelTitre.setText(dateFormat.format(dateDeReference.getTime()));
		recompacter();
	}


}

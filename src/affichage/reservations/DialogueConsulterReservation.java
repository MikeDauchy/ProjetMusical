package affichage.reservations;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import affichage.reservations.panel.PanelAgenda;
import donnees.reservations.Reservation;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.donnes.ReservationFactory;


public class DialogueConsulterReservation extends JPanel{

	private static final long serialVersionUID = 1L;

	private Dialog dialog;

	JButton btnSemainePrec = new JButton("Semaine précédente");
	JButton btnSemaineSuiv = new JButton("Semaine suivante");
	
	JLabel labelTitre = new JLabel("Consultation des reservations", SwingConstants.CENTER);
	JPanel panelEnTete = new JPanel();
	JPanel panelContenu = new JPanel();
	JPanel panelAgenda;
	
	Calendar dateDeReference;

	public DialogueConsulterReservation(Dialog dialog) throws HeadlessException, SQLException, ObjetInconnu {
		super();
		this.dialog = dialog;
		setLayout( new BorderLayout());
		
		
		//creation de l'entete
		creationListenerBtnPrec();
		creationListenerBtnSuiv();
		labelTitre.setForeground(Color.blue);
		panelEnTete.setLayout(new GridLayout(1, 3));
		panelEnTete.add(btnSemainePrec);
		panelEnTete.add(labelTitre);
		panelEnTete.add(btnSemaineSuiv);
		//
		add(panelEnTete, BorderLayout.NORTH);
		
		
		
		//Chargement du contenu du planning
		dateDeReference = new java.util.GregorianCalendar();
		dateDeReference.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		Calendar dimancheDeReference = new java.util.GregorianCalendar();
		dimancheDeReference.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				
		List<Reservation> listReservation;
		try {
			listReservation = ReservationFactory.getInstance().listerByDates(dateDeReference.getTime(), dimancheDeReference.getTime());
		} catch (ObjetInconnu e) {
			listReservation = new ArrayList<Reservation>();
		}
		panelAgenda = new PanelAgenda(listReservation, (Calendar)dateDeReference.clone(), dialog);
		panelContenu.add(panelAgenda);
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
		btnSemainePrec.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ajouterSemaineAgenda(-1);
			}
		});
	}
	
	private void creationListenerBtnSuiv(){
		btnSemaineSuiv.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ajouterSemaineAgenda(1);
			}
		});
	}
	
	private void ajouterSemaineAgenda(int nbSemaine){
		//Chargement du contenu du planning
		dateDeReference.add(Calendar.DATE, nbSemaine*7);
		
		Calendar lundi = (Calendar)dateDeReference.clone();
		
		Calendar dimanche = (Calendar)dateDeReference.clone();
		dimanche.add(Calendar.DATE, 7);
				
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
		recompacter();
	}


}

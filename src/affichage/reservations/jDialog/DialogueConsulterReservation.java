package affichage.reservations.jDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import affichage.reservations.jframe.PanelAgenda;
import donnees.reservations.Reservation;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.donnes.ReservationFactory;


public class DialogueConsulterReservation extends JPanel{

	private static final long serialVersionUID = 1L;

	private Dialog dialog;
	private PanelAgenda panelAgenda;

	JLabel labelTitre = new JLabel("Consultation des reservations", SwingConstants.CENTER);

	public DialogueConsulterReservation(Dialog dialog) throws HeadlessException, SQLException, ObjetInconnu {
		super();
		this.setSize(800, 1000);
		this.dialog = dialog;
		setLayout( new BorderLayout());
	
		Calendar lundiDeReference = new java.util.GregorianCalendar();
		lundiDeReference.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		Calendar dimancheDeReference = new java.util.GregorianCalendar();
		lundiDeReference.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		lundiDeReference.add(Calendar.DATE, 7);
				
//		List<Reservation> listReservation = ReservationFactory.getInstance().listerByDates(lundiDeReference.getTime(), dimancheDeReference.getTime());
		panelAgenda = new PanelAgenda(null, lundiDeReference);
		
		add(labelTitre, BorderLayout.NORTH);
		add(panelAgenda, BorderLayout.CENTER);
		labelTitre.setForeground(Color.blue);

		setVisible(true);
	}


}

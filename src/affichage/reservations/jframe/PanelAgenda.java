package affichage.reservations.jframe;

import java.awt.GridLayout;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import affichage.reservations.panel.HeurePanel;
import donnees.reservations.Reservation;

public class PanelAgenda extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelAgenda(List<Reservation> reservations, Calendar dateDeReference) {

		super();
		GridLayout gl = new GridLayout(16, 7);
		gl.setHgap(10);
		this.setLayout(gl);
		// On ajoute le bouton au content pane de la JFrame

		// Calendar maDate = new java.util.GregorianCalendar();
		// maDate.setTime(date);
		// // On se positionne sur le Lundi de la semaine courante :
		// maDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// // Puis on ajoute 7 jours :
		// maDate.add(Calendar.DATE, 7);
		// maDate.getTime();

		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL,
				Locale.getDefault());
		Date date;
		// On se positionne sur le Lundi de la semaine courante :
		dateDeReference.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		dateDeReference.add(Calendar.DATE, 1);

		date = new Date(dateDeReference.getTimeInMillis());
		String mardi = dateFormat.format(date);
		dateDeReference.add(Calendar.DATE, 1);

		date = new Date(dateDeReference.getTimeInMillis());
		String mercredi = dateFormat.format(date);
		dateDeReference.add(Calendar.DATE, 1);

		date = new Date(dateDeReference.getTimeInMillis());
		String jeudi = dateFormat.format(date);
		dateDeReference.add(Calendar.DATE, 1);

		date = new Date(dateDeReference.getTimeInMillis());
		String vendredi = dateFormat.format(date);
		dateDeReference.add(Calendar.DATE, 1);

		date = new Date(dateDeReference.getTimeInMillis());
		String samedi = dateFormat.format(date);
		dateDeReference.add(Calendar.DATE, 1);

		date = new Date(dateDeReference.getTimeInMillis());
		String dimanche = dateFormat.format(date);

		String[] joursDeLaSemaine = { "heures", mardi, mercredi, jeudi,
				vendredi, samedi, dimanche };

		for (int lignes = 0; lignes != 16; lignes++) {
			for (int colonnes = 0; colonnes != 7; colonnes++) {
				if (lignes == 0) {
					this.add(new JLabel(joursDeLaSemaine[colonnes],
							SwingConstants.CENTER));
				} else if (colonnes == 0 && lignes > 0) {
					this.add(new JLabel(8 + lignes + "", SwingConstants.CENTER));
				} else {
					this.add(new HeurePanel());
				}
			}
		}
	}

	private boolean isJourTravaille(Calendar p_date) {
		// On constitue la liste des jours fériés
		final List<Calendar> joursFeries = new ArrayList<Calendar>();
		// On recherche les jours fériés de l'année de la date en paramètre
		final Calendar jourFerie = (Calendar) p_date.clone();
		jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.JANUARY, 1);
		joursFeries.add((Calendar) jourFerie.clone());
		jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.MAY, 1);
		joursFeries.add((Calendar) jourFerie.clone());
		jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.MAY, 8);
		joursFeries.add((Calendar) jourFerie.clone());
		jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.JULY, 14);
		joursFeries.add((Calendar) jourFerie.clone());
		jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.AUGUST, 15);
		joursFeries.add((Calendar) jourFerie.clone());
		jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.NOVEMBER, 1);
		joursFeries.add((Calendar) jourFerie.clone());
		jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.NOVEMBER, 11);
		joursFeries.add((Calendar) jourFerie.clone());
		jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.DECEMBER, 25);
		joursFeries.add((Calendar) jourFerie.clone());

		// Calcul du jour de pâques (algorithme de Oudin (1940))
		// Calcul du nombre d'or - 1
		final int intGoldNumber = p_date.get(Calendar.YEAR) % 19;
		// Année divisé par cent
		final int intAnneeDiv100 = (int) (p_date.get(Calendar.YEAR) / 100);
		// intEpacte est = 23 - Epacte (modulo 30)
		final int intEpacte = (intAnneeDiv100 - intAnneeDiv100 / 4
				- (8 * intAnneeDiv100 + 13) / 25 + (19 * intGoldNumber) + 15) % 30;
		// Le nombre de jours à partir du 21 mars
		// pour atteindre la pleine lune Pascale
		final int intDaysEquinoxeToMoonFull = intEpacte
				- (intEpacte / 28)
				* (1 - (intEpacte / 28) * (29 / (intEpacte + 1))
						* ((21 - intGoldNumber) / 11));
		// Jour de la semaine pour la pleine lune Pascale (0=dimanche)
		final int intWeekDayMoonFull = (p_date.get(Calendar.YEAR)
				+ p_date.get(Calendar.YEAR) / 4 + intDaysEquinoxeToMoonFull + 2
				- intAnneeDiv100 + intAnneeDiv100 / 4) % 7;
		// Nombre de jours du 21 mars jusqu'au dimanche de ou
		// avant la pleine lune Pascale (un nombre entre -6 et 28)
		final int intDaysEquinoxeBeforeFullMoon = intDaysEquinoxeToMoonFull
				- intWeekDayMoonFull;
		// mois de pâques
		final int intMonthPaques = 3 + (intDaysEquinoxeBeforeFullMoon + 40) / 44;
		// jour de pâques
		final int intDayPaques = intDaysEquinoxeBeforeFullMoon + 28 - 31
				* (intMonthPaques / 4);
		// lundi de pâques
		jourFerie.set(p_date.get(Calendar.YEAR), intMonthPaques - 1,
				intDayPaques + 1);
		final Calendar lundiDePaque = (Calendar) jourFerie.clone();
		joursFeries.add(lundiDePaque);
		// Ascension
		final Calendar ascension = (Calendar) lundiDePaque.clone();
		ascension.add(Calendar.DATE, 38);
		joursFeries.add(ascension);
		// Pentecote
		final Calendar lundiPentecote = (Calendar) lundiDePaque.clone();
		lundiPentecote.add(Calendar.DATE, 48);
		joursFeries.add(lundiPentecote);
		if (joursFeries.contains(p_date)
				|| p_date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
				|| p_date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return false;
		}
		return true;
	}
}
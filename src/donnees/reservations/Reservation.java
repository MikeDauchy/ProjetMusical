package donnees.reservations;

import java.sql.SQLException;
import java.util.Date;

import donnees.Facture;
import donnees.salles.Salle;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.donnes.FactureFactory;
import fabriques.donnes.SalleFactory;


public abstract class Reservation {
	
	protected int idReservation;
	protected int idFacture;
	protected int idSalle;
	protected int nbHeure;
	protected Date dateDebut;
	protected Date dateFin;

	public int getNbHeure() {
		return nbHeure;
	}

	public void setNbHeure(int nbHeure) {
		this.nbHeure = nbHeure;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public int getIdReservation() {
		return idReservation;
	}

	public void setIdReservation(int idReservation) {
		this.idReservation = idReservation;
	}

	public int getIdFacture() {
		return idFacture;
	}

	public void setIdFacture(int idFacture) {
		this.idFacture = idFacture;
	}
	
	public Facture getFacture() throws ObjetInconnu, SQLException{
		return FactureFactory.getInstance().rechercherByIdFacture(idFacture);
	}

	public int getIdSalle() {
		return idSalle;
	}

	public void setIdSalle(int idSalle) {
		this.idSalle = idSalle;
	}
	
	public Salle getSalle() throws ObjetInconnu, SQLException {
		return SalleFactory.getInstance().rechercherByIdSalle(idSalle);
	}

	public abstract double getPrix() throws ObjetInconnu, SQLException;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateDebut == null) ? 0 : dateDebut.hashCode());
		result = prime * result + ((dateFin == null) ? 0 : dateFin.hashCode());
		result = prime * result + idSalle;
		result = prime * result + nbHeure;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservation other = (Reservation) obj;
		if (dateDebut == null) {
			if (other.dateDebut != null)
				return false;
		} else if (dateDebut.getYear() != other.dateDebut.getYear()
				&& dateDebut.getMonth() != other.dateDebut.getMonth()
				&& dateDebut.getDay() != other.dateDebut.getDay())
			return false;
		if (dateFin == null) {
			if (other.dateFin != null)
				return false;
		} else if (dateFin.getYear() != other.dateFin.getYear()
				&& dateFin.getMonth() != other.dateFin.getMonth()
				&& dateFin.getDay() != other.dateFin.getDay())
			return false;
		if (idSalle != other.idSalle)
			return false;
		if (nbHeure != other.nbHeure)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reservation [idReservation=" + idReservation + ", idFacture="
				+ idFacture + ", idSalle=" + idSalle + ", nbHeure=" + nbHeure
				+ ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + "]";
	}
}

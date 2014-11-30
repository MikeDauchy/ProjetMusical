package donnees;

import java.sql.SQLException;
import java.util.Date;

import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.donnes.ClientFactory;

public class Forfait {

	private int idForfait;
	private int idClient;
	private int nbHeure;
	private Date dateDebut;
	private Date dateFin;
	private double montant;

	public int getIdForfait() {
		return idForfait;
	}

	public void setIdForfait(int idFacture) {
		this.idForfait = idFacture;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public Client getClient() throws ObjetInconnu, SQLException {
		return ClientFactory.getInstance().rechercherById(idClient);
	}

	public void setClient(Client client) {
		this.idClient = client.getIdClient();
	}

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

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateDebut == null) ? 0 : dateDebut.hashCode());
		result = prime * result + ((dateFin == null) ? 0 : dateFin.hashCode());
		result = prime * result + idClient;
		long temp;
		temp = Double.doubleToLongBits(montant);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + nbHeure;
		return result;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Forfait other = (Forfait) obj;
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
		if (idClient != other.idClient)
			return false;
		if (Double.doubleToLongBits(montant) != Double
				.doubleToLongBits(other.montant))
			return false;
		if (nbHeure != other.nbHeure)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Forfait [idForfait=" + idForfait + ", idClient=" + idClient
				+ ", nbHeure=" + nbHeure + ", dateDebut=" + dateDebut
				+ ", dateFin=" + dateFin + ", montant=" + montant + "]";
	}

}

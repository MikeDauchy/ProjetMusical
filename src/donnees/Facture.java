package donnees;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import donnees.reservations.Reservation;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.donnes.ClientFactory;
import fabriques.donnes.ReservationFactory;

public class Facture {

	private int idFacture;
	private int idClient;
	private boolean estPaye;

	public int getIdFacture() {
		return idFacture;
	}

	public void setIdFacture(int idFacture) {
		this.idFacture = idFacture;
	}

	public int getIdClient() {
		return idClient;
	}

	public boolean isEstPaye() {
		return estPaye;
	}

	public void setEstPaye(boolean estPaye) {
		this.estPaye = estPaye;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public Client getClient() throws SQLException {
		try {
			return ClientFactory.getInstance().rechercherById(idClient);
		} catch (ObjetInconnu e) {
			return null;
		}
	}

	public void setClient(Client client) {
		this.idClient = client.getIdClient();
	}

	public List<Reservation> getListReservations() throws SQLException {
		try {
			return ReservationFactory.getInstance()
					.listerByIdFacture(idFacture);
		} catch (ObjetInconnu e) {
			return new ArrayList<Reservation>();
		}
	}

	public double getMontant() throws SQLException {
		double montant = 0;
		try {
			for (Reservation reservation : getListReservations()) {
				montant += reservation.getPrix();
			}
		} catch (ObjetInconnu e) {
			return montant;
		}
		return montant;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (estPaye ? 1231 : 1237);
		result = prime * result + idClient;
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
		Facture other = (Facture) obj;
		if (estPaye != other.estPaye)
			return false;
		if (idClient != other.idClient)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Facture [idFacture=" + idFacture + ", idClient=" + idClient
				+ ", estPaye=" + estPaye + "]";
	}

}

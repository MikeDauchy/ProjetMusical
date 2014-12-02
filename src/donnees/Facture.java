package donnees;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import donnees.reservations.Reservation;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.donnes.ClientFactory;

public class Facture {

	private int idFacture;
	private int idClient;
	private boolean estPaye;
	private List<Reservation> listReservations = new ArrayList<Reservation>();

	public int getIdFacture() {
		return idFacture;
	}

	public void setIdFacture(int idFacture) {
		this.idFacture = idFacture;
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

	public List<Reservation> getListReservations() {
		return listReservations;
	}

	public void setListReservations(List<Reservation> listReservations) {
		this.listReservations = listReservations;
	}

	public double getMontant() {
		// TODO:A coder !
		return -1;
	}

	public boolean isEstPaye() {
		return estPaye;
	}

	public void setEstPaye(boolean estPaye) {
		this.estPaye = estPaye;
	}

}

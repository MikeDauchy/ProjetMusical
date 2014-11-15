package donnees;

import java.util.ArrayList;
import java.util.List;

import donnees.reservations.Reservation;


public class Facture {

	private Client client;
	private List<Reservation> listReservations = new ArrayList<Reservation>();
	private boolean estPaye;
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public List<Reservation> getListReservations() {
		return listReservations;
	}
	public void setListReservations(List<Reservation> listReservations) {
		this.listReservations = listReservations;
	}
	public double getMontant() {
//		TODO:A coder !
		return -1;
	}
	
	public boolean isEstPaye() {
		return estPaye;
	}
	public void setEstPaye(boolean estPaye) {
		this.estPaye = estPaye;
	}

}

package donnees;

import java.util.ArrayList;
import java.util.List;

import donnees.reservations.Reservation;

public class Client {

	private int idClient;
	private String nom;
	private String prenom;
	private String numTel;
	private int nbPointFidelite;
	private List<Forfait> listForfait = new ArrayList<Forfait>();
	private List<Reservation> listReservations = new ArrayList<Reservation>();
	private List<Facture> listFactures = new ArrayList<Facture>();
	
	public Client(){
		super();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNumTel() {
		return numTel;
	}

	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}

	public int getPointFidelite() {
		return nbPointFidelite;
	}

	public void setPointFidelite(int pointFidelite) {
		this.nbPointFidelite = pointFidelite;
	}
	
	public void addForfait(Forfait forfait){
		listForfait.add(forfait);
	}
	
	public void addReservation(Reservation reservation){
		listReservations.add(reservation);
	}
	
	public void addFacture(Facture facture){
		listFactures.add(facture);
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nbPointFidelite;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((numTel == null) ? 0 : numTel.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
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
		Client other = (Client) obj;
		if (nbPointFidelite != other.nbPointFidelite)
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (numTel == null) {
			if (other.numTel != null)
				return false;
		} else if (!numTel.equals(other.numTel))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Client [idClient=" + idClient + ", nom=" + nom + ", prenom="
				+ prenom + ", numTel=" + numTel + ", nbPointFidelite="
				+ nbPointFidelite + "]";
	}
	
	
}

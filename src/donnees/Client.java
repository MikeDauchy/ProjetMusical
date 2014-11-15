package donnees;

import java.util.ArrayList;
import java.util.List;

import donnees.reservations.Reservation;

public class Client {

	private String nom;
	private String prenom;
	private String numTel;
	private int nbPointFidelite;
	private List<Forfait> listForfait = new ArrayList<Forfait>();
	private List<Reservation> listReservations = new ArrayList<Reservation>();
	private List<Facture> listFactures = new ArrayList<Facture>();
	
	
	public Client(String nom, String prenom, String numTel, int pointFidelite) {
		this.nom = nom;
		this.prenom = prenom;
		this.numTel = numTel;
		this.nbPointFidelite = pointFidelite;
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
}

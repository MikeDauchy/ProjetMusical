package fabriques.donnes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import donnees.Forfait;
import donnees.reservations.Reservation;
import donnees.reservations.ReservationDeuxHeures;
import donnees.reservations.ReservationUneHeure;
import exceptions.accesAuDonnees.CreationObjetException;
import exceptions.accesAuDonnees.ObjetExistant;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.bdd.ConnexionFactory;

public class ReservationFactory {

	static ReservationFactory singleton;
	
	private ReservationFactory(){
		super();
	}
	
	public Reservation creer(int idFacture, int idSalle, int nbHeure, Date dateDebut, Date dateFin) throws ObjetExistant, CreationObjetException, SQLException {
			Reservation reservation = null;
			
			try{
				rechercherByIdSalleAndDates(idSalle, dateDebut, dateFin);
				throw new ObjetExistant(Reservation.class.toString(), "avec l'idSalle "+idSalle+" et pour les dates "+dateDebut+" et "+dateFin);
			}catch(ObjetInconnu e){
			
				String query = "INSERT INTO reservation (id_facture, id_salle, nb_heure, date_debut, date_fin, type_reservation) VALUES(?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.clearParameters();
				preparedStatement.setInt(1, idFacture);
				preparedStatement.setInt(2, idSalle);
				preparedStatement.setInt(3, nbHeure);
				preparedStatement.setTimestamp(4, new Timestamp(dateDebut.getTime()));
				preparedStatement.setTimestamp(5, new Timestamp(dateFin.getTime()));
				preparedStatement.setInt(6, (nbHeure > 1)?2:1);
				preparedStatement.execute();
				
				ResultSet rs = preparedStatement.getGeneratedKeys();
				rs.next();
		        int idReservation = rs.getInt(1);
				
				try {
					reservation = rechercherByIdReservation(idReservation);
				} catch (ObjetInconnu e1) {
					throw new CreationObjetException("La creation de la reservation avec l'idReservation "+idReservation+" n'a pas fonctionnée");
				}
			}
		return reservation;
	}
	

	public Reservation rechercherByIdReservation(int idReservation) throws ObjetInconnu, SQLException{
			
		String query = "Select id_reservation, id_facture, id_salle, nb_heure, date_debut, date_fin, type_reservation FROM Reservation WHERE id_reservation = ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setInt(1, idReservation);
		ResultSet rs = preparedStatement.executeQuery();
		
		if(!rs.next())
			throw new ObjetInconnu(Reservation.class.toString(), "avec l'idReservation "+idReservation);
		
		Reservation reservation = (rs.getInt("type_reservation") > 1)?new ReservationDeuxHeures(): new ReservationUneHeure();
		reservation.setIdReservation(rs.getInt("id_reservation"));
		reservation.setIdFacture(rs.getInt("id_facture"));
		reservation.setIdSalle(rs.getInt("id_salle"));
		reservation.setNbHeure(rs.getInt("nb_heure"));
		reservation.setNbHeure(rs.getInt("nb_heure"));
		reservation.setDateDebut(rs.getTimestamp(("date_debut")));
		reservation.setDateFin(rs.getTimestamp("date_fin"));
		
		return reservation;
	}
	
	public List<Reservation> rechercherByIdSalleAndDates(int idSalle,  Date dateDebut, Date dateFin) throws ObjetInconnu, SQLException{
		
		List<Reservation> listReservation = new ArrayList<Reservation>();
		
		String query = "Select id_reservation, id_facture, id_salle, nb_heure, date_debut, date_fin, type_reservation FROM Reservation WHERE id_salle = ? and (date_debut, date_fin) OVERLAPS (? , ?)";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setInt(1, idSalle);
		preparedStatement.setTimestamp(2, new Timestamp(dateDebut.getTime()));
		preparedStatement.setTimestamp(3, new Timestamp(dateFin.getTime()));
		ResultSet rs = preparedStatement.executeQuery();
		
		if(!rs.next())
			throw new ObjetInconnu(Reservation.class.toString(), "avec l'idSalle "+idSalle+" et pour les dates "+dateDebut+" et "+dateFin);
		do{
			Reservation reservation = (rs.getInt("type_reservation") > 1)?new ReservationDeuxHeures(): new ReservationUneHeure();
			reservation.setIdReservation(rs.getInt("id_reservation"));
			reservation.setIdFacture(rs.getInt("id_facture"));
			reservation.setIdSalle(rs.getInt("id_salle"));
			reservation.setNbHeure(rs.getInt("nb_heure"));
			reservation.setNbHeure(rs.getInt("nb_heure"));
			reservation.setDateDebut(rs.getTimestamp(("date_debut")));
			reservation.setDateFin(rs.getTimestamp("date_fin"));
			listReservation.add(reservation);
		}while(rs.next());
		
		return listReservation;
	}
	
	
	public List<Reservation> listerByDates(Date dateDebut, Date dateFin) throws SQLException, ObjetInconnu {
		List<Reservation> listReservation = new ArrayList<Reservation>();
		
		String query = "Select id_reservation, id_facture, id_salle, nb_heure, date_debut, date_fin, type_reservation FROM Reservation WHERE date_debut >= ? and date_fin <= ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setTimestamp(1, new Timestamp(dateDebut.getTime()));
		preparedStatement.setTimestamp(2, new Timestamp(dateFin.getTime()));
		ResultSet rs = preparedStatement.executeQuery();
		
		if(!rs.next())
			throw new ObjetInconnu(Reservation.class.toString(), "pour les dates "+dateDebut+" et "+dateFin);
		do{
			Reservation reservation = (rs.getInt("type_reservation") > 1)?new ReservationDeuxHeures(): new ReservationUneHeure();
			reservation.setIdReservation(rs.getInt("id_reservation"));
			reservation.setIdFacture(rs.getInt("id_facture"));
			reservation.setIdSalle(rs.getInt("id_salle"));
			reservation.setNbHeure(rs.getInt("nb_heure"));
			reservation.setNbHeure(rs.getInt("nb_heure"));
			reservation.setDateDebut(rs.getTimestamp(("date_debut")));
			reservation.setDateFin(rs.getTimestamp("date_fin"));
			listReservation.add(reservation);
		}while(rs.next());
		
		return listReservation;
	}
	
	public List<Reservation> listerByIdClient(int idClient) throws SQLException, ObjetInconnu {
		List<Reservation> listReservation = new ArrayList<Reservation>();
		
		String query = "Select Reservation.id_reservation, Reservation.id_facture, Reservation.id_salle, Reservation.nb_heure, Reservation.date_debut, Reservation.date_fin, Reservation.type_reservation "
					 + "FROM Reservation "
					 + "JOIN facture on reservation.id_facture = facture.id_facture "
					 + "WHERE facture.id_client = ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setInt(1, idClient);
		ResultSet rs = preparedStatement.executeQuery();
		
		if(!rs.next())
			throw new ObjetInconnu(Reservation.class.toString(), "pour l'idClient "+idClient);
		do{
			Reservation reservation = (rs.getInt("type_reservation") > 1)?new ReservationDeuxHeures(): new ReservationUneHeure();
			reservation.setIdReservation(rs.getInt("id_reservation"));
			reservation.setIdFacture(rs.getInt("id_facture"));
			reservation.setIdSalle(rs.getInt("id_salle"));
			reservation.setNbHeure(rs.getInt("nb_heure"));
			reservation.setNbHeure(rs.getInt("nb_heure"));
			reservation.setDateDebut(rs.getTimestamp(("date_debut")));
			reservation.setDateFin(rs.getTimestamp("date_fin"));
			listReservation.add(reservation);
		}while(rs.next());
		
		return listReservation;
	}
	
	public List<Reservation> listerByIdFacture(int idFacture) throws SQLException, ObjetInconnu {
		List<Reservation> listReservation = new ArrayList<Reservation>();
		
		String query = "Select id_reservation, id_facture, id_salle, nb_heure, date_debut, date_fin, type_reservation FROM Reservation WHERE id_facture = ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setInt(1, idFacture);
		ResultSet rs = preparedStatement.executeQuery();
		
		if(!rs.next())
			throw new ObjetInconnu(Reservation.class.toString(), "pour l'id facture : "+idFacture);
		do{
			Reservation reservation = (rs.getInt("type_reservation") > 1)?new ReservationDeuxHeures(): new ReservationUneHeure();
			reservation.setIdReservation(rs.getInt("id_reservation"));
			reservation.setIdFacture(rs.getInt("id_facture"));
			reservation.setIdSalle(rs.getInt("id_salle"));
			reservation.setNbHeure(rs.getInt("nb_heure"));
			reservation.setNbHeure(rs.getInt("nb_heure"));
			reservation.setDateDebut(rs.getTimestamp(("date_debut")));
			reservation.setDateFin(rs.getTimestamp("date_fin"));
			listReservation.add(reservation);
		}while(rs.next());
		
		return listReservation;
	}
	
	public void update(Reservation reservation) throws ObjetInconnu, SQLException{
		if(rechercherByIdReservation(reservation.getIdReservation()) == null)
			throw new ObjetInconnu(Reservation.class.toString(), reservation.toString());
		
		String query = "UPDATE reservation SET id_facture = ?, id_salle = ?, nb_heure = ?, date_debut = ?, date_fin = ?, type_reservation = ? WHERE id_reservation = ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setInt(1, reservation.getIdFacture());
		preparedStatement.setInt(2, reservation.getIdSalle());
		preparedStatement.setInt(3, reservation.getNbHeure());
		preparedStatement.setTimestamp(4, new Timestamp(reservation.getDateDebut().getTime()));
		preparedStatement.setTimestamp(5, new Timestamp(reservation.getDateFin().getTime()));
		preparedStatement.setInt(6, (reservation.getNbHeure() > 1)?2:1);
		preparedStatement.setInt(7, reservation.getIdReservation());
		preparedStatement.execute();
	}
	
	
	public void supprimer(Reservation reservation) throws SQLException, ObjetInconnu{
			
		if(rechercherByIdReservation(reservation.getIdReservation()) == null)
			throw new ObjetInconnu(Reservation.class.toString(), reservation.toString());
		
		String query = "DELETE FROM reservation WHERE id_reservation = ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setInt(1, reservation.getIdReservation());
		preparedStatement.execute();
	}
	
	public static ReservationFactory getInstance(){
		if(singleton==null) singleton = new ReservationFactory();
		return singleton;
	}
	
}

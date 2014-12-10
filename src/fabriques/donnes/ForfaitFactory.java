package fabriques.donnes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import donnees.Forfait;
import donnees.salles.Salle;
import exceptions.accesAuDonnees.CreationObjetException;
import exceptions.accesAuDonnees.ObjetExistant;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.bdd.ConnexionFactory;


public class ForfaitFactory {
	
	static ForfaitFactory singleton;
	
	private ForfaitFactory(){
		super();
	}
	
	public Forfait creer(int idClient, int nbHeure, Date dateDebut, Date dateFin, double montant, Salle.type typeSalle) throws ObjetExistant, CreationObjetException, SQLException {
		Forfait forfait = null;
		int idForfait;
		
		try{
			if(!rechercherByIdClient(idClient).isEmpty())
				throw new ObjetExistant(Forfait.class.toString(), "pour l'idClient "+idClient);
		}catch(ObjetInconnu e){
			
			String query = "INSERT INTO forfait (id_client, nb_heure, date_debut, date_fin, montant, type_salle) VALUES(?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.clearParameters();
			preparedStatement.setInt(1, idClient);
			preparedStatement.setInt(2, nbHeure);
			preparedStatement.setDate(3, new java.sql.Date(dateDebut.getTime()));
			preparedStatement.setDate(4, new java.sql.Date(dateFin.getTime()));
			preparedStatement.setDouble(5, montant);
			preparedStatement.setString(6, typeSalle.toString());
			preparedStatement.executeUpdate();
			
			ResultSet rs = preparedStatement.getGeneratedKeys();
			rs.next();
	        idForfait = rs.getInt(1);
			
			try {
				forfait = rechercherByIdForfait(idForfait);
			} catch (ObjetInconnu e1) {
				throw new CreationObjetException("La creation du forfait avec l'id client "+idClient+" n'a pas fonctionnée");
			}
		}
		
		return forfait;
	}
	
	
	public Forfait rechercherByIdForfait(int idForfait) throws ObjetInconnu, SQLException{
		
		String query = "Select id_forfait, id_client, nb_heure, date_debut, date_fin, montant, type_salle FROM forfait WHERE id_forfait = ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setInt(1, idForfait);
		ResultSet rs = preparedStatement.executeQuery();
		
		if(!rs.next())
			throw new ObjetInconnu(Forfait.class.toString(), "avec l'idForfait "+idForfait);
		
		Forfait forfait = new Forfait();
		forfait.setIdForfait(rs.getInt("id_forfait"));
		forfait.setIdClient(rs.getInt("id_client"));
		forfait.setNbHeure(rs.getInt("nb_heure"));
		forfait.setDateDebut(new java.util.Date(rs.getDate("date_debut").getTime()));
		forfait.setDateFin(new java.util.Date(rs.getDate("date_fin").getTime()));
		forfait.setMontant(rs.getDouble("montant"));
		forfait.setTypeSalle(Salle.type.valueOf(rs.getString("type_salle")));
		
		return forfait;
	}
	
	public List<Forfait> rechercherByIdClient(int idClient) throws ObjetInconnu, SQLException{
		List<Forfait> listForfaits = new ArrayList<Forfait>();
		
		String query = "Select id_forfait, id_client, nb_heure, date_debut, date_fin, montant, type_salle FROM forfait WHERE id_client = ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setInt(1, idClient);
		ResultSet rs = preparedStatement.executeQuery();
		
		if(!rs.next())
			throw new ObjetInconnu(Forfait.class.toString(), "avec l'idClient "+idClient);
		do{
			Forfait forfait = new Forfait();
			forfait.setIdForfait(rs.getInt("id_forfait"));
			forfait.setIdClient(rs.getInt("id_client"));
			forfait.setNbHeure(rs.getInt("nb_heure"));
			forfait.setDateDebut(new java.util.Date(rs.getDate("date_debut").getTime()));
			forfait.setDateFin(new java.util.Date(rs.getDate("date_fin").getTime()));
			forfait.setMontant(rs.getDouble("montant"));
			forfait.setTypeSalle(Salle.type.valueOf(rs.getString("type_salle")));
			listForfaits.add(forfait);
		}while(rs.next());
		
		return listForfaits;
	}
	
	public List<Forfait> lister() throws SQLException {
		List<Forfait> listForfait = new ArrayList<Forfait>();
		
		String query = "Select id_forfait, id_client, nb_heure, date_debut, date_fin, montant, type_salle FROM forfait";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		
		while(rs.next()){
			Forfait forfait = new Forfait();
			forfait.setIdForfait(rs.getInt("id_forfait"));
			forfait.setIdClient(rs.getInt("id_client"));
			forfait.setNbHeure(rs.getInt("nb_heure"));
			forfait.setDateDebut(new java.util.Date(rs.getDate("date_debut").getTime()));
			forfait.setDateFin(new java.util.Date(rs.getDate("date_fin").getTime()));
			forfait.setMontant(rs.getDouble("montant"));
			forfait.setTypeSalle(Salle.type.valueOf(rs.getString("type_salle")));
			listForfait.add(forfait);
		}
		
		return listForfait;
	}
	
	
	public void update(Forfait forfait) throws ObjetInconnu, SQLException{
		if(rechercherByIdForfait(forfait.getIdForfait()) == null)
			throw new ObjetInconnu(Forfait.class.toString(), forfait.toString());
		
		String query = "UPDATE forfait SET id_client = ?, nb_heure = ?, date_debut = ?, date_fin = ?, montant = ?, type_salle = ? WHERE id_forfait = ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setInt(1, forfait.getIdClient());
		preparedStatement.setInt(2, forfait.getNbHeure());
		preparedStatement.setDate(3, new java.sql.Date(forfait.getDateDebut().getTime()));
		preparedStatement.setDate(4, new java.sql.Date(forfait.getDateFin().getTime()));
		preparedStatement.setDouble(5, forfait.getMontant());
		preparedStatement.setString(6, forfait.getTypeSalle().toString());
		preparedStatement.setDouble(7, forfait.getIdForfait());
		preparedStatement.executeUpdate();
	}
	
	public void supprimer(Forfait forfait) throws SQLException, ObjetInconnu{
			
		if(rechercherByIdClient(forfait.getIdClient()) == null)
			throw new ObjetInconnu(Forfait.class.toString(), forfait.toString());
		
		String query = "DELETE FROM forfait WHERE id_forfait = ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setInt(1, forfait.getIdForfait());
		preparedStatement.execute();
	}
	
	public static ForfaitFactory getInstance(){
		if(singleton==null) singleton = new ForfaitFactory();
		return singleton;
	}
}

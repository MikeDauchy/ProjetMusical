package fabriques.donnes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import donnees.Facture;
import donnees.salles.EnregistrementSalle;
import donnees.salles.MoyenneSalle;
import donnees.salles.PetiteSalle;
import donnees.salles.Salle;
import exceptions.accesAuDonnees.CreationObjetException;
import exceptions.accesAuDonnees.ObjetExistant;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.bdd.ConnexionFactory;

public class SalleFactory {
	

static SalleFactory singleton;
	
	private SalleFactory(){
		super();
	}
	
	public Salle creer(String description,  Salle.type typeSalle) throws ObjetExistant, CreationObjetException, SQLException {
			Salle salle;
			
			String query = "INSERT INTO salle (description, type_salle) VALUES(?, ?)";
			PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.clearParameters();
			preparedStatement.setString(1, description);
			preparedStatement.setString(2, typeSalle.toString());
			preparedStatement.execute();
			
			ResultSet rs = preparedStatement.getGeneratedKeys();
			rs.next();
	        int idSalle = rs.getInt(1);
			
			try {
				salle = rechercherByIdSalle(idSalle);
			} catch (ObjetInconnu e1) {
				throw new CreationObjetException("La creation de la Salle avec la description "+description+" et de type "+typeSalle);
			}
		
		return salle;
	}
	
	public Salle rechercherByIdSalle(int idSalle) throws ObjetInconnu, SQLException{
		Salle salle = null;
			
		String query = "Select id_salle, description, type_salle FROM salle WHERE id_salle = ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setInt(1, idSalle);
		ResultSet rs = preparedStatement.executeQuery();
		
		if(!rs.next())
			throw new ObjetInconnu(Salle.class.toString(), "avec l'idSalle "+idSalle);
		
		
		switch(Salle.type.valueOf(rs.getString("type_salle"))){
			case PETITE:
				salle = new PetiteSalle();
				salle.setTypeSalle(Salle.type.PETITE);break;
			case MOYENNE:
				salle = new MoyenneSalle();
				salle.setTypeSalle(Salle.type.MOYENNE);break;
			case ENREGISTREMENT:
				salle = new EnregistrementSalle();
				salle.setTypeSalle(Salle.type.ENREGISTREMENT);break;
		}
		salle.setIdSalle(rs.getInt("id_salle"));
		salle.setDescription(rs.getString("description"));
		
		return salle;
	}
	
	public Salle rechercherByTypeSalle(Salle.type typeSalle) throws ObjetInconnu, SQLException{
		Salle salle = null;
			
		String query = "Select id_salle, description, type_salle FROM salle WHERE type_salle = ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setString(1, typeSalle.toString());
		ResultSet rs = preparedStatement.executeQuery();
		
		if(!rs.next())
			throw new ObjetInconnu(Salle.class.toString(), "avec le type de salle "+typeSalle.toString());
		
		
		switch(Salle.type.valueOf(rs.getString("type_salle"))){
			case PETITE:
				salle = new PetiteSalle();
				salle.setTypeSalle(Salle.type.PETITE);break;
			case MOYENNE:
				salle = new MoyenneSalle();
				salle.setTypeSalle(Salle.type.MOYENNE);break;
			case ENREGISTREMENT:
				salle = new EnregistrementSalle();
				salle.setTypeSalle(Salle.type.ENREGISTREMENT);break;
		}
		salle.setIdSalle(rs.getInt("id_salle"));
		salle.setDescription(rs.getString("description"));
		
		return salle;
	}
	
	public List<Salle> lister() throws SQLException {
		List<Salle> listSalle = new ArrayList<Salle>();
		Salle salle = null;
		
		String query = "Select id_salle, description, type_salle FROM salle";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		
		while(rs.next()){
			switch(Salle.type.valueOf(rs.getString("type_salle"))){
				case PETITE:
					salle = new PetiteSalle();
					salle.setTypeSalle(Salle.type.PETITE);break;
				case MOYENNE:
					salle = new MoyenneSalle();
					salle.setTypeSalle(Salle.type.MOYENNE);break;
				case ENREGISTREMENT:
					salle = new EnregistrementSalle();
					salle.setTypeSalle(Salle.type.ENREGISTREMENT);break;
			}
			salle.setIdSalle(rs.getInt("id_salle"));
			salle.setDescription(rs.getString("description"));
			listSalle.add(salle);
		}
		
		return listSalle;
	}
	
	public List<Salle> listerByType(Salle.type typeSalle) throws SQLException {
		List<Salle> listSalle = new ArrayList<Salle>();
		Salle salle = null;
		
		String query = "Select id_salle, description, type_salle FROM salle WHERE type_salle = ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setString(1, typeSalle.toString());
		ResultSet rs = preparedStatement.executeQuery();
		
		while(rs.next()){
			switch(Salle.type.valueOf(rs.getString("type_salle"))){
				case PETITE:
					salle = new PetiteSalle();
					salle.setTypeSalle(Salle.type.PETITE);break;
				case MOYENNE:
					salle = new MoyenneSalle();
					salle.setTypeSalle(Salle.type.MOYENNE);break;
				case ENREGISTREMENT:
					salle = new EnregistrementSalle();
					salle.setTypeSalle(Salle.type.ENREGISTREMENT);break;
			}
			salle.setIdSalle(rs.getInt("id_salle"));
			salle.setDescription(rs.getString("description"));
			listSalle.add(salle);
		}
		
		return listSalle;
	}
	
	public void update(Salle salle) throws ObjetInconnu, SQLException{
		if(rechercherByIdSalle(salle.getIdSalle()) == null)
			throw new ObjetInconnu(Salle.class.toString(), salle.toString());
		
		Salle.type type = null;
		if(salle.getClass().equals(PetiteSalle.class)){
			type = Salle.type.PETITE;
		}
		if(salle.getClass().equals(MoyenneSalle.class)){
			type = Salle.type.MOYENNE;
		}
		if(salle.getClass().equals(EnregistrementSalle.class)){
			type = Salle.type.ENREGISTREMENT;
		}
		
		String query = "UPDATE salle SET description = ?, type_salle = ? WHERE id_salle = ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.clearParameters();
		preparedStatement.setString(1, salle.getDescription());
		preparedStatement.setString(2, type.toString());
		preparedStatement.setInt(3, salle.getIdSalle());
		preparedStatement.execute();
	}
	
	public void supprimer(Salle salle) throws SQLException, ObjetInconnu{
			
		if(rechercherByIdSalle(salle.getIdSalle()) == null)
			throw new ObjetInconnu(Facture.class.toString(), salle.toString());
		
		String query = "DELETE FROM salle WHERE id_salle = ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setInt(1, salle.getIdSalle());
		preparedStatement.execute();
	}
	
	public static SalleFactory getInstance(){
		if(singleton==null) singleton = new SalleFactory();
		return singleton;
	}
	
}

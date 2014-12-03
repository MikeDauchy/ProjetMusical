package fabriques.donnes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import donnees.Facture;
import exceptions.accesAuDonnees.CreationObjetException;
import exceptions.accesAuDonnees.ObjetExistant;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.bdd.ConnexionFactory;

public class FactureFactory {

	static FactureFactory singleton;
	
	private FactureFactory(){
		super();
	}
	
	public Facture creer(int idClient, boolean estPaye) throws ObjetExistant, CreationObjetException, SQLException {
			Facture facture;
		
			String query = "INSERT INTO facture (id_client, est_paye) VALUES(?, ?)";
			PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.clearParameters();
			preparedStatement.setInt(1, idClient);
			preparedStatement.setBoolean(2, estPaye);
			preparedStatement.execute();
			
			ResultSet rs = preparedStatement.getGeneratedKeys();
			rs.next();
	        int idFacture = rs.getInt(1);
			
			try {
				facture = rechercherByIdFacture(idFacture);
			} catch (ObjetInconnu e1) {
				throw new CreationObjetException("La creation de la facture avec l'idClient "+idClient+" n'a pas fonctionnée");
			}
		
		return facture;
	}
	
	public List<Facture> rechercherByIdClient(int idClient) throws ObjetInconnu, SQLException{
		
		List<Facture> listFacture = new ArrayList<Facture>();
		
		String query = "Select id_facture, id_client, est_paye FROM facture WHERE id_client = ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setInt(1, idClient);
		ResultSet rs = preparedStatement.executeQuery();
		
		if(!rs.next())
			throw new ObjetInconnu(Facture.class.toString(), "avec l'idClient "+idClient);
		do{
			Facture facture = new Facture();
			facture.setIdClient(rs.getInt("id_client"));
			facture.setIdFacture(rs.getInt("id_facture"));
			facture.setEstPaye(rs.getBoolean("est_paye"));
			listFacture.add(facture);
		}while(rs.next());
		
		return listFacture;
	}
	
	public Facture rechercherByIdFacture(int idFacture) throws ObjetInconnu, SQLException{
			
		String query = "Select id_facture, id_client, est_paye FROM facture WHERE id_facture = ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setInt(1, idFacture);
		ResultSet rs = preparedStatement.executeQuery();
		
		if(!rs.next())
			throw new ObjetInconnu(Facture.class.toString(), "avec l'idFacture "+idFacture);
		
		Facture facture = new Facture();
		facture.setIdClient(rs.getInt("id_client"));
		facture.setIdFacture(rs.getInt("id_facture"));
		facture.setEstPaye(rs.getBoolean("est_paye"));
		
		return facture;
	}
	
	public List<Facture> lister() throws SQLException {
		List<Facture> listFacture = new ArrayList<Facture>();
		
		String query = "Select id_client, id_facture, est_paye FROM facture";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		
		while(rs.next()){
			Facture facture = new Facture();
			facture.setIdClient(rs.getInt("id_client"));
			facture.setIdFacture(rs.getInt("id_facture"));
			facture.setEstPaye(rs.getBoolean("est_paye"));
			listFacture.add(facture);
		}
		
		return listFacture;
	}
	
	
	public void supprimer(Facture facture) throws SQLException, ObjetInconnu{
			
		if(rechercherByIdFacture(facture.getIdFacture()) == null)
			throw new ObjetInconnu(Facture.class.toString(), facture.toString());
		
		String query = "DELETE FROM facture WHERE id_facture = ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setInt(1, facture.getIdFacture());
		preparedStatement.execute();
	}
	
	public static FactureFactory getInstance(){
		if(singleton==null) singleton = new FactureFactory();
		return singleton;
	}
	
}

package fabriques.donnes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import donnees.Client;
import exceptions.accesAuDonnees.CreationObjetException;
import exceptions.accesAuDonnees.ObjetExistant;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.bdd.ConnexionFactory;

public class FactureFactory {

	//FIXME: A coder !!!
	
	
	
static FactureFactory singleton;
	
	private FactureFactory(){
		super();
	}
	
	public Facture creer(int idClient, boolean estPaye) throws ObjetExistant, CreationObjetException, SQLException {
			Facture facture;
		
			String query = "INSERT INTO client (nom, prenom, numTel, nbPointFidelite) VALUES(?, ?, ?, ?)";
			PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
			preparedStatement.clearParameters();
			preparedStatement.setString(1, nom);
			preparedStatement.setString(2, prenom);
			preparedStatement.setString(3, numTel);
			preparedStatement.setInt(4, nbPointFidelite);
			preparedStatement.execute();
			
			try {
				facture = rechercherByNom(nom).get(0);
			} catch (ObjetInconnu e1) {
				throw new CreationObjetException("La creation du client avec le nom "+nom+" n'a pas fonctionnée");
			}
		
		return facture;
	}
	
	public List<Client> rechercherByNom(String nom) throws ObjetInconnu, SQLException{
		
		List<Client> listClient = new ArrayList<Client>();
		
		String query = "Select id_client, nom, prenom, numTel, nbPointFidelite FROM client WHERE nom = ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setString(1, nom);
		ResultSet rs = preparedStatement.executeQuery();
		
		if(!rs.next())
			throw new ObjetInconnu(Client.class.toString(), nom);
		do{
			Client client = new Client();
			client.setIdClient(rs.getInt("id_client"));
			client.setNom(rs.getString("nom"));
			client.setPrenom(rs.getString("prenom"));
			client.setNumTel(rs.getString("numTel"));
			client.setPointFidelite(rs.getInt("nbPointFidelite"));
			listClient.add(client);
		}while(rs.next());
		
		return listClient;
	}
	
	public Client rechercherById(int idClient) throws ObjetInconnu, SQLException{
			
		String query = "Select id_client, nom, prenom, numTel, nbPointFidelite FROM client WHERE id_client = ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setInt(1, idClient);
		ResultSet rs = preparedStatement.executeQuery();
		
		if(!rs.next())
			throw new ObjetInconnu(Client.class.toString(), "avec l'id "+idClient);
		
		Client client = new Client();
		client.setIdClient(rs.getInt("id_client"));
		client.setNom(rs.getString("nom"));
		client.setPrenom(rs.getString("prenom"));
		client.setNumTel(rs.getString("numTel"));
		client.setPointFidelite(rs.getInt("nbPointFidelite"));
		
		return client;
	}
	
	public List<Client> lister() throws SQLException {
		List<Client> listClient = new ArrayList<Client>();
		
		String query = "Select id_client, nom, prenom, numTel, nbPointFidelite FROM client";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		
		while(rs.next()){
			Client client = new Client();
			client.setIdClient(rs.getInt("id_client"));
			client.setNom(rs.getString("nom"));
			client.setPrenom(rs.getString("prenom"));
			client.setNumTel(rs.getString("numTel"));
			client.setPointFidelite(rs.getInt("nbPointFidelite"));
			listClient.add(client);
		}
		
		return listClient;
	}
	
	
	public void supprimer(Client client) throws SQLException, ObjetInconnu{
			
		if(rechercherById(client.getIdClient()) == null)
			throw new ObjetInconnu(Client.class.toString(), client.toString());
		
		String query = "DELETE FROM client WHERE id_client = ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setInt(1, client.getIdClient());
		preparedStatement.execute();
	}
	
	public static FactureFactory getInstance(){
		if(singleton==null) singleton = new FactureFactory();
		return singleton;
	}
	
}

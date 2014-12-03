package fabriques.donnes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import donnees.Client;
import exceptions.accesAuDonnees.CreationObjetException;
import exceptions.accesAuDonnees.ObjetExistant;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.bdd.ConnexionFactory;

public class ClientFactory {
	
	static ClientFactory singleton;
	
	private ClientFactory(){
		super();
	}
	
	public Client creer(String nom, String prenom, String numTel, int nbPointFidelite) throws ObjetExistant, CreationObjetException, SQLException {
		Client client = null;
		try{
			if(!rechercherByNom(nom).isEmpty())
				throw new ObjetExistant(Client.class.toString(), nom);
		}catch(ObjetInconnu e){
			
			String query = "INSERT INTO client (nom, prenom, numTel, nbPointFidelite) VALUES(?, ?, ?, ?)";
			PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.clearParameters();
			preparedStatement.setString(1, nom);
			preparedStatement.setString(2, prenom);
			preparedStatement.setString(3, numTel);
			preparedStatement.setInt(4, nbPointFidelite);
			preparedStatement.execute();
			
			ResultSet rs = preparedStatement.getGeneratedKeys();
			rs.next();
	        int idClient = rs.getInt(1);
			
			try {
				client = rechercherById(idClient);
			} catch (ObjetInconnu e1) {
				throw new CreationObjetException("La creation du client avec le nom "+nom+" n'a pas fonctionnée");
			}
		}
		
		return client;
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
	
	public void update(Client client) throws ObjetInconnu, SQLException{
		if(rechercherById(client.getIdClient()) == null)
			throw new ObjetInconnu(Client.class.toString(), client.toString());
		
		String query = "UPDATE client SET nom = ?, prenom = ?, numTel = ?, nbPointFidelite = ? WHERE id_client = ?";
		PreparedStatement preparedStatement = ConnexionFactory.getInstance().prepareStatement(query);
		preparedStatement.clearParameters();
		preparedStatement.setString(1, client.getNom());
		preparedStatement.setString(2, client.getPrenom());
		preparedStatement.setString(3, client.getNumTel());
		preparedStatement.setInt(4, client.getPointFidelite());
		preparedStatement.setInt(5, client.getIdClient());
		preparedStatement.execute();
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
	
	public static ClientFactory getInstance(){
		if(singleton==null) singleton = new ClientFactory();
		return singleton;
	}
	
}

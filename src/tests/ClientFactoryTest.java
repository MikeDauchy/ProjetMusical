package tests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import donnees.Client;
import exceptions.accesAuDonnees.CreationObjetException;
import exceptions.accesAuDonnees.ObjetExistant;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.donnes.ClientFactory;


public class ClientFactoryTest {
	
	Client quentinClient;
	List<Client> listClientQuentin = new ArrayList<Client>();
	
	@Before
	public void init() {
		quentinClient = new Client();
		quentinClient.setNom("Laujac");
		quentinClient.setPrenom("Quentin");
		quentinClient.setNumTel("0662487954");
		quentinClient.setPointFidelite(12);
		
		listClientQuentin.add(quentinClient);
	}

	/**
	 * simpleAdd
	 * @throws SQLException 
	 * @throws ObjetExistant 
	 * @throws CreationObjetException 
	 * @throws ObjetInconnu 
	 */
	@Test
	public void testGlobal() throws CreationObjetException, ObjetExistant, SQLException, ObjetInconnu {
		
		Client client = ClientFactory.getInstance().creer("Laujac", "Quentin", "0662487954", 12);
		assertEquals(client, quentinClient);
		
		assertEquals(ClientFactory.getInstance().rechercherById(client.getIdClient()), quentinClient);
		
		List<Client> listClient = ClientFactory.getInstance().rechercherByNom(client.getNom());
		assertEquals(listClient, listClientQuentin);
		
		List<Client> listerClient = ClientFactory.getInstance().lister();
		assertEquals(listerClient, listClientQuentin);
		
		ClientFactory.getInstance().supprimer(client);
		
		try{
			ClientFactory.getInstance().rechercherById(client.getIdClient());
		}catch(ObjetInconnu e){
			//resultat attendu
			assert(true);
		}
	}

}

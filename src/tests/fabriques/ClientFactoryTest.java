package tests.fabriques;

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
	
	Client mockClient;
	List<Client> listClientMock = new ArrayList<Client>();
	
	@Before
	public void init() {
		mockClient = new Client();
		mockClient.setNom("mockqsdfqdfc");
		mockClient.setPrenom("mockofn,fldocnd");
		mockClient.setNumTel("0662487954");
		mockClient.setPointFidelite(12);
		
		listClientMock.add(mockClient);
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
		
		Client client = ClientFactory.getInstance().creer("mockqsdfqdfc", "mockofn,fldocnd", "0662487954", 12);
		assertEquals(client, mockClient);
		
		assertEquals(ClientFactory.getInstance().rechercherById(client.getIdClient()), mockClient);
		
		List<Client> listClient = ClientFactory.getInstance().rechercherByNom(client.getNom());
		assertEquals(listClient, listClientMock);
		
		List<Client> listerClient = ClientFactory.getInstance().lister();
		assertEquals(listerClient, listClientMock);
		
		ClientFactory.getInstance().supprimer(client);
		
		try{
			ClientFactory.getInstance().rechercherById(client.getIdClient());
		}catch(ObjetInconnu e){
			//resultat attendu
			assert(true);
		}
	}

}

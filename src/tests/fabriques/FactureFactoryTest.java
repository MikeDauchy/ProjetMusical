package tests.fabriques;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import donnees.Client;
import donnees.Facture;
import exceptions.accesAuDonnees.CreationObjetException;
import exceptions.accesAuDonnees.ObjetExistant;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.donnes.ClientFactory;
import fabriques.donnes.FactureFactory;

public class FactureFactoryTest {
	Facture mockFacture;
	List<Facture> listFactureMock = new ArrayList<Facture>();
	
	@Before
	public void init() {
		mockFacture = new Facture();
		mockFacture.setIdClient(-2);
		mockFacture.setEstPaye(true);
		
		listFactureMock.add(mockFacture);
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
		
		Facture facture = FactureFactory.getInstance().creer(-2, true);
		assertEquals(facture, mockFacture);
		
		assertEquals(FactureFactory.getInstance().rechercherByIdFacture(facture.getIdFacture()), mockFacture);
		
		List<Facture> listFacture = FactureFactory.getInstance().rechercherByIdClient(facture.getIdClient());
		assertEquals(listFacture, listFactureMock);
		
		List<Facture> listerFacture = FactureFactory.getInstance().lister();
		assertEquals(listerFacture, listFactureMock);
		
		FactureFactory.getInstance().supprimer(facture);
		
		try{
			FactureFactory.getInstance().rechercherByIdFacture(facture.getIdClient());
		}catch(ObjetInconnu e){
			//resultat attendu
			assert(true);
		}
	}
}

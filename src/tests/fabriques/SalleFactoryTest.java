package tests.fabriques;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import donnees.salles.GrandeSalle;
import donnees.salles.Salle;
import exceptions.accesAuDonnees.CreationObjetException;
import exceptions.accesAuDonnees.ObjetExistant;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.donnes.SalleFactory;

public class SalleFactoryTest {
	Salle mockSalle;
	List<Salle> listSalleMock = new ArrayList<Salle>();
	
	@Before
	public void init() {
		mockSalle = new GrandeSalle();
		mockSalle.setDescription("Sale B-25 Grande");
		
		listSalleMock.add(mockSalle);
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
		
		Salle salle = SalleFactory.getInstance().creer("Sale B-25 Grande", Salle.type.GRANDE);
		assertEquals(mockSalle, salle);
		assertEquals(salle.getClass(), mockSalle.getClass());
		
		List<Salle> listSalle = SalleFactory.getInstance().listerByType(Salle.type.GRANDE);
		assertEquals(listSalle, listSalleMock);
		
		List<Salle> listerSalle = SalleFactory.getInstance().lister();
		assertEquals(listerSalle, listSalleMock);
		
		SalleFactory.getInstance().supprimer(salle);
		
		try{
			SalleFactory.getInstance().rechercherByIdSalle(salle.getIdSalle());
		}catch(ObjetInconnu e){
			//resultat attendu
			assert(true);
		}
	}
}

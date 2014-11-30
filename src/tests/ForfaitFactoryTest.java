package tests;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import donnees.Forfait;
import exceptions.accesAuDonnees.CreationObjetException;
import exceptions.accesAuDonnees.ObjetExistant;
import exceptions.accesAuDonnees.ObjetInconnu;
import fabriques.ForfaitFactory;

public class ForfaitFactoryTest {
	Forfait mokeForfait;
	List<Forfait> listMokeForfait = new ArrayList<Forfait>();
	Date dateDebut;
	Date dateFin;
	
	@Before
	public void init() {
		
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(2015, 1, 1);
		dateDebut = cal.getTime();
		
		cal.set(2015, 3, 1);
		dateFin = cal.getTime();
		
		mokeForfait = new Forfait();
		mokeForfait.setIdClient(2);
		mokeForfait.setNbHeure(12);
		mokeForfait.setDateDebut(dateDebut);
		mokeForfait.setDateFin(dateFin);
		mokeForfait.setMontant(80);
		
		listMokeForfait.add(mokeForfait);
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
		
		Forfait forfait = ForfaitFactory.getInstance().creer(2, 12, dateDebut, dateFin, 80);
		assertEquals(mokeForfait, forfait);
		
		assertEquals(ForfaitFactory.getInstance().rechercherByIdForfait(forfait.getIdForfait()), mokeForfait);
		
		List<Forfait> listForfaits = ForfaitFactory.getInstance().rechercherByIdClient(forfait.getIdClient());
		assertEquals(listMokeForfait, listForfaits);
		
		List<Forfait> listerForfaits = ForfaitFactory.getInstance().lister();
		assertEquals(listMokeForfait, listerForfaits);
		
		ForfaitFactory.getInstance().supprimer(forfait);
		
		try{
			ForfaitFactory.getInstance().rechercherByIdForfait(forfait.getIdForfait());
		}catch(ObjetInconnu e){
			//resultat attendu
			assert(true);
		}
	}
}

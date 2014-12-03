package main;

import java.sql.SQLException;

import affichage.FenetrePrincipal;
import exceptions.accesAuDonnees.CreationObjetException;
import exceptions.accesAuDonnees.ObjetExistant;
import exceptions.accesAuDonnees.ObjetInconnu;


public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		new FenetrePrincipal();
	}

}

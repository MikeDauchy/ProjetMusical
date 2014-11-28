package bdd.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnexionFactory {

	static ConnexionFactory connexionFactory;
	private Connection c;

	private ConnexionFactory(){
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			c = DriverManager.getConnection(
					"jdbc:hsqldb:file:bdd/annuaire;shutdown=true","sa", "");
		} catch (SQLException e) {
			System.out.println("Echec de connexion à la BDD");
		} catch (ClassNotFoundException e){

		}
	}

	public static Connection getInstance(){
		if(connexionFactory == null){
			connexionFactory = new ConnexionFactory();
		}
		return connexionFactory.c;
	}

	public static void closeConnexion() throws SQLException{
		if(connexionFactory != null){
			connexionFactory.c.close();
			connexionFactory = null;
		}
	}

}

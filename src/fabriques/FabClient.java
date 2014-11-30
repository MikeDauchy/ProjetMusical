package fabriques;

import java.util.HashMap;
import java.util.Map;

import donnees.Client;
import exceptions.accesAuDonnees.ObjetExistant;
import exceptions.accesAuDonnees.ObjetInconnu;

public class FabClient {
	
	Map<String, Client> lesClients =  new HashMap<String, Client>();
	static FabClient singleton;
	
	public Client creer(String nom, String prenom, String numTel, int nbPointFidelite) throws ObjetExistant {
		Client client;
		try{
			client = rechercher(nom);
			throw new ObjetExistant(Client.class.toString(), nom);
		}catch(ObjetInconnu e){
			client = new Client(nom, prenom, numTel, nbPointFidelite);
			lesClients.put(nom, client);
		}
		
		return client;
	}
	
	public Client rechercher(String nom) throws ObjetInconnu{
		Client patient = lesClients.get(nom);
		if(patient == null) throw new ObjetInconnu(Client.class.toString(), nom);
		return patient;
	}
	
	
	public void supprimer(String nom){
		lesClients.remove(nom);
	}
	
	public static FabClient getInstance(){
		if(singleton==null) singleton = new FabClient();
		return singleton;
	}
	
}

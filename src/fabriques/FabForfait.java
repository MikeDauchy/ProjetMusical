package fabriques;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import donnees.Client;
import donnees.Forfait;
import exceptions.accesAuDonnees.ObjetExistant;
import exceptions.accesAuDonnees.ObjetInconnu;

public class FabForfait {
	Map<String, Map<String,Forfait>> lesForfaits =  new HashMap<String, List<Forfait>>();
	static FabClient singleton;
	
	public Forfait creer(String nom, String prenom, String numTel, int nbPointFidelite) throws ObjetExistant {
		Forfait forfait;
		try{
			forfait = rechercher(nom);
			throw new ObjetExistant(Forfait.class.toString(), nom);
		}catch(ObjetInconnu e){
			forfait = new Forfait(nom, prenom, numTel, nbPointFidelite);
			lesForfaits.put(nom, forfait);
		}
		
		return forfait;
	}
	
	public List<Forfait> rechercher(String nom) throws ObjetInconnu{
		List<Forfait> forfait = lesForfaits.get(nom);
		if(forfait == null) throw new ObjetInconnu(Forfait.class.toString(), nom);
		return forfait;
	}
	
	
	public void supprimer(String nom){
		lesForfaits.remove(nom);
	}
	
	public static FabClient getInstance(){
		if(singleton==null) singleton = new FabForfait();
		return singleton;
	}

}

package donnees.salles;


public abstract class Salle {
	
	protected String description;
	protected double prix;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	abstract double getPrix();
}

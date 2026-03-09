package main5;
public class Chambre implements Comparable<Chambre> {
	private int capacite;
	private String etat;
	private int prix;
	private int categorie;
	private int numero;

	public Chambre(int numero,
			int prix,
			int categorie,
			String etat,
			int capacite
			) {
		// TODO Auto-generated constructor stub
		this.categorie=categorie;
		this.prix=prix;
		this.etat=etat;
		this.capacite=capacite;
		this.numero=numero;
		
	}
    
	public int compareTo(Chambre c)
	{
	
		return Integer.compare(capacite,c.getCapacite());
		
	}

	public String toString() {
		return"Chamber "+numero+":"+" categorie :"+ categorie+" capacite : "+capacite+" etat : "+etat+" prix : "+prix+"$ \n";
	}


	public int getCapacite() {
		return capacite;
	}


	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}


	public String getEtat() {
		return etat;
	}


	public void setEtat(String etat) {
		this.etat = etat;
	}


	public int getPrix() {
		return prix;
	}


	public void setPrix(int prix) {
		this.prix = prix;
	}


	public int getCategorie() {
		return categorie;
	}


	public void setCategorie(int categorie) {
		this.categorie = categorie;
	}


	public int getNumero() {
		return numero;
	}


	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	
	
	
	
	
}

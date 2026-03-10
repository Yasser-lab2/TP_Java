package main5;

public class Chambre {
	private int capacite;
	private char etat;
	private double prix;
	private int categorie;
	private int numero;

	public Chambre(int numero,
			double prix,
			int categorie,
			char etat,
			int capacite
			) {
		// TODO Auto-generated constructor stub
		this.categorie=categorie;
		this.prix=prix;
		this.etat=etat;
		this.capacite=capacite;
		this.numero=numero;
		
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


	public char getEtat() {
		return etat;
	}


	public void setEtat(char etat) {
		this.etat = etat;
	}


	public double getPrix() {
		return prix;
	}


	public void setPrix(double prix) {
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

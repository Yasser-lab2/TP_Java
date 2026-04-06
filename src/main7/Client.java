package main7;


public class Client implements Comparable<Client> {
	private String nom;
	private String prenom;
	private int cin;
	private String civilite;
	
	public Client(String nom, String prenom, int cin, String civilite) {
		this.nom = nom;
		this.prenom = prenom;
		this.cin = cin;
		this.civilite = civilite;
	}

	public Client() {
		// TODO Auto-generated constructor stub
	}

	public String getNom() { return nom; }
	public String getPrenom() { return prenom; }
	public int getCin() { return cin; }
	public String getCivilite() { return civilite; }

	@Override
	public String toString() {
		return civilite + " " + nom + " " + prenom + " (CIN: " + cin + ")";
	}

	// Permet de trier les clients par ordre alphabétique (Nom, puis Prénom) pour le TreeMap
	@Override
	public int compareTo(Client autre) {
		int comparaisonNom = this.nom.compareToIgnoreCase(autre.nom);
		if (comparaisonNom == 0) {
			return this.prenom.compareToIgnoreCase(autre.prenom);
		}
		return comparaisonNom;
	}
}


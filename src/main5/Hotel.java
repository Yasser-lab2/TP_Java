package main5;

import java.util.Scanner;


public class Hotel {

	

	public Hotel() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Hotel h = new Hotel();

		System.out.println("Combien de chambres ? ");
		int n = Integer.parseInt(sc.nextLine());

		Chambre[] tab = h.saisirTableau(n);

		System.out.println("=== Toutes les chambres ===");
		h.afficher(tab);

		System.out.println("=== Tri par capacité ===");
		h.trierParCapacite(tab);
		h.afficher(tab);

		System.out.println("Entrez une catégorie à filtrer : ");
		int cat = Integer.parseInt(sc.nextLine());
		System.out.println("=== Chambres de catégorie " + cat + " ===");
		h.affichercat(tab, cat);
	}

	
	public Chambre[] saisirTableau(int n) {
		Chambre[] hotel = new Chambre[n];
		for(int i=0;i<n;i++) {
			hotel[i]=saisie();
		}
		return hotel;
	}
	
	public void affichercat(Chambre [] t, int cat) {
		for(Chambre c : t) { 
			if(c.getCategorie()==cat) {
				System.out.println(c);
			}
		}
	}
	
	public void trierParCapacite(Chambre[] t) {
        for (int i = 0; i < t.length - 1; i++) {
            for (int j = 0; j < t.length - i - 1; j++) {
                if (t[j].getCapacite() > t[j+1].getCapacite()) {
                    Chambre temp = t[j];
                    t[j] = t[j+1];
                    t[j+1] = temp;
                }
            }
        }
    }
	

	public void afficher(Chambre [] t) {
		for(Chambre c : t) { 
				System.out.println(c);
		}
	}

	public Chambre saisie() {
		
		Scanner s = new Scanner(System.in);
		System.out.println("enter catergorie  : ");
		String categorie = s.nextLine();
		
		double prix;
		do {
			System.out.println("enter prix (> 0) : ");
			prix = Double.parseDouble(s.nextLine());
		} while (prix <= 0);

		int capacite;
		do {
			System.out.println("enter capacite (1-4): ");
			capacite = Integer.parseInt(s.nextLine());
		} while (capacite < 1 || capacite > 4);

		System.out.println("enter numero: ");
		String numero = s.nextLine();

		Chambre C1 = new Chambre(Integer.parseInt(numero), prix, Integer.parseInt(categorie), 'L',
				capacite);
		
		return C1;
	}

}


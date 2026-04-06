package main5;

import java.util.Scanner;
import java.util.Vector;


public class Hotel {

	

	public Hotel() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Hotel h = new Hotel();
		Fichier_Hotel fh = new Fichier_Hotel();
		Chambre[] tab = null;

		int choix;
		do {
			System.out.println("\n========= MENU HOTEL =========");
			System.out.println("--- Gestion Tableau ---");
			System.out.println("1.  Saisir les chambres dans un tableau");
			System.out.println("2.  Afficher toutes les chambres (tableau)");
			System.out.println("3.  Afficher par catégorie (tableau)");
			System.out.println("4.  Trier par capacité (tableau)");
			System.out.println("--- Gestion Fichier ---");
			System.out.println("5.  Sauvegarder le tableau dans le fichier");
			System.out.println("6.  Afficher toutes les chambres (fichier)");
			System.out.println("7.  Ajouter une chambre (fichier)");
			System.out.println("8.  Supprimer une chambre (fichier)");
			System.out.println("9.  Modifier une chambre (fichier)");
			System.out.println("10. Consulter une chambre par numéro (fichier)");
			System.out.println("11. Copier chambres d'une catégorie dans un fichier");
			System.out.println("12. Afficher les chambres libres (fichier)");
			System.out.println("13. Calculer les recettes");
			System.out.println("0.  Quitter");
			System.out.print("Votre choix : ");
			choix = Integer.parseInt(sc.nextLine());

			switch (choix) {
			case 1:
				System.out.print("Combien de chambres ? ");
				int n = Integer.parseInt(sc.nextLine());
				tab = h.saisirTableau(n, sc);
				System.out.println("Tableau de " + n + " chambres créé.");
				break;
			case 2:
				if (tab == null) {
					System.out.println("Tableau vide. Saisir d'abord (choix 1).");
				} else {
					System.out.println("=== Toutes les chambres ===");
					h.afficher(tab);
				}
				break;
			case 3:
				if (tab == null) {
					System.out.println("Tableau vide. Saisir d'abord (choix 1).");
				} else {
					System.out.print("Entrez une catégorie à filtrer : ");
					int cat = Integer.parseInt(sc.nextLine());
					System.out.println("=== Chambres de catégorie " + cat + " ===");
					h.affichercat(tab, cat);
				}
				break;
			case 4:
				if (tab == null) {
					System.out.println("Tableau vide. Saisir d'abord (choix 1).");
				} else {
					h.trierParCapacite(tab);
					System.out.println("=== Tri par capacité ===");
					h.afficher(tab);
				}
				break;
			case 5:
				if (tab == null) {
					System.out.println("Tableau vide. Saisir d'abord (choix 1).");
				} else {
					Vector<Chambre> vChambres = new Vector<>();
					for (Chambre c : tab) {
						vChambres.add(c);
					}
					fh.sauvegarderChambres(vChambres);
				}
				break;
			case 6:
				Vector<Chambre> toutes = fh.lireToutesChambres();
				System.out.println("=== Chambres depuis fichier ===");
				for (Chambre c : toutes) {
					System.out.println(c);
				}
				break;
			case 7:
				Chambre nouvelle = h.saisie(sc);
				fh.ajouterChambre(nouvelle);
				break;
			case 8:
				System.out.print("Numéro de la chambre à supprimer : ");
				int numSupp = Integer.parseInt(sc.nextLine());
				fh.supprimerChambre(numSupp);
				break;
			case 9:
				System.out.print("Numéro de la chambre à modifier : ");
				int numMod = Integer.parseInt(sc.nextLine());
				System.out.print("Nouveau prix : ");
				double newPrix = Double.parseDouble(sc.nextLine());
				System.out.print("Nouvelle catégorie : ");
				int newCat = Integer.parseInt(sc.nextLine());
				System.out.print("Nouvel état (L/O) : ");
				char newEtat = sc.nextLine().charAt(0);
				System.out.print("Nouvelle capacité (1-4) : ");
				int newCap = Integer.parseInt(sc.nextLine());
				fh.modifierChambre(numMod, newPrix, newCat, newEtat, newCap);
				break;
			case 10:
				System.out.print("Numéro de la chambre à consulter : ");
				int numCons = Integer.parseInt(sc.nextLine());
				Chambre trouvee = fh.consulterChambre(numCons);
				if (trouvee != null) {
					System.out.println(trouvee);
				}
				break;
			case 11:
				System.out.print("Catégorie à copier : ");
				int catCopie = Integer.parseInt(sc.nextLine());
				fh.copierCategorie(catCopie);
				System.out.println("Copie terminée dans categorie_" + catCopie + ".dat");
				break;
			case 12:
				Vector<Chambre> libres = fh.extraireLibres();
				System.out.println("=== Chambres libres ===");
				for (Chambre c : libres) {
					System.out.println(c);
				}
				break;
			case 13:
				fh.calculerRecettes();
				break;
			case 0:
				System.out.println("Au revoir !");
				break;
			default:
				System.out.println("Choix invalide.");
			}
		} while (choix != 0);

		sc.close();
	}

	
	public Chambre[] saisirTableau(int n, Scanner sc) {
		Chambre[] hotel = new Chambre[n];
		for(int i=0;i<n;i++) {
			hotel[i]=saisie(sc);
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

	public Chambre saisie(Scanner s) {
		System.out.println("enter catergorie  : ");
		int categorie = Integer.parseInt(s.nextLine());

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

		return new Chambre(prix, categorie, 'L', capacite);
	}

}


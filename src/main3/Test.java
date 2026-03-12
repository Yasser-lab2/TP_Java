package main3;
import java.io.*;

import java.util.*;

public class Test {
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "bibliotheque.ser";

    public static void main(String[] args) {
        Bibliothèque maBiblio = chargerDonnees();
        int choix;

        do {
            System.out.println("\n--- MENU GESTION BIBLIOTHÈQUE ---");
            System.out.println("1. Afficher les livres");
            System.out.println("2. Ajouter un livre");
            System.out.println("3. Rechercher par auteur");
            System.out.println("4. Sauvegarder et Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine(); 

            switch (choix) {
                case 1 -> System.out.println(maBiblio);
                case 2 -> ajouterUnLivre(maBiblio);
                case 3 -> rechercherLivre(maBiblio);
                case 4 -> sauvegarderDonnees(maBiblio);
                default -> System.out.println("Choix invalide.");
            }
        } while (choix != 4);
    }

    private static void ajouterUnLivre(Bibliothèque b) {
        System.out.print("Titre : ");
        String titre = scanner.nextLine();
        System.out.print("Nombre d'auteurs : ");
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] auteurs = new String[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Auteur " + (i + 1) + " : ");
            auteurs[i] = scanner.nextLine();
        }
        System.out.print("ISBN : ");
        String isbn = scanner.nextLine();
        System.out.print("Prix : ");
        double prix = scanner.nextDouble();

        if (b.ajouteLivre(new Livre(titre, auteurs, isbn, prix))) {
            System.out.println("Livre ajouté avec succès !");
        } else {
            System.out.println("Erreur : Bibliothèque pleine.");
        }
    }

    private static void rechercherLivre(Bibliothèque b) {
        System.out.print("Nom de l'auteur (ou début du nom) : ");
        String nom = scanner.nextLine();
        Vector<Livre> resultats = b.cherche(nom);
        if (resultats.isEmpty()) {
            System.out.println("Aucun livre trouvé.");
        } else {
            resultats.forEach(System.out::println);
        }
    }

    //  SÉRIALISATION 

    private static void sauvegarderDonnees(Bibliothèque b) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(b);
            System.out.println("Données sauvegardées. Au revoir !");
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    private static Bibliothèque chargerDonnees() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (Bibliothèque) ois.readObject();
            } catch (Exception e) {
                System.out.println("Erreur de chargement, création d'une nouvelle bibliothèque.");
            }
        }
        System.out.print("Nouvelle session. Capacité de la bibliothèque ? ");
        return new Bibliothèque(scanner.nextInt());
    }
}
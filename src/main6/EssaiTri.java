package main6;

import java.util.Scanner;

public class EssaiTri {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        TriSimple tri = new TriSimple(3, 2); 
        
        System.out.println("--- Programme de Test TriSimple ---");
        System.out.println("Commandes disponibles :");
        System.out.println("  a <nombres> : insérer des nombres (ex: a 5 2 9)");
        System.out.println("  s <nombres> : supprimer des nombres (ex: s 5 2)");
        System.out.println("  q           : quitter le programme");
        System.out.println("Etat initial : " + tri.toString());
        
        boolean continuer = true;
        
        while (continuer) {
            System.out.print("\n> ");
            String ligne = scanner.nextLine().trim();
            
            if (ligne.isEmpty()) continue;
            
            String[] parties = ligne.split("\\s+");
            String commande = parties[0].toLowerCase();
            
            switch (commande) {
                case "a":
                    for (int i = 1; i < parties.length; i++) {
                        try {
                            int val = Integer.parseInt(parties[i]);
                            tri.inserer(val);
                        } catch (NumberFormatException e) {
                            System.out.println("Ignoré : '" + parties[i] + "' n'est pas un entier valide.");
                        }
                    }
                    System.out.println(tri);
                    break;
                    
                case "s":
                    for (int i = 1; i < parties.length; i++) {
                        try {
                            int val = Integer.parseInt(parties[i]);
                            tri.supprimer(val);
                        } catch (NumberFormatException e) {
                            System.out.println("Ignoré : '" + parties[i] + "' n'est pas un entier valide.");
                        }
                    }
                    System.out.println(tri);
                    break;
                    
                case "q":
                    System.out.println("Fin du programme. Au revoir !");
                    continuer = false;
                    break;
                    
                default:
                    System.out.println("Commande non reconnue. Utilisez 'a', 's' ou 'q'.");
            }
        }
        
        scanner.close();
    }
}
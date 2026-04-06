package main7;

public class Test {

	public static void main(String[] args) {
        Agence agence = new Agence();
        
        Voiture v1 = new Voiture("Renault", "Clio", 2009, 45);
        Voiture v2 = new Voiture("Peugeot", "208", 2015, 60);
        Voiture v3 = new Voiture("Mercedes", "Classe A", 2020, 150);
        Voiture v4 = new Voiture("Renault", "Megane", 2009, 80);
        
        agence.ajouterVoiture(v1);
        agence.ajouterVoiture(v2);
        agence.ajouterVoiture(v3);
        agence.ajouterVoiture(v4);

        System.out.println("--- Q5 : Voitures à moins de 100 ---");
        agence.afficheSelection(new CriterePrix(100));

        System.out.println("\n--- Q7 : InterCritere (Renault, 2009, prix < 100) ---");
        InterCritere interC = new InterCritere();
        interC.addCritere(new CritereMarque("Renault"));
        interC.addCritere(new CritereAnnee(2009));
        interC.addCritere(new CriterePrix(100));
        agence.afficheSelection(interC);

        
        System.out.println("\n--- Q9 & Q10 : Tests des Locations ---");
        
        // On crée des clients dans le désordre alphabétique
        Client c1 = new Client("Zidane", "Zinedine", 11111, "M.");
        Client c2 = new Client("Mbappe", "Kylian", 22222, "M.");
        Client c3 = new Client("Abidal", "Eric", 33333, "M.");

        try {
            // Test d'ajout de location (Q9)
            System.out.println("> Location de voitures pour Abidal, Mbappe et Zidane...");
            agence.loueVoiture(c1, v1);
            agence.loueVoiture(c2, v2);
            agence.loueVoiture(c3, v3);
            
            // Test Q10 : L'affichage doit se faire dans l'ordre: Abidal, Mbappe, Zidane (grâce au TreeMap)
            agence.afficherLocations();
            
            // Test exception (Voiture déjà louée)
            System.out.println("\n> Tentative de location de la Clio (déjà louée par Zidane) par un autre client :");
            Client c4 = new Client("Griezmann", "Antoine", 44444, "M.");
            agence.loueVoiture(c4, v1); // Va déclencher l'exception
            
        } catch (Exception e) {
            System.out.println("Erreur interceptée : " + e.getMessage());
        }

        // Test de la restitution d'une voiture
        System.out.println("\n> Mbappe rend sa voiture...");
        agence.rendVoiture(c2);
        agence.afficherLocations();
	}
}
package main7;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

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
        // Q5: Ligne de code permettant d'afficher toutes les voitures dont le prix est inférieur à 100
        agence.afficheSelection(new CriterePrix(100));

        System.out.println("\n--- Q7 : InterCritere (Renault, 2009, prix < 100) ---");
        // Q7: Lignes de code pour créer un critère intersection
        InterCritere interC = new InterCritere();
        interC.addCritere(new CritereMarque("Renault"));
        interC.addCritere(new CritereAnnee(2009)); // Nécessite la classe CritereAnnee
        interC.addCritere(new CriterePrix(100));
        agence.afficheSelection(interC);

}
}

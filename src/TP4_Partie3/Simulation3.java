package TP4_Partie3;


public class Simulation3 {

    // Tableau partagé entre tous les threads (static = partagé)
    public static Jouet3[] lesJouets = new Jouet3[10];

    public static void main(String[] args) {
        // Créer les 10 jouets
        for (int i = 0; i < 10; i++) {
            lesJouets[i] = new Jouet3(i);
        }

        VerificateurJouet3 ahmed = new VerificateurJouet3("Ahmed");
        VerificateurJouet3 amine = new VerificateurJouet3("Amine");

        new Thread(ahmed).start();
        new Thread(amine).start();
    }
}

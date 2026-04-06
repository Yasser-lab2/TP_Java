package TP4_Partie2;

public class Simulation2 {

    // Tableau partagé entre tous les threads (static = partagé)
    public static Jouet2[] lesJouets = new Jouet2[10];

    public static void main(String[] args) {
        // Créer les 10 jouets
        for (int i = 0; i < 10; i++) {
            lesJouets[i] = new Jouet2(i);
        }

        VerificateurJouet2 ahmed = new VerificateurJouet2("Ahmed");
        VerificateurJouet2 amine = new VerificateurJouet2("Amine");

        new Thread(ahmed).start();
        new Thread(amine).start();
    }
}

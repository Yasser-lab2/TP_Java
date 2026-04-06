package TP4_Partie4;

public class Simulation4 {

    public static Jouet4[] lesJouets = new Jouet4[100];

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            lesJouets[i] = new Jouet4(i);
        }

        // Niveau 0, vitesse max 1000ms (lents)
        VerificateurJouet4 ahmed = new VerificateurJouet4("Ahmed", 0, 1000);
        VerificateurJouet4 amine = new VerificateurJouet4("Amine", 0, 1000);
        // Niveau 1, vitesse max 400ms (rapide) → attend après les autres
        VerificateurJouet4 bachir = new VerificateurJouet4("Bachir", 1, 400);

        new Thread(ahmed).start();
        new Thread(amine).start();
        new Thread(bachir).start();
    }
}

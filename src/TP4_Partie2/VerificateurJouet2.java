package TP4_Partie2;

public class VerificateurJouet2 implements Runnable {

    private String nom;

    public VerificateurJouet2(String nom) {
        this.nom = nom;
    }

    @Override
    public void run() {
        // Au lieu d'appeler verifieJouet(),
        // on appelle la méthode du Jouet en lui passant "this" (soi-même)
        for (int i = 0; i < 10; i++) {
            Simulation2.lesJouets[i].tuEsVerifiePar(this);
        }
    }

    public String getNom() {
        return nom;
    }
}

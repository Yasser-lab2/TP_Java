package TP4_Partie3;


public class VerificateurJouet3 implements Runnable {

    private String nom;

    public VerificateurJouet3(String nom) {
        this.nom = nom;
    }

    @Override
    public void run() {
        // Au lieu d'appeler verifieJouet(),
        // on appelle la méthode du Jouet en lui passant "this" (soi-même)
        for (int i = 0; i < 10; i++) {
        	if(!Simulation3.lesJouets[i].isDejaVerifie()){
            Simulation3.lesJouets[i].tuEsVerifiePar(this);
        	}
        }
    }

    public String getNom() {
        return nom;
    }
}

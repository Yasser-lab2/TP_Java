package TP4_Partie4;

import TP4_Partie3.Simulation3;

public class VerificateurJouet4 implements Runnable {

    private String nom;
    private int niveau;   // 0 = Ahmed/Amine, 1 = Bachir
    private int vitesse;  // durée max de vérification en ms

    // Nouveau constructeur avec niveau et vitesse
    public VerificateurJouet4(String nom, int niveau, int vitesse) {
        this.nom = nom;
        this.niveau = niveau;
        this.vitesse = vitesse;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
        	
        	if(!Simulation4.lesJouets[i].isDejaVerifie()){
                Simulation4.lesJouets[i].tuEsVerifiePar(this);
            	}
        }
    }

    public String getNom()  { return nom; }
    public int getNiveau()  { return niveau; }
    public int getVitesse() { return vitesse; }
}

package TP4_Partie4;

public class Jouet4 {

    private int numero;
    // 0 = non vérifié, 1 = vérifié par Ahmed/Amine, 2 = vérifié par Bachir
    private int niveauVerification = 0;

    public Jouet4(int numero) {
        this.numero = numero;
    }
    public boolean isDejaVerifie() { return niveauVerification==1;};

    public synchronized void tuEsVerifiePar(VerificateurJouet4 v) {
        int niveau = v.getNiveau();

        // CAS 1 : Ce vérificateur a déjà fait son travail sur ce jouet
        // (ex: Ahmed a déjà vérifié, Amine arrive → il passe)
        if (niveauVerification > niveau) {
            System.out.println(v.getNom() + " → jouet n°" + numero + " déjà traité à son niveau.");
            return;
        }

        // CAS 2 : Bachir (niveau 1) arrive mais personne n'a encore
        // vérifié ce jouet (niveauVerification = 0) → il attend
        if (niveau == 1 && niveauVerification < 1) {
            System.out.println(v.getNom() + " ATTEND que le jouet n°" + numero + " soit pré-vérifié...");

            // BOUCLE while  :
            // Après le réveil, on re-vérifie la condition
            // car plusieurs threads peuvent être réveillés
            while (niveauVerification < 1) {
                try {
                    wait(); // Bachir s'endort ET libère le verrou
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // CAS 3 : On effectue la vérification
        System.out.println(v.getNom() + " COMMENCE à vérifier le jouet n°" + numero);

        try {
            // Bachir est plus rapide (vitesse plus faible)
            Thread.sleep((long)(Math.random() * v.getVitesse()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        niveauVerification++; // on passe au niveau suivant
        System.out.println(v.getNom() + " A FINI le jouet n°" + numero
                + " [niveau=" + niveauVerification + "]");

        // RÉVEILLE tous les threads qui attendent sur cet objet
        // (principalement Bachir, s'il attendait)
        notifyAll();
    }
}



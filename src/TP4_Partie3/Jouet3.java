package TP4_Partie3;

public class Jouet3 {

    private int numero;
    private boolean dejaVerifie = false; // false = pas encore vérifié

    public Jouet3(int numero) {
        this.numero = numero;
    }

    public synchronized void tuEsVerifiePar(VerificateurJouet3 v) {
        // Si déjà vérifié par quelqu'un d'autre, on sort immédiatement
        if (dejaVerifie) {
            System.out.println(v.getNom() + " passe : jouet n°" + numero + " déjà vérifié.");
            return; // sortie immédiate de la méthode
        }

        // On marque immédiatement comme "en cours/vérifié"
        // AVANT le sleep, pour qu'un autre thread ne rentre pas
        dejaVerifie = true;

        System.out.println(v.getNom() + " COMMENCE à vérifier le jouet n°" + numero);

        try {
            Thread.sleep((long)(Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(v.getNom() + " A FINI de vérifier le jouet n°" + numero);
    }

    public int getNumero() { return numero; }
    public boolean isDejaVerifie() { return dejaVerifie; }
}
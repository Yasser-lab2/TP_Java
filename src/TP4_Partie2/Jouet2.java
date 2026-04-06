package TP4_Partie2;

public class Jouet2 {

    private int numero; // numéro du jouet (0 à 9)

    public Jouet2(int numero) {
        this.numero = numero;
    }

    // synchronized = un seul thread peut exécuter cette méthode
    // à la fois pour CE jouet précis
    public synchronized void tuEsVerifiePar(VerificateurJouet2 v) {
        System.out.println(v.getNom() + " COMMENCE à vérifier le jouet n°" + numero);

        try {
            Thread.sleep((long)(Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(v.getNom() + " A FINI de vérifier le jouet n°" + numero);
    }

    public int getNumero() {
        return numero;
    }
}

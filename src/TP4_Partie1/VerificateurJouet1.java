package TP4_Partie1;

//VerificateurJouet.java — Partie I
public class VerificateurJouet1 implements Runnable {
 private String nom;

 public VerificateurJouet1(String nom) {
     this.nom = nom;
 }

 @Override
 public void run() {
     for (int i = 0; i < 10; i++) {
         verifieJouet(i);
     }
 }

 public void verifieJouet(int numero) {
     System.out.println(nom + " commence à vérifier le jouet " + numero);
     try {
         Thread.sleep((long)(Math.random() * 1000));
     } catch (InterruptedException e) {
         e.printStackTrace();
     }
     System.out.println(nom + " a fini de vérifier le jouet " + numero);
 }

 public String getNom() { return nom; }
}

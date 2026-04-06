package TP4_Partie1;

//Simulation.java — Partie I
public class Simulation1 {
 public static void main(String[] args) {
     VerificateurJouet1 ahmed = new VerificateurJouet1("Ahmed");
     VerificateurJouet1 amine = new VerificateurJouet1("Amine");

     Thread t1 = new Thread(ahmed);
     Thread t2 = new Thread(amine);

     t1.start();
     t2.start();
 }
}

package main4;
import java.util.StringTokenizer;

public class Exercice1 {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Erreur : Il faut exactement 2 arguments sur la ligne de commande.");
            System.err.println("Arguments : <double> <entier_positif>");
            return;
        }

        double x = 0.0;
        int nbDecimales = 0;

        try {
            x = Double.parseDouble(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Erreur : Le premier argument '" + args[0] + "' n'est pas un double valide.");
            return;
        }
        try {
            nbDecimales = Integer.parseInt(args[1]);
            if (nbDecimales < 0) {
                System.err.println("Erreur : Le deuxième argument (nombre de décimales) doit être positif ou nul.");
                return;
            }
        } catch (NumberFormatException e) {
            System.err.println("Erreur : Le deuxième argument '" + args[1] + "' n'est pas un entier valide.");
            return;
        }

   
        String resultat = tronque(x, nbDecimales);
        System.out.println(resultat);
    }


    public static String tronque(double x, int nbDecimales) {
        
        String chaineX = String.valueOf(x);
        StringTokenizer st = new StringTokenizer(chaineX, ".");
        
        String partieEntiere = st.nextToken(); 
        if (nbDecimales == 0) {
            return partieEntiere;
        }
        if (st.hasMoreTokens()) {
            String partieDecimale = st.nextToken();
            if (partieDecimale.length() > nbDecimales) {
                partieDecimale = partieDecimale.substring(0, nbDecimales);
            }
            
            return partieEntiere + "." + partieDecimale;
        }

        return partieEntiere;
    }
}

package main6;

public class TriSimple {
    private int[] tableau;
    private int nbElements; 
    private int increment;
    
  
    private static final int CAPACITE_DEFAUT = 10;
    private static final int INCREMENT_DEFAUT = 5;

   
    public TriSimple() {
        this(CAPACITE_DEFAUT, INCREMENT_DEFAUT);
    }

  
    public TriSimple(int capaciteInitiale, int increment) {
        if (capaciteInitiale <= 0) capaciteInitiale = 10;
        if (increment <= 0) increment = 5;
        
        this.tableau = new int[capaciteInitiale];
        this.nbElements = 0;
        this.increment = increment;
    }

  
    public void inserer(int entier) {
        
        if (nbElements == tableau.length) {
            agrandir();
        }

        int i = nbElements - 1;
        while (i >= 0 && tableau[i] > entier) {
            tableau[i + 1] = tableau[i];
            i--;
        }
        
        tableau[i + 1] = entier;
        nbElements++;
    }

   
    public void supprimer(int entier) {
        int indexTrouve = -1;
        
        
        for (int i = 0; i < nbElements; i++) {
            if (tableau[i] == entier) {
                indexTrouve = i;
                break; 
            }
        }

        
        if (indexTrouve != -1) {
            for (int i = indexTrouve; i < nbElements - 1; i++) {
                tableau[i] = tableau[i + 1];
            }
            nbElements--;

            if (tableau.length - nbElements >= 2 * increment) {
                diminuer();
            }
        }
    }

   
    private void agrandir() {
        int nouvelleCapacite = tableau.length + increment;
        int[] nouveauTableau = new int[nouvelleCapacite];
        
        for (int i = 0; i < nbElements; i++) {
            nouveauTableau[i] = tableau[i];
        }
        tableau = nouveauTableau;
    }

    private void diminuer() {
        int nouvelleCapacite = tableau.length - increment;
        int[] nouveauTableau = new int[nouvelleCapacite];
        
        for (int i = 0; i < nbElements; i++) {
            nouveauTableau[i] = tableau[i];
        }
        tableau = nouveauTableau;
    }

    @Override
    public String toString() {
        if (nbElements == 0) {
            return "[] (Capacité actuelle : " + tableau.length + ")";
        }
        
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < nbElements; i++) {
            sb.append(tableau[i]);
            if (i < nbElements - 1) {
                sb.append(", ");
            }
        }
        sb.append("] (Capacité actuelle : ").append(tableau.length).append(")");
        
        return sb.toString();
    }
}

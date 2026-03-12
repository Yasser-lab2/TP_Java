package main6;

public class TriSimple {
    private int[] tableau;
    private int nbElements; // Nombre d'éléments réellement présents dans le tableau
    private int increment;
    
    // Valeurs par défaut
    private static final int CAPACITE_DEFAUT = 10;
    private static final int INCREMENT_DEFAUT = 5;

    /**
     * Constructeur sans paramètre
     */
    public TriSimple() {
        this(CAPACITE_DEFAUT, INCREMENT_DEFAUT);
    }

    /**
     * Constructeur avec paramètres
     * @param capaciteInitiale La taille de départ du tableau
     * @param increment La valeur d'agrandissement/réduction
     */
    public TriSimple(int capaciteInitiale, int increment) {
        if (capaciteInitiale <= 0) capaciteInitiale = 10;
        if (increment <= 0) increment = 5;
        
        this.tableau = new int[capaciteInitiale];
        this.nbElements = 0;
        this.increment = increment;
    }

    /**
     * Insère un entier tout en conservant l'ordre croissant.
     */
    public void inserer(int entier) {
        // Agrandissement si le tableau est plein
        if (nbElements == tableau.length) {
            agrandir();
        }

        // Trouver la position d'insertion et décaler les éléments plus grands vers la droite
        int i = nbElements - 1;
        while (i >= 0 && tableau[i] > entier) {
            tableau[i + 1] = tableau[i];
            i--;
        }
        
        // Insérer le nouvel élément
        tableau[i + 1] = entier;
        nbElements++;
    }

    /**
     * Supprime la première occurrence trouvée d'un entier.
     */
    public void supprimer(int entier) {
        int indexTrouve = -1;
        
        // Recherche de l'entier
        for (int i = 0; i < nbElements; i++) {
            if (tableau[i] == entier) {
                indexTrouve = i;
                break; // On ne retire qu'une seule occurrence
            }
        }

        // Si l'entier est présent, on le supprime par décalage vers la gauche
        if (indexTrouve != -1) {
            for (int i = indexTrouve; i < nbElements - 1; i++) {
                tableau[i] = tableau[i + 1];
            }
            nbElements--;

            // Vérification pour la réduction de la taille
            if (tableau.length - nbElements >= 2 * increment) {
                diminuer();
            }
        }
    }

    /**
     * Agrandit le tableau en ajoutant l'incrément à la capacité actuelle.
     */
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

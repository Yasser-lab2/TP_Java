package main3;
import java.io.Serializable;
import java.util.Arrays;

public class Livre implements Serializable {
    private String titre;
    private String[] auteurs;
    private String isbn;
    private double prix;

    public Livre(String titre, String[] auteurs, String isbn, double prix) {
        this.titre = titre;
        this.auteurs = auteurs;
        this.isbn = isbn;
        this.prix = prix;
    }

    // Méthode pour vérifier si un auteur correspond (pour la question e)
    public boolean aPourAuteur(String nomAuteur) {
        for (String a : auteurs) {
            if (a.toLowerCase().startsWith(nomAuteur.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Livre [Titre: %s, Auteurs: %s, ISBN: %s, Prix: %.2f€]", 
                titre, Arrays.toString(auteurs), isbn, prix);
    }
}
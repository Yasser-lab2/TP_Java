package main3;

import java.io.Serializable;
import java.util.Vector;

public class Bibliothèque implements Serializable {
    private Livre[] livres;
    private int nbLivres;

    public Bibliothèque(int capaciteMax) {
        this.livres = new Livre[capaciteMax];
        this.nbLivres = 0;
    }

    public int capacite() {
        return livres.length;
    }

    public int size() {
        return nbLivres;
    }

    public boolean ajouteLivre(Livre l) {
        if (nbLivres < livres.length) {
            livres[nbLivres++] = l;
            return true;
        }
        return false;
    }

    public Vector<Livre> cherche(String auteur) {
        Vector<Livre> resultat = new Vector<>();
        for (int i = 0; i < nbLivres; i++) {
            if (livres[i].aPourAuteur(auteur)) {
                resultat.add(livres[i]);
            }
        }
        return resultat;
    }

    @Override
    public String toString() {
        if (nbLivres == 0) return "La bibliothèque est vide.";
        StringBuilder sb = new StringBuilder("Contenu de la bibliothèque :\n");
        for (int i = 0; i < nbLivres; i++) {
            sb.append("- ").append(livres[i].toString()).append("\n");
        }
        return sb.toString();
    }
}
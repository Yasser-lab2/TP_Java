package main3;

import java.util.Vector; 

public class Bibliothèque implements java.io.Serializable {
    private Livre[] collection;
    private int nbLivres;

    public Bibliothèque(int capaciteMax) {
        this.collection = new Livre[capaciteMax]; 
        this.nbLivres = 0;
    }

    public int capacite() {
        return collection.length; 
    }

    public boolean ajouteLivre(Livre livre) {
        if (nbLivres < collection.length) {
            collection[nbLivres] = livre; 
            nbLivres++;
            return true;
        }
        return false;
    }

    public int size() {
        return nbLivres;
    }

    @Override
    public String toString() {
        String res = "Contenu de la bibliothèque :\n";
        for (int i = 0; i < nbLivres; i++) {
            res += collection[i].tostring() + "\n"; 
        	
        }
        return res;
    }

 
    public Vector<Livre> cherche(String auteur) {
        Vector<Livre> resultat = new Vector<Livre>(); 
        for (int i = 0; i < nbLivres; i++) {
            String[] auteurs = collection[i].getAuteurs();
            for (String a : auteurs) {
                if (a.contains(auteur)) {
                    resultat.addElement(collection[i]);
                    
                }
            }
        }
        return resultat;
    }
    
    
    
    
    
}
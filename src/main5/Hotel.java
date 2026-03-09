package main5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

import main5.Chambre;

public class Hotel {

	private Vector<Chambre> chbrs = new Vector<Chambre>();

	public Hotel() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
//		Chambre C1 = saisie();


//		System.out.println(C1.toString());

	}

	
	public Chambre[] saisirTableau(int n) {
		Chambre[] hotel = new Chambre[n];
		for(int i=0;i<n;i++) {
			hotel[i]=saisie();
		}
		return hotel;
	}
	
	public void affichercat(Chambre [] t, int cat) {
		for(Chambre c : t) { 
			if(c.getCategorie()==cat) {
				System.out.println(c);
			}
		}
	}
	
	public void trierParCapacite(Chambre[] t) {
        for (int i = 0; i < t.length - 1; i++) {
            for (int j = 0; j < t.length - i - 1; j++) {
                if (t[j].getCapacite() > t[j+1].getCapacite()) {
                    Chambre temp = t[j];
                    t[j] = t[j+1];
                    t[j+1] = temp;
                }
            }
        }
    }
	

	public void afficher(Chambre [] t) {
		for(Chambre c : t) { 
				System.out.println(c);
		}
	}

	public Chambre saisie() {
		
		Scanner s = new Scanner(System.in);
		System.out.println("enter catergorie  : ");
		String categorie = s.nextLine();
		
		System.out.println("enter prix : ");
		String prix = s.nextLine();

		System.out.println("enter capacite: ");
		String capacite = s.nextLine();

		System.out.println("enter numero: ");
		String numero = s.nextLine();

		Chambre C1 = new Chambre(Integer.parseInt(numero), Integer.parseInt(prix), Integer.parseInt(categorie), "L",
				Integer.parseInt(capacite));
		
		return C1;
	}

}


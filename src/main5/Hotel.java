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

		Hotel h = new Hotel();

		h.listChambreSaisie();

		h.afficher();

		h.trier();

		h.afficher();

//		System.out.println(C1.toString());

	}

	public void trier() {

		Collections.sort(chbrs);

	}

	public void listChambreSaisie() {
		int count = Integer.parseInt(new Scanner(System.in).nextLine());
		for (int i = 0; i < count; i++) {
			Chambre c = saisie();

			chbrs.add(c);

		}
	}

	public void afficherCat(int catergorie) {
		for (Iterator iterator = chbrs.iterator(); iterator.hasNext();) {
			Chambre c = (Chambre) iterator.next();
			if (c.getCategorie() == catergorie) {

				System.out.println(c);

			}
		}
	}

	public void afficher() {
		for (Iterator iterator = chbrs.iterator(); iterator.hasNext();) {
			Chambre c = (Chambre) iterator.next();

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

	public void serialize() {

	
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("chambre.dat"));
			try {
				// On écrit objet, puis on vide le buffer
				oos.writeObject(chbrs);
				oos.flush();

			}
			finally {
				oos.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public Vector<Chambre> deserialize() {
//		
//		
//		
//		try {
//
//			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("chambre.dat"));
//			try {
//				// On cast l'entrée
//				main5.Chambre derserializedchambrese = (Chambre) ois.readObject();
//				// Puis on affiche
//				System.out.println(newch.toString());
//				return derserializedchambrese;
//			} finally {
//				ois.close();
//			}
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//
//	}
//
}

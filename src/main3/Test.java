package main3;

import java.io.*;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String[] Auteurs = { "Ayoub" };
//
//		Livre L1 = new Livre("Batresyyg", Auteurs, "123-8387", 30);
//		Livre L2 = new Livre("jwhijowps", Auteurs, "18728", 40);
//		Livre L3 = new Livre("Tasser", Auteurs, "213-8387", 90);
//		System.out.println(L1.tostring());
//		Bibliothèque b = new Bibliothèque(12);

//		b.ajouteLivre(L1);
//		b.ajouteLivre(L2);
//		b.ajouteLivre(L3);
		Bibliothèque newbible;

//		System.out.println(b.toString());

		// Imaginons qu'on ait une classe Article avec les attributs String titre et
		// auteur
		try {


			
//			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("fichierObjet"));
//			try {
//				// On écrit objet, puis on vide le buffer
//				oos.writeObject(b);
//				oos.flush();
//
//			}
//
//			finally {
//				oos.close();
//			}

			// On fait l'opération inverse (lecture)
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("fichierObjet"));
			try {
				// On cast l'entrée
				newbible = (Bibliothèque) ois.readObject();
				// Puis on affiche
				System.out.println(newbible.toString());
			} finally {
				ois.close();
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}

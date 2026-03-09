package main5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class Fichier_Hotel {

	private String fichier = "chambres.dat";

	public Fichier_Hotel() {
		// TODO Auto-generated constructor stub
	}

	public void sauvegarderChambres(Vector<Chambre> v) {
	    try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("chambres.dat"))) {
	        for (Chambre c : v) {
	            dos.writeInt(c.getNumero());
	            dos.writeInt(c.getCategorie());
	            dos.writeDouble(c.getPrix());
	            dos.writeInt(c.getCapacite());
	            dos.writeChar(c.getEtat());
	        }
	        System.out.println("Sauvegarde terminée.");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public void copierCategorie(int catCible) {
	    try (DataInputStream dis = new DataInputStream(new FileInputStream("chambres.dat"));
	         DataOutputStream dos = new DataOutputStream(new FileOutputStream("categorie_" + catCible + ".dat"))) {
	        
	        while (true) { // La boucle s'arrête via l'EOFException
	            int num = dis.readInt();
	            int cat = dis.readInt();
	            double prix = dis.readDouble();
	            int cap = dis.readInt();
	            char etat = dis.readChar();

	            if (cat == catCible) {
	                dos.writeInt(num);
	                dos.writeInt(cat);
	                dos.writeDouble(prix);
	                dos.writeInt(cap);
	                dos.writeChar(etat);
	            }
	        }
	    } catch (EOFException e) {
	        // Fin normale du fichier
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public Vector<Chambre> extraireLibres() {
	    Vector<Chambre> libres = new Vector<>();
	    try (DataInputStream dis = new DataInputStream(new FileInputStream("chambres.dat"))) {
	        while (true) {
	            int num = dis.readInt();
	            int cat = dis.readInt();
	            double prix = dis.readDouble();
	            int cap = dis.readInt();
	            char etat = dis.readChar();

	            if (etat == 'L') {
	                libres.add(new Chambre(num, cat, prix, cap, etat));
	            }
	        }
	    } catch (EOFException e) {
	        // Fin du fichier atteinte
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return libres;
	}
	public void calculerRecettes() {
	    double maxPossible = 0;
	    double réelle = 0;

	    try (DataInputStream dis = new DataInputStream(new FileInputStream("chambres.dat"))) {
	        while (true) {
	            dis.readInt(); // On saute le numéro
	            dis.readInt(); // On saute la catégorie
	            double prix = dis.readDouble();
	            dis.readInt(); // On saute la capacité
	            char etat = dis.readChar();

	            maxPossible += prix;
	            if (etat == 'O') {
	                réelle += prix;
	            }
	        }
	    } catch (EOFException e) {
	        System.out.println("Recette Max (si tout occupé) : " + maxPossible);
	        System.out.println("Recette Réelle (occupées) : " + réelle);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}

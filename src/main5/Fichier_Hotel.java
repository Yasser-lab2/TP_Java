package main5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;


public class Fichier_Hotel {

	private String fichier = "chambres.dat";

	public Fichier_Hotel() {
		// TODO Auto-generated constructor stub
	}

	
	public Vector<Chambre> lireToutesChambres() {
	    Vector<Chambre> chambres = new Vector<>();
	    try (DataInputStream dis = new DataInputStream(new FileInputStream(fichier))) {
	        while (true) {
	            int num = dis.readInt();
	            int cat = dis.readInt();
	            double prix = dis.readDouble();
	            int cap = dis.readInt();
	            char etat = dis.readChar();
	            chambres.add(new Chambre(num, prix, cat, etat, cap));
	        }
	    } catch (EOFException e) {
	        // Fin du fichier
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return chambres;
	}

	
	public void sauvegarderChambres(Vector<Chambre> v) {
	    try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fichier))) {
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

	
	public void ajouterChambre(Chambre c) {
	    try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fichier, true))) {
	        dos.writeInt(c.getNumero());
	        dos.writeInt(c.getCategorie());
	        dos.writeDouble(c.getPrix());
	        dos.writeInt(c.getCapacite());
	        dos.writeChar(c.getEtat());
	        System.out.println("Chambre " + c.getNumero() + " ajoutée.");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	
	public void supprimerChambre(int numero) {
	    Vector<Chambre> chambres = lireToutesChambres();
	    boolean trouvee = false;
	    for (int i = 0; i < chambres.size(); i++) {
	        if (chambres.get(i).getNumero() == numero) {
	            chambres.remove(i);
	            trouvee = true;
	            break;
	        }
	    }
	    if (trouvee) {
	        sauvegarderChambres(chambres);
	        System.out.println("Chambre " + numero + " supprimée.");
	    } else {
	        System.out.println("Chambre " + numero + " non trouvée.");
	    }
	}

	// 4.a - Modification d'une chambre par numéro
	public void modifierChambre(int numero, double nouveauPrix, int nouvelleCat, char nouvelEtat, int nouvelleCap) {
	    Vector<Chambre> chambres = lireToutesChambres();
	    boolean trouvee = false;
	    for (Chambre c : chambres) {
	        if (c.getNumero() == numero) {
	            c.setPrix(nouveauPrix);
	            c.setCategorie(nouvelleCat);
	            c.setEtat(nouvelEtat);
	            c.setCapacite(nouvelleCap);
	            trouvee = true;
	            break;
	        }
	    }
	    if (trouvee) {
	        sauvegarderChambres(chambres);
	        System.out.println("Chambre " + numero + " modifiée.");
	    } else {
	        System.out.println("Chambre " + numero + " non trouvée.");
	    }
	}

	
	public Chambre consulterChambre(int numero) {
	    try (DataInputStream dis = new DataInputStream(new FileInputStream(fichier))) {
	        while (true) {
	            int num = dis.readInt();
	            int cat = dis.readInt();
	            double prix = dis.readDouble();
	            int cap = dis.readInt();
	            char etat = dis.readChar();
	            if (num == numero) {
	                System.out.println("Chambre trouvée : " + num);
	                return new Chambre(num, prix, cat, etat, cap);
	            }
	        }
	    } catch (EOFException e) {
	        System.out.println("Chambre " + numero + " non trouvée.");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	public void copierCategorie(int catCible) {
	    try (DataInputStream dis = new DataInputStream(new FileInputStream(fichier));
	         DataOutputStream dos = new DataOutputStream(new FileOutputStream("categorie_" + catCible + ".dat"))) {
	        
	        while (true) { 
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
	    try (DataInputStream dis = new DataInputStream(new FileInputStream(fichier))) {
	        while (true) {
	            int num = dis.readInt();
	            int cat = dis.readInt();
	            double prix = dis.readDouble();
	            int cap = dis.readInt();
	            char etat = dis.readChar();

	            if (etat == 'L') {
	                libres.add(new Chambre(num, prix, cat, etat, cap));
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

	    try (DataInputStream dis = new DataInputStream(new FileInputStream(fichier))) {
	        while (true) {
	            dis.readInt(); 
	            dis.readInt(); 
	            double prix = dis.readDouble();
	            dis.readInt(); 
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

package main7;

import java.util.*;

public class Agence {
	List<Voiture> voitures;
	
	// Q10: Remplacement de la HashMap par une TreeMap
	Map<Client, Voiture> locations = new TreeMap<Client, Voiture>();

	public Agence() {
		this.voitures = new ArrayList<>();
	}

	public void loueVoiture(Client client, Voiture v) throws Exception {
		if(!voitures.contains(v)) {
			throw new Exception("Cette voiture n'existe pas dans l'agence");
		}
		if (estLoue(v)) {
			throw new Exception("Cette voiture est déjà louée");
		}
		if(estLoueur(client)) {
			throw new Exception("Ce client a déjà loué une voiture");
		}
		locations.put(client, v);
	}

	public boolean estLoueur(Client c) {
		return (locations.containsKey(c)); 
	}

	public boolean estLoue(Voiture v) {
		return locations.containsValue(v);
	}

	public void rendVoiture(Client client) {
		locations.remove(client);
	}
	
	// Correction du nom de la méthode (Q8)
    public Iterator<Voiture> lesVoituresLouees(){
    	return locations.values().iterator();
    }
    
   	public Iterator<Voiture> selectionne(Critere c) {
		List<Voiture> l = new ArrayList<Voiture>();
		for (Voiture e : voitures) {
			if (c.estSatisfaitPar(e)) {
				l.add(e);
			}
		}
		return l.iterator();
	}

	public void afficheSelection(Critere c) {
		Iterator<Voiture> it = selectionne(c);
		while (it.hasNext()) {
			System.out.println(it.next().toString()); // Appelle désormais la bonne méthode toString()
		}
	}

	public void ajouterVoiture(Voiture v) {
		voitures.add(v);
	}
	
	// Méthode bonus utile pour la Q10 pour vérifier l'affichage alphabétique
	public void afficherLocations() {
		System.out.println("--- État des Locations actuelles ---");
		for (Map.Entry<Client, Voiture> entry : locations.entrySet()) {
			System.out.println(entry.getKey().toString() + " a loué -> " + entry.getValue().getMarque() + " " + entry.getValue().getModel());
		}
	}
}
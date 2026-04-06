package main7;

import java.util.*;

public class Agence {
	List<Voiture> voitures;
	Map<Client, Voiture> locations = new HashMap<Client, Voiture>();

	public Agence() {
		// TODO Auto-generated constructor stub
		this.voitures = new ArrayList<>();
	}

	public void loueVoiture(Client client, Voiture v) throws Exception {
		if(!voitures.contains(v)) {
			throw new Exception("Cette voiture n'existe pas");
		}
		if (estLoue(v)) {
			throw new Exception("cette voiture est déjà loué");
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
	
    public Iterator<Voiture> lesVoituresL(){
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
			System.out.println(it.next().tostring());

		}
	}

	public void ajouterVoiture(Voiture v) {
		voitures.add(v);
	}

}

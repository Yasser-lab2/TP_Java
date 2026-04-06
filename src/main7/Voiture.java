package main7;
import java.util.Objects;

public class Voiture {
    
	private String marque;
	private String model;
	private int annee;
	private int prix;
	
	public Voiture(String marque, String model, int annee, int prix) {
		// TODO Auto-generated constructor stub
		this.marque=marque;
		this.model=model;
		this.annee=annee;
		this.prix=prix;
	}

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voiture voiture = (Voiture) o;
        return annee == voiture.annee && 
               prix == voiture.prix && 
               Objects.equals(marque, voiture.marque) && 
               Objects.equals(model, voiture.model);
    }
	
	public String tostring() {
		return "la marque : "+ marque + ", Le model : "+ model + ", L'année de production : "+ annee +", Le prix : "+prix;
	}

}

package main7;

public class CritereAnnee implements Critere {

	private int annee;
    public CritereAnnee(int annee) { this.annee = annee; }
    
    @Override
    public boolean estSatisfaitPar(Voiture v) {
        return v.getAnnee() == annee;
    }

}

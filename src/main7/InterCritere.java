package main7;
import java.util.*;
public class InterCritere implements Critere {
    
	List<Critere> lesCritere;
	public InterCritere() {
		// TODO Auto-generated constructor stub
		this.lesCritere=new ArrayList<Critere>();
	}
	
	public void addCritere(Critere c) {
		lesCritere.add(c);
	}
	
	@Override
	public boolean estSatisfaitPar(Voiture v) {
		// TODO Auto-generated method stub
		
		for (Critere critere : lesCritere) {
			if (!critere.estSatisfaitPar(v)) {
				return false;
			}
		}
		
		return true;
	}

}

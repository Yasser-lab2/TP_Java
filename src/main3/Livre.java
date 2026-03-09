package main3;
import java.util.Arrays;

public class Livre implements java.io.Serializable {

	public Livre(String titre, String[] Auteurs, String ISBN, int Prix ) {
		// TODO Auto-generated constructor stub
		this.titre=titre;
		this.Auteurs=Auteurs;
		this.ISBN=ISBN;
		this.Prix=Prix;
	}
	private String titre;
	private String[] Auteurs;
	private String ISBN;
	private int Prix;
	
	public String tostring() {
		String listeAuteurs = Arrays.toString(this.Auteurs);
		return "Titre : " + titre + ", Auteurs : "+ listeAuteurs + ", ISBN : "+ ISBN + ", Prix : "+ Prix +"$";
	}
	public String[] getAuteurs() {
		return Auteurs;
	}
	
	
	
	

}

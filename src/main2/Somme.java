package main2;

public class Somme {

	public Somme() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int somme=0;
		for(int i=0; i<args.length;i++) {
			try {
				somme+=Integer.parseInt(args[i]);	
			}catch(NumberFormatException e){
				System.out.println("L'argument n'est pas correct");
			}
			
		}
		System.out.println("la somme est : "+somme);
	}


}

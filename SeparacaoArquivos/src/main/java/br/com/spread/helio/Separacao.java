package br.com.spread.helio;

public class Separacao {

	public static void main(String[] args) {
		
		Thread t = new Thread(new SeparaMain());
		System.out.println("Iniciando thread...");
		t.run();
		

	}

}

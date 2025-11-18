package app;

import java.util.Scanner;

public class Menu {
		
	public Menu() {}
	
	public void mostrarOpciones() {
		this.puntosElegibles();
		System.out.print("Ingrese su opcion: ");
		Scanner scanMenu = null;
		try {
			scanMenu = new Scanner(System.in);
			int opcion = scanMenu.nextInt();
			
			while(opcion < 1 && opcion > 10) {
				limpiarPantalla();
				System.out.println("Opcion invalida, debe ser un numero entre 1 y 9. Intente nuevamente.");
				this.puntosElegibles();
				System.out.print("Ingrese su opcion: ");
				opcion = scanMenu.nextInt();
			}
			
			switch(opcion) {
			case 1:
				this.opcion01();
				break;
			case 2: 
				this.opcion02();
				break;
			case 3: 
				this.opcion03();
				break;
			case 4:
				this.opcion04();
				break;
			case 5:
				this.opcion05();
				break;
			case 6:
				this.opcion06();
				break;
			case 7:
				this.opcion07();
				break;
			case 8:
				this.opcion08();
				break;
			case 9:
				return;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private static void limpiarPantalla() {
	    System.out.print("\033[H\033[2J");
	    System.out.flush();
	}
	
	private void puntosElegibles() {
		System.out.println("Elija una de las siguientes opciones:"); 
		System.out.println("01. Mostrar roles faltantes para una cancion determinada.");
		System.out.println("02. Mostrar roles faltantaes para todas las canciones del recital.");
		System.out.println("03. Contratar artista para una cancion determinada.");
		System.out.println("04. Contratar artista para todas las canciones del recital.");
		System.out.println("05. Entrenar artista");
		System.out.println("06. Listar artistas contratados, su informacion relevante y su costo.");
		System.out.println("07. Listar canciones con su estado actual.");
		System.out.println("08. [PROLOG]");
		System.out.println("09. Salir");
	}
	
	private void opcion01() {
		System.out.println("Roles faltantes para una cancion");
		return;
	}
	
	private void opcion02() {
		System.out.println("Roles faltantes para una cancion");
		return;
	}
	
	private void opcion03() {
		System.out.println("Roles faltantes para una cancion");
		return;
	}
	
	private void opcion04() {
		System.out.println("Roles faltantes para una cancion");
		return;
	}
	
	private void opcion05() {
		System.out.println("Roles faltantes para una cancion");
		return;
	}
	
	private void opcion06() {
		System.out.println("Roles faltantes para una cancion");
		return;
	}
	
	private void opcion07() {
		System.out.println("Roles faltantes para una cancion");
		return;
	}
	
	private void opcion08() {
		System.out.println("Roles faltantes para una cancion");
		return;
	}
}

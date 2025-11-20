package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import artistas.Artista;
import artistas.ArtistaContratado;
import artistas.Rol;
import canciones.Cancion;
import recitales.Recital;

public class Menu {
		
	public Menu() {}
	
	public void mostrarOpciones(Recital recital, Set<Artista> artistasBase, Set<ArtistaContratado> artistasContratados, Set<Rol> roles) {
		limpiarPantalla();
		puntosElegibles();
		System.out.print("Ingrese su opcion: ");
		Scanner scanMenu = null;
		try {
			scanMenu = new Scanner(System.in);
			int opcion = scanMenu.nextInt();
			
			while(opcion < 1 || opcion > 10) {
				limpiarPantalla();
				System.out.println("Opcion invalida, debe ser un numero entre 1 y 9. Intente nuevamente.");
				puntosElegibles();
				System.out.print("Ingrese su opcion: ");
				opcion = scanMenu.nextInt();
			}
			
			switch(opcion) {
			case 1:
				this.opcion01(recital);
				break;
			case 2: 
				this.opcion02(recital);
				break;
			case 3: 
				this.opcion03(recital, artistasBase, artistasContratados);
				break;
			case 4:
				this.opcion04(recital, artistasBase, artistasContratados);
				break;
			case 5:
				this.opcion05(roles, artistasContratados);
				break;
			case 6:
				this.opcion06(recital, artistasBase);
				break;
			case 7:
				this.opcion07(recital, artistasBase);
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
		
		System.out.print("Desea hacer otra cosa? (Y/N) ");
		char opcionContinuar = scanMenu.next().charAt(0);
		
		while(Character.toUpperCase(opcionContinuar) != 'N' && Character.toUpperCase(opcionContinuar) != 'Y') {
			limpiarPantalla();
			System.out.println("Opcion invalida, debe ser Y o N. Intente nuevamente.");
			System.out.print("Desea hacer otra cosa? (Y/N) ");
			System.out.print("Ingrese su opcion: ");
			opcionContinuar = scanMenu.next().charAt(0);
		}
		
		if(Character.toUpperCase(opcionContinuar) == 'Y') {
			limpiarPantalla();
			System.out.flush();
			mostrarOpciones(recital, artistasBase, artistasContratados, roles);
		}else {
			return;
		}
	}
	
	private static void limpiarPantalla() {
	    System.out.print("\033[H\033[2J");
	    System.out.flush();
	}
	
	private static void puntosElegibles() {
		System.out.println("Elija una de las siguientes opciones:"); 
		System.out.println("01. Mostrar roles faltantes para una cancion determinada.");
		System.out.println("02. Mostrar roles faltantes para todas las canciones del recital.");
		System.out.println("03. Contratar artista para una cancion determinada.");
		System.out.println("04. Contratar artista para todas las canciones del recital.");
		System.out.println("05. Entrenar artista");
		System.out.println("06. Listar artistas contratados, su informacion relevante y su costo.");
		System.out.println("07. Listar canciones con su estado actual.");
		System.out.println("08. [PROLOG]");
		System.out.println("09. Salir");
	}
	
	private void opcion01(Recital recital) {
		ArrayList<Cancion> canciones = new ArrayList<>(recital.getCanciones());
		int cantCanciones = canciones.size();
		System.out.println("Elija la cancion: ");
		for(int i = 1; i <= canciones.size(); i++) {
			System.out.println(String.format("%02d - %s", i, canciones.get(i-1)));
		}
		
		Scanner scanMenu = null;
		
		try {
			scanMenu = new Scanner(System.in);
			int opcion = scanMenu.nextInt();
			
			while(opcion < 1 && opcion > cantCanciones) {
				limpiarPantalla();
				System.out.println("Opcion invalida, debe ser un numero entre 1 y "+ cantCanciones +". Intente nuevamente.");
				for(int i = 1; i < canciones.size(); i++) {
					System.out.println(String.format("%02d - %s", i, canciones.get(i-1)));
				}
				System.out.print("Ingrese su opcion: ");
				opcion = scanMenu.nextInt();
			}
			
			HashMap<Rol, Integer> rolesFaltantes = canciones.get(opcion-1).rolesFaltantes();
			
			if(rolesFaltantes.size() == 0) {
				System.out.println("Para " + canciones.get(opcion-1) + " estan todos los roles cubiertos. ");
				return;
			}
			
			System.out.println("Para " + canciones.get(opcion-1) + " faltan los siguientes roles: ");
			for(Map.Entry<Rol, Integer> entradas : rolesFaltantes.entrySet()) {
				System.out.println(String.format("%02d %s", entradas.getValue(), entradas.getKey()));
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void opcion02(Recital recital) {
		Set<Cancion> listaCanciones = recital.getCanciones();
		
		for(Cancion cancion : listaCanciones) {
			HashMap<Rol, Integer> rolesFaltantes = cancion.rolesFaltantes();
			System.out.println(cancion.toString().toUpperCase() + ": ");
			if(rolesFaltantes.size() == 0) {
				System.out.println("No hay roles por cubrir.");
			}else {
				for(Map.Entry<Rol, Integer> entradas : rolesFaltantes.entrySet()) {
					Rol rol = entradas.getKey();
					int cant = entradas.getValue();
					System.out.println(String.format("%02d %s", cant, rol));
				}
			}
		}
		
		return;
	}
	
	private void opcion03(Recital recital, Set<Artista> ab, Set<ArtistaContratado> ac) {
		ArrayList<Cancion> cancionesRecital = new ArrayList<>(recital.getCanciones());
		ArrayList<Cancion> cancionesRolesFaltantes = new ArrayList<>();
		for(Cancion c : cancionesRecital) {
			if(c.rolesFaltantes().size() > 0) {
				cancionesRolesFaltantes.add(c);
			}
		}
		
		int cantCanciones = cancionesRolesFaltantes.size();
		System.out.println("Elija la cancion: ");
		for(int i = 1; i <= cancionesRolesFaltantes.size(); i++) {
			System.out.println(String.format("%02d - %s", i, cancionesRolesFaltantes.get(i-1)));
		}
		
		Scanner scanMenu = null;
		
		try {
			scanMenu = new Scanner(System.in);
			int opcion = scanMenu.nextInt();
			
			while(opcion < 1 || opcion > cantCanciones) {
				limpiarPantalla();
				System.out.println("Opcion invalida, debe ser un numero entre 1 y "+ cantCanciones +". Intente nuevamente.");
				for(int i = 1; i <= cantCanciones; i++) {
					System.out.println(String.format("%02d - %s", i, cancionesRolesFaltantes.get(i-1)));
				}
				System.out.print("Ingrese su opcion: ");
				opcion = scanMenu.nextInt();
			}
			
			recital.contratarPorCancion(cancionesRolesFaltantes.get(opcion-1), ab, ac);
			
			HashMap<Rol, Set<Artista>> asignaciones = cancionesRolesFaltantes.get(opcion-1).getArtistasAsignados();
			
			System.out.println("Asignaciones hechas: ");
			for(Map.Entry<Rol, Set<Artista>> entradas : asignaciones.entrySet()) {
				Rol rol = entradas.getKey();
				Set<Artista> ar = entradas.getValue();
				System.out.print(rol.toString().toUpperCase() + ": ");
				for(Artista a : ar) {
					System.out.println("" + a + " ");
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void opcion04(Recital recital, Set<Artista> artistaBase, Set<ArtistaContratado> artistaContratado) {
		recital.contratarTodasCanciones(artistaBase, artistaContratado);
		HashMap<Cancion, HashMap<Rol, Set<Artista>>> asignacionesRecital = recital.getAsignacionesRecital();
		for(Map.Entry<Cancion, HashMap<Rol, Set<Artista>>> entradas : asignacionesRecital.entrySet()) {
			Cancion c = entradas.getKey();
			System.out.println(c + ": ");
			HashMap<Rol, Set<Artista>> mapRA = entradas.getValue();
			
			for(Map.Entry<Rol, Set<Artista>> ent : mapRA.entrySet()) {
				Rol r = ent.getKey();
				System.out.print("\t- " + r + ": [ ");
				Set<Artista> setArt = ent.getValue();
				for(Artista a : setArt) {
					System.out.print(a + " ");
				}
				System.out.println("]");
			}
			HashMap<Rol, Integer> rolesFaltantes = c.rolesFaltantes();
			if(rolesFaltantes.size() > 0) {
				System.out.println("\t- Faltan: ");
				for(Map.Entry<Rol, Integer> ent : rolesFaltantes.entrySet()) {
					System.out.println(String.format("\t\t- %02d %s", ent.getValue(), ent.getKey()));
				}
			}
			
		}
		
		return;
	}
	
	private void opcion05(Set<Rol> roles, Set<ArtistaContratado> contratados) {
		ArrayList<ArtistaContratado> cont = new ArrayList<>(contratados);
		ArrayList<Rol> rolesArray = new ArrayList<>(roles);
		int cantCont = contratados.size();
		System.out.println("Elige el artista: ");
		for(int i = 1; i <= cantCont; i++) {
			System.out.println(String.format("%02d- %s", i ,cont.get(i-1)));
		}
		
		Scanner scanMenu = null;
		
		try {
			scanMenu = new Scanner(System.in);
			int opcion = scanMenu.nextInt();
			
			while(opcion < 1 || opcion > cantCont) {
				limpiarPantalla();
				System.out.println("Opcion invalida, debe ser un numero entre 1 y "+ cantCont +". Intente nuevamente.");
				for(int i = 1; i <= cantCont; i++) {
					System.out.println(String.format("%02d- %s", i ,cont.get(i-1)));
				}
				System.out.print("Ingrese su opcion: ");
				opcion = scanMenu.nextInt();
			}
		
			ArtistaContratado a = cont.get(opcion-1);
			
			System.out.print("Eliga el rol a entrenar: ");
			int cantRolesEntrenar = rolesArray.size() - a.getHistoricoRoles().size();
			if(cantRolesEntrenar > 0) {				
				for(int i = 1; i <= roles.size(); i++) {
					System.out.println(String.format("%02d- %s", i ,rolesArray.get(i-1)));
				}
				
				opcion = scanMenu.nextInt();

				while(opcion < 1 || opcion > roles.size()) {
					limpiarPantalla();
					System.out.println("Opcion invalida, debe ser un numero entre 1 y "+ roles.size() +". Intente nuevamente.");
					for(int i = 1; i <= roles.size(); i++) {
						System.out.println(String.format("%02d- %s", i ,rolesArray.get(i-1)));
					}
					System.out.print("Ingrese su opcion: ");
					opcion = scanMenu.nextInt();
				}
				
				a.entrenarRol(rolesArray.get(opcion-1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
	private void opcion06(Recital recital, Set<Artista> ab) {
		Set<Cancion> listaCancion = recital.getCanciones();
		HashMap<Artista, Set<Rol>> artistasRecital = new HashMap<>();
		for(Cancion c : listaCancion) {
			HashMap<Rol, Set<Artista>> artistasAsignados = c.getArtistasAsignados();
			for(Map.Entry<Rol, Set<Artista>> entradas : artistasAsignados.entrySet()) {
				Rol rol = entradas.getKey();
				Set<Artista> arts = entradas.getValue();
				for(Artista a : arts) {
					Set<Rol> roles = artistasRecital.getOrDefault(a, new HashSet<>());
					roles.add(rol);
					artistasRecital.put(a, roles);						
				}
			}
		}
		
		for(Map.Entry<Artista, Set<Rol>> entradas : artistasRecital.entrySet()) {
			ArrayList<Artista> artBase = new ArrayList<>(ab);
			Artista a = entradas.getKey();
			System.out.println(entradas.getKey());
			Set<Rol> roles = entradas.getValue();
			Iterator<Rol> rit = roles.iterator();
			
			System.out.println("\t Roles: [ ");
			while(rit.hasNext()) {
		        Rol r = rit.next();
		        System.out.println("\t\t " + r + " - Costo: $" + a.calcularCosto(artBase, r));
		    }
		    System.out.println("\t        ]");
		}
	}
	
	private void opcion07(Recital recital, Set<Artista> ab) {	
		Set<Cancion> canciones = recital.getCanciones();
		for(Cancion c : canciones) {
			System.out.println(c + ": ");
			HashMap<Rol, Integer> rolesFaltantes = c.rolesFaltantes();
			
			String estado = rolesFaltantes.size() == 0 ? "Completa" : "Faltan roles por cubrir.";
			System.out.println("Estado: " + estado);
			if(rolesFaltantes.size() > 0) {
				System.out.println("\tRoles faltantes: ");
				for(Map.Entry<Rol, Integer> entradas : rolesFaltantes.entrySet()) {
					System.out.println("\t\t- " + entradas.getValue() + " " + entradas.getKey());					
				}
			}
			
			System.out.println("Artisas asignados: ");
			HashMap<Rol, Set<Artista>> asig = c.getArtistasAsignados();
			for(Map.Entry<Rol, Set<Artista>> entradas : asig.entrySet()) {
				Rol r = entradas.getKey();
				Set<Artista> art = entradas.getValue();
				Iterator<Artista> artIt = art.iterator();
				
				System.out.print("\t" + r + ": [ ");
				while(artIt.hasNext()) {
					System.out.print(artIt.next() + " ");
				}
				System.out.println("]");
			}
		}
	}
	
	private void opcion08() {		
		return;
	}
}

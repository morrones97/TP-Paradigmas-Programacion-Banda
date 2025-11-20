package app;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import artistas.Artista;
import artistas.ArtistaContratado;
import artistas.Rol;
import canciones.Cancion;
import recitales.Recital;

public class App {

	public static void main(String[] args) throws Exception {
		Rol cantante = new Rol("Cantante");
		Rol guitarrista = new Rol("Guitarrista");
		Rol baterista = new Rol("Baterista");
		Rol tecladista = new Rol("Tecladista");
		Rol productor = new Rol("Productor");
		
		Set<Rol> setRoles = new HashSet<>(Arrays.asList(cantante, guitarrista, baterista, tecladista, productor));
		
		String sonantes = "Los Sonantes";
		String ritmo = "Ritmo Urbano";
		String voces = "Voces del Alba";
		String electro = "Electro Rock";
		
		Set<Rol> rolesJPB = new HashSet<>(Arrays.asList(cantante, guitarrista));
		Set<Rol> rolesMGB = new HashSet<>(Arrays.asList(baterista));
		Set<Rol> rolesCRC = new HashSet<>(Arrays.asList(productor));
		Set<Rol> rolesCRCEnt = new HashSet<>(Arrays.asList(tecladista));
		Set<Rol> rolesLTC = new HashSet<>(Arrays.asList(cantante));
		
		Set<String> bandasJPB = new HashSet<>(Arrays.asList(sonantes));
		Set<String> bandasMGB = new HashSet<>(Arrays.asList(ritmo));
		Set<String> bandasLTC = new HashSet<>(Arrays.asList(voces));
		
		Artista JuanPerezB = new Artista("Juan Perez", rolesJPB, bandasJPB);
		Artista MariaGomezB = new Artista("Maria Gomez", rolesMGB, bandasMGB);
		ArtistaContratado CarlosRomeroC = new ArtistaContratado("Carlos Romero", rolesCRC, null, 25000, 4, rolesCRCEnt);
		ArtistaContratado LucianaTorresC = new ArtistaContratado("Luciana Torres", rolesLTC, bandasLTC, 20000, 6, null);
		LucianaTorresC.entrenarRol(productor);
		
		Set<Artista> artistasBase = new HashSet<>(Arrays.asList(JuanPerezB, MariaGomezB)); 
		Set<ArtistaContratado> artistasContratados = new HashSet<>(Arrays.asList(CarlosRomeroC, LucianaTorresC)); 
		
		HashMap<Rol, Integer> rolesCancion1 = new HashMap<>(Map.of(
			    cantante, 1,
			    guitarrista, 1
			));
		
		HashMap<Rol, Integer> rolesCancion2 = new HashMap<>(Map.of(
			    cantante, 1,
			    tecladista, 1
			));
		
		HashMap<Rol, Integer> rolesCancion3 = new HashMap<>(Map.of(
			    guitarrista, 2,
			    baterista, 1
			));
		
		Cancion cancion1 = new Cancion("Amanecer Electrico", rolesCancion1);
		Cancion cancion2 = new Cancion("Rio de fuego", rolesCancion2);
		Cancion cancion3 = new Cancion("Luz de tormenta", rolesCancion3);
		
		Set<Cancion> listaCanciones = new HashSet<>(Arrays.asList(cancion1, cancion2));
		
		Recital recital = new Recital("Noche del Rock 2025", sonantes, listaCanciones);
		
		
		
		Menu menuOpc = new Menu();
		
		menuOpc.mostrarOpciones(recital, artistasBase, artistasContratados, setRoles);
		
	}
	

}

package app;

import archivos.LectorJSON;

import java.util.HashSet;
import java.util.Set;

import artistas.Artista;
import artistas.ArtistaContratado;
import artistas.Rol;
import recitales.Recital;

public class App {

	public static void main(String[] args) throws Exception {
		Set<Artista> artistasBase = new HashSet<>();
    	Set<ArtistaContratado> artistasContratados = new HashSet<>();
        Set<Rol> roles = new HashSet<>();
        Recital recital = LectorJSON.leerArchivoRecital("recital.json");
        LectorJSON.leerArchivoArtistas("artistas.json", artistasBase, artistasContratados, roles);
   	
		Menu menuOpc = new Menu();	
		menuOpc.mostrarOpciones(recital, artistasBase, artistasContratados, roles);
		
	}
	

}

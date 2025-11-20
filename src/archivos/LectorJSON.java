package archivos;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

import artistas.Artista;
import artistas.ArtistaContratado;
import artistas.Rol;
import canciones.Cancion;
import recitales.Recital;

public class LectorJSON {
    public static void leerArchivoArtistas(String nombreArchivo, Set<Artista> artBase,
		Set<ArtistaContratado> artCont, Set<Rol> roles) throws FileNotFoundException {
    	Gson gson = new Gson();
    	FileReader reader = new FileReader("data/"+nombreArchivo);
    	ArtistaData data = gson.fromJson(reader, ArtistaData.class);
       
    	for (ArtistaJson a : data.getArtistas()) {
    		Set<Rol> rolesBase = new HashSet<>();
    		Set<String> bandasArtista = new HashSet<>();
    		ArrayList<String> historicoRolesArch = a.getHistoricoRoles();  
    	    for(String r : historicoRolesArch) {
    	    	roles.add(new Rol(r));
    	    	rolesBase.add(new Rol(r));
    	    }
    	    String name = a.getName();
    	    ArrayList<String> historicoBandasArch = a.getHistoricoBandas();  
    	    bandasArtista.addAll(historicoBandasArch);
    	    ArtistaContratadoJSON ac = a.getArtistaContratado();
    	    
    	    if(ac != null) {
    	    	Set<Rol> rolesEntrenados = new HashSet<>();
    	    	ArrayList<String> rolesEntrenadosArch = ac.getRolesEntrenados();
    	    	for(String r : rolesEntrenadosArch) {
        	    	roles.add(new Rol(r));
        	    	rolesEntrenados.add(new Rol(r));
        	    }
    	    	double costoCancion = ac.getCostoPorCancion();
    	    	int cantCancionesMax = ac.getCantCancionesMaxRecital();
    	    	
    	    	artCont.add(new ArtistaContratado(name, rolesBase, bandasArtista, costoCancion, 
    	    			cantCancionesMax, rolesEntrenados));    	    	
    	    }else {
    	    	artBase.add(new Artista(name, rolesBase, bandasArtista)); 
    	    }
    	}
    }
    
    public static Recital leerArchivoRecital(String nombreArchivo) 
    		throws FileNotFoundException {
    	Gson gson = new Gson();
    	FileReader reader = new FileReader("data/"+nombreArchivo);
    	RecitalJson data = gson.fromJson(reader, RecitalJson.class);
    	Set<Cancion> canciones = new HashSet<>();
    	String tituloRecital = data.getTitulo();
    	String bandaRecital = data.getBanda();
    	for(CancionJson c : data.getCanciones()) {
    		String tituloCancion = c.getTitulo();
    		HashMap<Rol, Integer> roles = new HashMap<>();
    		for(Map.Entry<String, Integer> ent : c.getRolesRequeridos().entrySet()) {
    			Rol rol = new Rol(ent.getKey());
    			roles.put(rol, roles.getOrDefault(rol, 0) + ent.getValue());
    		}
    		canciones.add(new Cancion(tituloCancion, roles));
    		
    	}
    	
    	return new Recital(tituloRecital, bandaRecital, canciones);
    }
}

//public Recital(String titulo, String banda, Set<Cancion> listaCanciones) {
//this(titulo, banda);
//this.listaCanciones = new HashSet<>(listaCanciones);	
//}
 
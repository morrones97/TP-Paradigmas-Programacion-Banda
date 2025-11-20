package archivos;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;

import artistas.Artista;
import artistas.ArtistaContratado;
import artistas.Rol;
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
    
    public static void leerArchivoRecital(String nombreArchivo, Recital recital) {
    	
    }
}
 
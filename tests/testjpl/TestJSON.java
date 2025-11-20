package testjpl;

import com.google.gson.Gson;

import archivos.ArtistaData;
import archivos.ArtistaJson;

import java.io.FileReader;

public class TestJSON {
    public static void main(String[] args) throws Exception {
    	Gson gson = new Gson();
    	FileReader reader = new FileReader("data/artistas.json");
        ArtistaData data = gson.fromJson(reader, ArtistaData.class);
        
        for(ArtistaJson a : data.getArtistas()) {
        	 System.out.println("Nombre: " + a.getName());
        	 System.out.print("Roles: [ ");
        	 for(String r : a.getHistoricoRoles()) {
        		 System.out.print(r + " ");
        	 }  
        	 System.out.println("]");
        }
    }
}

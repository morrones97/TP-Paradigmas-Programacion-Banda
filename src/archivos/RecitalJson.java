package archivos;

import java.util.ArrayList;

public class RecitalJson {
	private String titulo;	
	private String banda;
	private ArrayList<CancionJson> canciones;
	
	public String getTitulo() {
		return this.titulo;
	}
	public String getBanda() {
		return this.banda;
	}
	public ArrayList<CancionJson> getCanciones() {
		return this.canciones;
	}	
}

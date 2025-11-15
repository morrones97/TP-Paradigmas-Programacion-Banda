package artistas;

import java.util.ArrayList;
import java.util.Set;

public class Artista {
	//ATRIBUTOS
	private String name;
	private Set<Rol> historicoRoles;
	private Set<String> historicoBandas;

	//CONSTRUCTOR
	public Artista(String n, Set<Rol> historicoRoles, Set<String> historicoBandas, int cantMaxCanciones) {
		this.name = n;
		this.historicoRoles = historicoRoles;
		this.historicoBandas = historicoBandas;
	}
	
	// GETTERS
	public String getName() {return name;}
	public Set<Rol> getHistoricoRoles() {return historicoRoles;}
	public Set<String> getHistoricoBandas() {return historicoBandas;}

	// METODOS PROPPIOS DE LA CLASE
	public void agregarBandaAHistorico(String nombreBanda) {
		if(nombreBanda.length() == 0) { 
			System.out.println("El nombre de la banda no puede ser vacio");
			return;
		}
		
		this.historicoBandas.add(nombreBanda);
	}
	
	public void agregarRolAHistorico(Rol rol) {
		if(rol == null) { 
			System.out.println("El rol no puede ser vacio");
			return;
		}
		
		this.historicoRoles.add(rol);
	}
	
	public boolean participoEnBanda(String banda) { return this.historicoBandas.contains(banda); }	
	
	
	public boolean poseeRol(Rol rol) { return this.historicoRoles.contains(rol); }
	public double calcularCosto(ArrayList<Artista> artistasBase, Rol rol) {return 0;}
}

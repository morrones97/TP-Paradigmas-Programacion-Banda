package artistas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Artista {
	//ATRIBUTOS
	private static int nextId = 1; 
	private final int id;
	private String name;
	private Set<Rol> historicoRoles;
	private Set<String> historicoBandas;

	//CONSTRUCTOR
	public Artista(String n, Set<Rol> historicoRoles, Set<String> historicoBandas) {
		this.id = nextId++;
		this.name = n;
		this.historicoRoles = historicoRoles == null ? new HashSet<>() : new HashSet<>(historicoRoles);
		this.historicoBandas = historicoBandas== null ? new HashSet<>() : new HashSet<>(historicoBandas);
	}
	
	public Artista(String n) {
		this.id = nextId++;
		this.name = n;
		this.historicoRoles = new HashSet<>();
		this.historicoBandas = new HashSet<>();
	}
	
	// GETTERS
	public String getName() {return name;}
	public Set<Rol> getHistoricoRoles() {return historicoRoles;}
	public Set<String> getHistoricoBandas() {return historicoBandas;}

	// METODOS PROPPIOS DE LA CLASE
	public void agregarBandaAHistorico(String nombreBanda) {
		if(nombreBanda.length() == 0) { 
			throw new IllegalArgumentException("El nombre de la banda no puede ser vacio");
		}
		
		this.historicoBandas.add(nombreBanda);
	}
	
	public void agregarRolAHistorico(Rol rol) {
		if(rol == null) { 
			throw new IllegalArgumentException("El rol no puede ser vacio");
		}
		
		this.historicoRoles.add(rol);
	}
	
	public boolean participoEnBanda(String banda) { return this.historicoBandas.contains(banda); }	
	
	
	public boolean poseeRol(Rol rol) { return this.historicoRoles.contains(rol); }
	public double calcularCosto(ArrayList<Artista> artistasBase, Rol rol) {return 0;}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;

	    Artista artista = (Artista) o;
	    return this.id == artista.id;
	    		
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(id);
	}
	
	@Override 
	public String toString() {
		String cad = "";
		cad += this.name;
		return cad;
	}
}

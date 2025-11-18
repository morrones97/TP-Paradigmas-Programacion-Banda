package artistas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ArtistaContratado extends Artista implements Comparable<ArtistaContratado>{
	private Set<Rol> rolesEntrenados;
	private int cantCancionesMaxRecital;
	private double costoPorCancion;
	
	public ArtistaContratado(String n, Set<Rol> historicoRoles, Set<String> historicoBandas, double costoCancion,
			int cantMaxCanciones, Set<Rol> rolesEntrenados) {
		super(n, historicoRoles, historicoBandas);
		this.rolesEntrenados = rolesEntrenados == null ? new HashSet<>() : new HashSet<>(rolesEntrenados);
		this.cantCancionesMaxRecital = cantMaxCanciones;
		this.costoPorCancion = costoCancion;
	}
	
	public int getCantCancionesMaxRecital() {return cantCancionesMaxRecital;}
	public double getCostoPorCancion() {return costoPorCancion;}
	
	// METODOS PROPPIOS DE LA CLASE
	public boolean esRolEntrenado(Rol rol) {
		return this.rolesEntrenados.contains(rol);
	}
	
	public void entrenarRol(Rol rol) {
		if(this.getHistoricoRoles().contains(rol) || this.rolesEntrenados.contains(rol)) {
			throw new IllegalArgumentException("Ya posee el rol: " + rol.getNombre());
		}
		
		this.rolesEntrenados.add(rol); 
	}
	
	public boolean comparteBanda(Artista artistaBase) {
		Set<String> bBase = artistaBase.getHistoricoBandas(); 
		for(String banda : bBase) {
			if(this.getHistoricoBandas().contains(banda)) {
				return true;
			}
		}		
		return false;
	}
	
	@Override
	public double calcularCosto(ArrayList<Artista> artistasBase, Rol rol) {		
		boolean comparteBandaConAlguno = 
				artistasBase.stream()
					.anyMatch(
							b -> b.getHistoricoBandas().stream()
							.anyMatch(
									banda -> this.participoEnBanda(banda)
									)
							);
		
		double costo = comparteBandaConAlguno ? this.costoPorCancion * 0.5 : this.costoPorCancion;
		
		if(this.esRolEntrenado(rol)) {
			costo *= 1.5;
		}
		
		return costo;
	}
	
	@Override 
	public boolean poseeRol(Rol rol) { 
		return (super.poseeRol(rol) || this.rolesEntrenados.contains(rol)); }
	
	
	@Override
	public int compareTo(ArtistaContratado otro) {
		if(this.costoPorCancion < otro.getCostoPorCancion()) return -1;
		if(this.costoPorCancion > otro.getCostoPorCancion()) return 1;
		return 0;
	}
}

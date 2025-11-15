package artistas;

import java.util.ArrayList;
import java.util.Set;

public class ArtistaContratado extends Artista{
	private Set<Rol> rolesEntrenados;
	private int cantCancionesMaxRecital;
	private double costoPorCancion;
	
	public ArtistaContratado(String n, Set<Rol> historicoRoles, Set<String> historicoBandas, double costoCancion,
			int cantMaxCanciones, Set<Rol> rolesEntrenados) {
		super(n, historicoRoles, historicoBandas, cantMaxCanciones);
		this.rolesEntrenados = rolesEntrenados;
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
		if(this.getHistoricoRoles().contains(rol)) {
			System.out.println("Ya posee el rol");
			return;
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
		/*boolean comparteBandaConAlguno = false;
		for(Artista aBase : artistasBase) {
			Set<String> bBase = aBase.getHistoricoBandas();
			for(String banda : bBase) {
				if(this.participoEnBanda(banda)) {
					comparteBandaConAlguno = true;
					break outer;					
				}
			}
		}*/
		
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
}

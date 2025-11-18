package canciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import artistas.Artista;
import artistas.Rol;

public class Cancion {
	private String titulo;
	private HashMap<Rol, Integer> rolesRequeridos = new HashMap<>();
	private HashMap<Rol, Set<Artista>> artistasAsignados = new HashMap<>();
	
	
	public Cancion(String titulo, HashMap<Rol, Integer> roles) {
	    if (titulo == null || titulo.isBlank())
	        throw new IllegalArgumentException("El título no puede estar vacío");

	    if (roles == null || roles.isEmpty())
	        throw new IllegalArgumentException("Debe haber al menos un rol requerido");
	    
		this.titulo = titulo;	
	
		for(Rol r : roles.keySet()) {
			this.rolesRequeridos.put(r, roles.get(r));		
		}
	}
	
	public Cancion(String titulo, HashMap<Rol, Integer> roles, HashMap<Rol, Set<Artista>> artistas) {
	    this(titulo, roles);
		
		if(artistas != null) {
			for(Map.Entry<Rol, Set<Artista>> entradas : artistas.entrySet()) {
				Rol rolArtista = entradas.getKey();
				Set<Artista> arts = entradas.getValue();
				
				validarAsignacionRolArtistas(rolArtista, arts);				
				
				this.artistasAsignados.put(rolArtista, new HashSet<>(arts));
			}
		}		
	}
	
	public HashMap<Rol, Integer> getRolesRequeridos() { return this.rolesRequeridos; }
	public HashMap<Rol, Set<Artista>> getArtistasAsignados() { return this.artistasAsignados; }
	public String getTitulo() { return this.titulo; }
	
	private void validarAsignacionRolArtistas(Rol rolArtista, Set<Artista> arts) {
		Integer cantReq = this.rolesRequeridos.get(rolArtista);
		if(cantReq == null) {
			throw new IllegalArgumentException("Rol no requerido " + rolArtista.getNombre());
		}
		
		int vacantesDisponibles = this.rolesRequeridos.get(rolArtista) - this.artistasAsignados.get(rolArtista).size();
		
		if(vacantesDisponibles < arts.size()) {
			throw new IllegalArgumentException(
					"Se requieren " + vacantesDisponibles + " " + rolArtista.getNombre() + 
					" pero se pasaron " + arts.size() + "."
				);
		}
		
		for(Artista a : arts) {
			if(!a.poseeRol(rolArtista)) {
				throw new IllegalArgumentException(
						"El artista " + a.getName() + 
						" no está habilitado para el rol " + rolArtista.getNombre()
						);
			}
		}
		
		return;
	}
	
	public HashMap<Rol, Integer> rolesFaltantes() {
		if(rolesRequeridos.size() == 0) {
			System.out.println("No hay roles requeridos para esta cancion");
			return null;
		}
		
		HashMap<Rol, Integer> rolesFaltantes = new HashMap<>();
		
		for(Map.Entry<Rol, Integer> rolesReq : this.rolesRequeridos.entrySet()) {
			Set<Artista> aAsignados = this.artistasAsignados.get(rolesReq.getKey());
			if(aAsignados == null || aAsignados.size() != rolesReq.getValue()) {
				rolesFaltantes.put(
						rolesReq.getKey(), 
						rolesReq.getValue() - this.artistasAsignados.get(rolesReq.getKey()).size()
					);
			}	
		}
		
		return rolesFaltantes;
	}
	
	public void asignarArtista(Rol rol, Set<Artista> artistas) {
		
		if(rol == null) {
			throw new IllegalArgumentException("El rol no puede ser null.");
		}
		
		if(artistas == null || artistas.isEmpty()) {
			throw new IllegalArgumentException("Los artistas no pueden ser null ni estar vacios.");
		}
		
					
		validarAsignacionRolArtistas(rol, artistas);	
		
		this.artistasAsignados.merge(rol, artistas, (existente, nuevos) -> {
			existente.addAll(new ArrayList<>(nuevos));
			return existente;
		});

	
		return;
	}
	
	public boolean puedeTocarse() {
		for(Map.Entry<Rol, Integer> rolesReq : this.rolesRequeridos.entrySet()) {
			Set<Artista> artistasAsig = this.artistasAsignados.getOrDefault(rolesReq.getKey(), null);
			
			if(artistasAsig == null) return false;
				
			if(rolesReq.getValue() > artistasAsig.size()) return false;
			}
		
		return true;
	}
	
	public double obtenerCosto(ArrayList<Artista> artistasBase) {
		if(!this.puedeTocarse()) {
			throw new IllegalStateException("No es posible calcular el costo: la canción no tiene todas las asignaciones completas.");
		}
		
		double precio = 0;
		
		for(Map.Entry<Rol, Set<Artista>> aAsignados : this.artistasAsignados.entrySet()) {
			Set<Artista> artistasAsignados = aAsignados.getValue();
			Rol rolAsignado = aAsignados.getKey();
			for(Artista a : artistasAsignados) {
				precio += a.calcularCosto(artistasBase, rolAsignado);
			}
		}
		
		
		return precio;
	}
}

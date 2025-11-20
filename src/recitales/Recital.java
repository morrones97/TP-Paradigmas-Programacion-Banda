package recitales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import artistas.Artista;
import artistas.ArtistaContratado;
import artistas.Rol;
import canciones.Cancion;

public class Recital {
	private String titulo;
	private String banda;
	private Set<Cancion> listaCanciones = new HashSet<>();
	private Map<ArtistaContratado, Integer> contadorCanciones = new HashMap<>();
	private Set<Artista> artistasBaseContratados = new HashSet<>();
	private HashMap<Cancion, HashMap<Rol, Set<Artista>>> asignacionesRecital = new HashMap<>();
	
	public Recital(String titulo, String banda) {
		this.titulo = titulo;
		this.banda = banda;
	}
	
	public Recital(String titulo, String banda, Set<Cancion> listaCanciones) {
		this(titulo, banda);
		this.listaCanciones = new HashSet<>(listaCanciones);	
	}
	
	public String getTitulo() { return titulo; }

	public String getBanda() { return banda; }
	
	public Set<Cancion> getCanciones() { return this.listaCanciones; }
	
	public HashMap<Cancion, HashMap<Rol, Set<Artista>>> getAsignacionesRecital() {
		return this.asignacionesRecital;
	}
	
	public Set<Artista> listarArtistas() {
		
		Set<Artista> artistasRecital = new HashSet<>();
		
		if(this.listaCanciones.size() == 0) {
			return artistasRecital;
		}
		
		for(Cancion c : this.listaCanciones) {
			HashMap<Rol, Set<Artista>> asignaciones = c.getArtistasAsignados();
			for(Map.Entry<Rol, Set<Artista>> entradas : asignaciones.entrySet()) {
				Set<Artista> artistasAsignados = entradas.getValue();
				for(Artista a : artistasAsignados) {					
					artistasRecital.add(a);
				}
			}
		}
		
		return artistasRecital;
	}
	
	public void contratarTodasCanciones(
			Set<Artista> artistasBase,
			Set<ArtistaContratado> artistasContratados
		) {
		for(Cancion cancion : this.listaCanciones) {
			boolean cancionConAsignaciones = 
					this.asignacionesRecital.containsKey(cancion) &&
					this.asignacionesRecital.get(cancion) != null &&
					cancion.rolesFaltantes() != null &&
					cancion.rolesFaltantes().size() == 0;
			
			if(!cancionConAsignaciones) {
				contratarPorCancion(cancion, artistasBase, artistasContratados);
			}
		}	
	}
	
	public void contratarPorCancion(
			Cancion cancion,
			Set<Artista> artistasBase,
			Set<ArtistaContratado> artistasContratados) {
		if(!this.listaCanciones.contains(cancion)) {
			return;
		}
		
		HashMap<Rol, Set<Artista>> asignacionNueva = new HashMap<>();
		Set<Rol> rolesRequeridos = cancion.rolesFaltantes().keySet();
		for(Rol rol : rolesRequeridos) {			
			Set<Artista> asignadosPorRol = asingarArtistasPorRol(rol, artistasBase, artistasContratados, asignacionNueva, cancion);
			if(asignadosPorRol != null && !asignadosPorRol.isEmpty()) {
				asignacionNueva.put(rol, asignadosPorRol);
				for(Artista a : asignadosPorRol) {
					if(artistasContratados != null && artistasContratados.contains(a)) {
						ArtistaContratado ac = (ArtistaContratado) a;
						this.contadorCanciones.merge(ac, 1, Integer::sum);
					}
				}
				
				cancion.asignarArtista(rol, asignadosPorRol);
			}
		}
		
	    HashMap<Rol, Set<Artista>> asignacionesCancion =
	            asignacionesRecital.getOrDefault(cancion, new HashMap<>());
	    
	    asignacionesCancion.putAll(asignacionNueva);

	    asignacionesRecital.put(cancion, asignacionesCancion);
	    
	}
	
	private Set<Artista> asingarArtistasPorRol(
			Rol rol, 
			Set<Artista> base, 
			Set<ArtistaContratado> contratados, 
			HashMap<Rol, Set<Artista>> asignacionNueva,
			Cancion cancion) {
		int cantidadRequeridaParaRol = cancion.getRolesRequeridos().get(rol);
		Set<Artista> elegidos = new HashSet<>();
		for(int i = 0; i < cantidadRequeridaParaRol; i++) {
			Artista elegido = elegirArtista(rol, base, contratados, cancion.getArtistasAsignados(), elegidos);
			if(elegido != null) {
				elegidos.add(elegido);
			}
		}		
		return elegidos;
	}
	
	private Artista elegirArtista(
			Rol rol, 
			Set<Artista> base, 
			Set<ArtistaContratado> contratados, 
			HashMap<Rol, Set<Artista>> asignacionesHechas,
			Set<Artista> elegidos) {
		
		Set<Artista> yaElegidos = new HashSet<>();
		for(Map.Entry<Rol, Set<Artista>> ent : asignacionesHechas.entrySet()) {
			for(Artista a : ent.getValue()) {
				yaElegidos.add(a);
			}
		}				
		yaElegidos.addAll(elegidos);
				
		for(Artista ab : base) {
			if(!yaElegidos.contains(ab) && ab.poseeRol(rol)) {
				return ab;
			}
		}
			
		if(contratados == null) return null;
		
		ArtistaContratado mejor = null;
		double mejorCosto = Double.MAX_VALUE;
		for(ArtistaContratado ac : contratados) {
			if(yaElegidos.contains(ac) || !ac.poseeRol(rol)) continue;
			
			int cantActual = contadorCanciones.getOrDefault(ac, 0);
			if(cantActual >= ac.getCantCancionesMaxRecital()) continue;
			
			double costo = ac.calcularCosto(new ArrayList<>(this.artistasBaseContratados), rol);
			
			if(costo < mejorCosto) {
				mejor = ac;
				mejorCosto = costo;
			}
		}
		
		return mejor;		
	}
	
	public void agregarCancion(Cancion cancion) {
		this.listaCanciones.add(cancion);
	}
	
	public boolean sacarCancion(Cancion cancion) {
		return this.listaCanciones.remove(cancion);
	}
	
	public HashMap<Rol, Integer> obtenerRolesFaltantes(){
		if(this.listaCanciones.isEmpty()) {
			return null;
		}
	
		HashMap<Rol, Integer> rolesFaltantesRecital = new HashMap<>();
		
		for(Cancion c : this.listaCanciones) {
			HashMap<Rol, Integer> rolesFaltantesCancion = c.rolesFaltantes();
			
			for(Map.Entry<Rol, Integer> entradas : rolesFaltantesCancion.entrySet()) {
				Rol rol = entradas.getKey();
				int cant = entradas.getValue();
				
				rolesFaltantesRecital.put(entradas.getKey(), rolesFaltantesRecital.getOrDefault(rol, 0) + cant);
			}
		}
		
		
		return rolesFaltantesRecital;
	}
	
	public double cotizarRecital(ArrayList<Artista> artistasBase) {
		HashMap<Rol, Integer> rolesFaltantes = this.obtenerRolesFaltantes();
		if(rolesFaltantes == null) {
			throw new IllegalArgumentException("Los roles falntantes no pueden ser null.");
		}
		if(!rolesFaltantes.isEmpty()) {
			throw new IllegalArgumentException("Los roles falntantes no pueden ser null."); 
		}
		
		double costo = 0;
		
		for(Cancion c : this.listaCanciones) {
			costo += c.obtenerCosto(artistasBase);
		}
		
		return costo;
	}
	
	

	
}

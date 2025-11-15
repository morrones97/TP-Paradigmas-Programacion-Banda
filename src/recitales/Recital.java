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
	
	
	public void contratarArtistas(ArrayList<Artista> artistasBase, ArrayList<ArtistaContratado> artistasContratados) {
		// Obtener artistas con roles coincidentes
		HashMap<Cancion, HashMap<Rol, Set<Artista>>> candidatosBase = new HashMap<>();
		HashMap<Cancion, HashMap<Rol, Set<ArtistaContratado>>> candidatosContratados = new HashMap<>();
			
		if(
				(artistasBase == null || artistasBase.isEmpty()) 
				&&
				(artistasContratados == null || artistasContratados.isEmpty())
			) return;
		
		// Candidatos por rol;
		generarCandidatosPorRol(artistasBase, candidatosBase);
		generarCandidatosPorRol(artistasContratados, candidatosContratados);
		
		Set<Artista> artistasBaseCandidatos = new HashSet<>();
		for(Map.Entry<Cancion, HashMap<Rol, Set<Artista>>> entradasCandidatosBase : candidatosBase.entrySet()) {
			HashMap<Rol, Set<Artista>> mapaArtistaRol = entradasCandidatosBase.getValue();
			for(Map.Entry<Rol, Set<Artista>> entradasArtistaRol : mapaArtistaRol.entrySet()) {
				Set<Artista> artistasRol = entradasArtistaRol.getValue(); 
				for(Artista r : artistasRol) {
					artistasBaseCandidatos.add(r);					
				}
			}
		}
		
		HashMap<Cancion, HashMap<Rol, Set<ArtistaContratado>>> candsContratadosRolEntrenado = new HashMap<>();
		HashMap<Cancion, HashMap<Rol, Set<ArtistaContratado>>> candsContratadosRolNoEntrenado = new HashMap<>();
		
		for(Map.Entry<Cancion, HashMap<Rol, Set<ArtistaContratado>>> entradasCandidatosContratados : candidatosContratados.entrySet()) {
			Cancion cancion = entradasCandidatosContratados.getKey();
			HashMap<Rol, Set<ArtistaContratado>> mapaArtistaRolC = entradasCandidatosContratados.getValue();
			for(Map.Entry<Rol, Set<ArtistaContratado>> entradasArtistaRolC : mapaArtistaRolC.entrySet()) {
				Set<ArtistaContratado> artistasRolC = entradasArtistaRolC.getValue(); 
				Rol rolContratado = entradasArtistaRolC.getKey();
				for(ArtistaContratado r : artistasRolC) {
					if(r.esRolEntrenado(rolContratado)) {
						HashMap<Rol, Set<ArtistaContratado>> cPorRol = 
								candsContratadosRolEntrenado.computeIfAbsent(cancion, k -> new HashMap<>());
						Set<ArtistaContratado> setArtistaC = cPorRol.computeIfAbsent(rolContratado, k -> new HashSet<>());
						
						setArtistaC.add(r);				
					}else {
						HashMap<Rol, Set<ArtistaContratado>> cPorRol = 
								candsContratadosRolNoEntrenado.computeIfAbsent(cancion, k -> new HashMap<>());
						Set<ArtistaContratado> setArtistaC = cPorRol.computeIfAbsent(rolContratado, k -> new HashSet<>());
						
						setArtistaC.add(r);
					}
				}
			}
		}
		
		HashMap<Cancion, HashMap<Rol, Set<ArtistaContratado>>> contNEC = new HashMap<>();
		HashMap<Cancion, HashMap<Rol, Set<ArtistaContratado>>> contNENC = new HashMap<>();
		
		HashMap<Cancion, HashMap<Rol, Set<ArtistaContratado>>> contEC = new HashMap<>();
		HashMap<Cancion, HashMap<Rol, Set<ArtistaContratado>>> contENC = new HashMap<>();
		
		seperarCompartenBandas(artistasBaseCandidatos, contEC, contENC, candsContratadosRolEntrenado);
		seperarCompartenBandas(artistasBaseCandidatos, contNEC, contNENC, candsContratadosRolNoEntrenado);
		
		
		
		/* candidatosBase --> Artistas base que son candidatos (0)
		 * contNEC 	---> Artistas contratados con roles entrenados que comparten banda (0,50)
		 * conEC ---> Artistas contratados con roles entrenados y que comparten banda (0,75) 
		 * contNENC ---> Artistas contratados con roles no entrenados que no comparten banda (1)
		 * constENC ---> Artistas contratados con roles entrenados que no comparten banda (1,5)
		 * */
	}
	
	// <? extends Artista> signfica un tipo cualquiera que extienda de artista
	private <T extends Artista>  void generarCandidatosPorRol(
			ArrayList<T> artistas, 
			HashMap<Cancion, HashMap<Rol, Set<T>>> candidatos) {
		if(artistas.size() == 0) return;
		
		for(Cancion c : this.listaCanciones) {
			Set<Rol> rolesFaltantes = c.rolesFaltantes().keySet();
			for(T a : artistas) {
				for(Rol r : rolesFaltantes) {
					if(a.poseeRol(r)) {
						HashMap<Rol, Set<T>> porRol =
		                        candidatos.computeIfAbsent(c, k -> new HashMap<>());
						Set<T> setArtistas = porRol.computeIfAbsent(r, k -> new HashSet<>());
						setArtistas.add(a);
					}
				}
			}
		}
	}
	
	
	private void seperarCompartenBandas(
			Set<Artista> artistasBaseCandidatos, 
			HashMap<Cancion, HashMap<Rol, Set<ArtistaContratado>>> comparte, 
			HashMap<Cancion, HashMap<Rol, Set<ArtistaContratado>>> noComparte,
			HashMap<Cancion, HashMap<Rol, Set<ArtistaContratado>>> fuente
			) {
		for(Map.Entry<Cancion, HashMap<Rol, Set<ArtistaContratado>>> entradasRE : fuente.entrySet()) {
			Cancion cancion = entradasRE.getKey();
			HashMap<Rol, Set<ArtistaContratado>> mapaArtRolE = entradasRE.getValue();
			for(Map.Entry<Rol, Set<ArtistaContratado>> entradaArtE : mapaArtRolE.entrySet()) {
				Set<ArtistaContratado> setArtCont = entradaArtE.getValue();
				Rol rol = entradaArtE.getKey();
				for(ArtistaContratado ac : setArtCont) {
					for(Artista ab : artistasBaseCandidatos) {
						if(ac.comparteBanda(ab)) {
							HashMap<Rol, Set<ArtistaContratado>> cPorRol = 
									comparte.computeIfAbsent(cancion, k -> new HashMap<>());
							Set<ArtistaContratado> setArtistaC = cPorRol.computeIfAbsent(rol, k -> new HashSet<>());
							setArtistaC.add(ac);
						}else {
							HashMap<Rol, Set<ArtistaContratado>> cPorRol = 
									noComparte.computeIfAbsent(cancion, k -> new HashMap<>());
							Set<ArtistaContratado> setArtistaC = cPorRol.computeIfAbsent(rol, k -> new HashSet<>());
							setArtistaC.add(ac);
						}
						
					}
				}
			}
		}
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
		if(rolesFaltantes == null || !rolesFaltantes.isEmpty()) {
			return -1;
		}
		
		double costo = 0;
		for(Cancion c : this.listaCanciones) {
			costo += c.obtenerCosto(artistasBase);
		}
		
		return costo;
	}
	
}

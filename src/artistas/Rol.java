package artistas;

public class Rol {
	private String nombre;
	
	public Rol(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() { return this.nombre; }
	
	@Override
	public String toString() {
		return this.nombre;
	}
}

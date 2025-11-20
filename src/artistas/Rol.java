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
	
	@Override
    public int hashCode() {
        return nombre.toLowerCase().hashCode();
    }
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rol rol = (Rol) o;
        return nombre.equalsIgnoreCase(rol.nombre);
    }
}

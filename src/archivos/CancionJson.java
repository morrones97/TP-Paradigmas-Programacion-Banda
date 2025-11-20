package archivos;

import java.util.Map;

public class CancionJson {
    private String titulo;
    private Map<String, Integer> rolesRequeridos;

    public String getTitulo() {
        return this.titulo;
    }

    public Map<String, Integer> getRolesRequeridos() {
        return this.rolesRequeridos;
    }
}

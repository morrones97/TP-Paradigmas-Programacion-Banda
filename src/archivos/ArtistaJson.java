package archivos;

import java.util.ArrayList;

public class ArtistaJson {
	String name;
	ArrayList<String> historicoRoles;
	ArrayList<String> historicoBandas;
	ArtistaContratadoJSON artistaContratado;	
	
    public String getName() { return name; }
    public ArrayList<String> getHistoricoRoles() { return historicoRoles; }
    public ArrayList<String> getHistoricoBandas() { return historicoBandas; }
    public ArtistaContratadoJSON getArtistaContratado() { return artistaContratado; }
}

package testjpl;

import org.jpl7.Query;

public class TestJPL {

	public static void main(String[] args) {
		 System.out.println("-- Probando integración JPL --");

	        // Consulta Prolog simple
	        Query q = new Query("X is 3 + 5");

	        if (q.hasSolution()) {
	            System.out.println("Prolog respondió correctamente.");
	            System.out.println("Resultado X = " + q.oneSolution().get("X"));
	        } else {
	            System.out.println("NO hubo respuesta de Prolog.");
	        }
	}

}

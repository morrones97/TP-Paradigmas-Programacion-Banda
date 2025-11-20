/**
 * 
 */
/**
 * 
 */
module TP02_Armado_Recital {
	requires java.xml;
	requires jpl;
    requires com.google.gson;

    opens artistas to com.google.gson;
    opens archivos to com.google.gson; 
}
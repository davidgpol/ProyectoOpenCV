package com.proyecto.web.utils;

public class Constantes {

	public static final String VERSION_OPENCV		= "opencv_java2411";
	public static final String JAVA_LIBRARY_PATH	= "java.library.path";
	public static final String CERO_STRING			= "0";
	public static final int CERO_INT				= 0;
	public static final int UNO_INT					= 1;
	public static final String RUTA_RESOURCES			= "classifiers/";
	public static final String RUTA_IMAGES				= "images/";
	public static final String HAARCASCADE_FRONTALFACE	= "haarcascade_frontalface_alt.xml";
	
	public enum Operacion {
		ANADIR_IMAGEN("A"), MODIFICAR_IMAGEN("M"), ELIMINAR_IMAGEN("I"), CONSULTAR_IMAGEN("C");
		
		private Operacion(String valor) {}
	}
}

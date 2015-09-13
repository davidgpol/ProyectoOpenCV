package main.java;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Cargador {

	static Logger logger = Logger.getLogger(Cargador.class.getName());
	
	static {	
		logger.log(Level.INFO, "Cargando libreria OpenCV");
		System.load("D:\\Workspaces\\CargadorOpenCV\\src\\main\\resources\\opencv_java2411.dll");
	}
	
	public static void main(String[] args) {}

}
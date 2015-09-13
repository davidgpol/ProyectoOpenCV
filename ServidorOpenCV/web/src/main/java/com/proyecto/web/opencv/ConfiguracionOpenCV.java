package com.proyecto.web.opencv;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ConfiguracionOpenCV implements ApplicationListener<ContextRefreshedEvent> {
	
	static Logger logger = Logger.getLogger(ConfiguracionOpenCV.class.getName());

	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		try {
			Class.forName("main.java.Cargador");
		} catch (ClassNotFoundException cnfe) {
			logger.log(Level.SEVERE, "No se puede cargar la libreria de OpenCV, saliendo");
		}
		
	}

}
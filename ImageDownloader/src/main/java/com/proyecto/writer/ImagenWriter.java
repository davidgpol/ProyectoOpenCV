package com.proyecto.writer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.proyecto.constantes.Constantes;
import com.proyecto.modelo.PersonaUrl;
import com.proyecto.processor.PersonaProcessor;

import utils.FilesUtils;

@Component
public class ImagenWriter implements ItemWriter<PersonaUrl> {
	
	private static Logger log = LogManager.getLogger(PersonaProcessor.class);
	
	public ImagenWriter() {}
	
	@Override
	public void write(List<? extends PersonaUrl> items) throws Exception {
		PersonaUrl personaUrl = null;

		for (PersonaUrl persona : items) {
			personaUrl = new PersonaUrl(persona.getNombre(), persona.getUrls());			
			
			String rutaCarpeta = Constantes.CARPETA_TEMPORAL + personaUrl.getNombre();
			FilesUtils.deleteIfExists(rutaCarpeta);
			FilesUtils.createDirectory(rutaCarpeta);
			
			int i = 0;
			String nombreFichero = null;
			for(String imagen: personaUrl.getUrls()) {
				try {
					InputStream in = new URL(imagen).openStream();
					nombreFichero =  personaUrl.getNombre() + i + Constantes.EXTENSION_IMAGEN;
					Files.copy(in, Paths.get(rutaCarpeta + Constantes.SEPARADOR_RUTA + nombreFichero));
					log.debug("Descargando " + nombreFichero + " en " + rutaCarpeta + " con url " + imagen);
					i = i + 1;
					in.close();
				} catch(FileNotFoundException fnfe) {
					log.error("Error creando el fichero: " + nombreFichero + " con url " + imagen + " " + fnfe.getCause());
				}  
				catch(IOException ioe) {log.error(ioe.getMessage());}
			}
		}
		
	}

}

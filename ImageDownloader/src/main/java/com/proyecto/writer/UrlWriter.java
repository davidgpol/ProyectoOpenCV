package com.proyecto.writer;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.proyecto.constantes.Constantes;
import com.proyecto.excel.Excel;
import com.proyecto.modelo.PersonaUrl;

@Component
public class UrlWriter implements ItemWriter<PersonaUrl>{
	
	@Autowired
	private Excel excel;
	
	@Override
	public void write(List<? extends PersonaUrl> items) throws Exception {
		String rutaFichero = Constantes.CARPETA_TEMPORAL +  Constantes.SEPARADOR_RUTA + Constantes.NOMBRE_EXCEL;
		Path path = Paths.get(rutaFichero);
		
		if(Files.exists(path, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS})) {
			Files.delete(path);
		}
		
		excel.setHojaCalculo("Urls");
		
		// Cabecera
		excel.anadirFila();
		excel.anadirCeldas(Arrays.asList("Nombre", "Urls"));
				
		for(PersonaUrl personaUrl: items) {
			excel.anadirFila();
			excel.anadirCeldas(Arrays.asList(personaUrl.getNombre(), personaUrl.getUrls().get(0)));
			personaUrl.getUrls().remove(0);
			for(String url: personaUrl.getUrls()) {
				excel.anadirFila();
				excel.anadirCeldas(Constantes.UNO_INT, Arrays.asList(url));
			}
		}
		excel.escribirExcel(path);
	
	}

}
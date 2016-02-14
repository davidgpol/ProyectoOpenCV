package com.proyecto.processor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.proyecto.constantes.Constantes;
import com.proyecto.dto.ImagenDto;

@Component
public class ImagenProcessor implements ItemProcessor<ImagenDto, List<ImagenDto>> {

	private static Logger log = LogManager.getLogger(ImagenProcessor.class);
	
	static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
	
	@Override
	public List<ImagenDto> process(ImagenDto imagenDto) throws IOException {

		List <String> nombresImagenes = getNombresImagenes(imagenDto);
		
		List<ImagenDto> listaImagenDto = procesarYConvertirAMat(nombresImagenes, imagenDto);
	    
		return ((listaImagenDto != null) && !listaImagenDto.isEmpty())? listaImagenDto : null;
	}

	private List<String> getNombresImagenes(ImagenDto imagenDto) throws IOException {
		List <String> nombresImagenes = null;
		String nombreCarpeta = imagenDto.getNombreCarpeta();
		try (Stream<Path> stream = Files.list(Paths.get(Constantes.CARPETA_TEMPORAL + nombreCarpeta))) {
			nombresImagenes = stream
					.map(String::valueOf)
					.sorted()
					.collect(Collectors.toList());
			log.debug("Lista con las rutas de las imagenes: " + nombresImagenes);
		}
		
		if((nombresImagenes == null) || nombresImagenes.isEmpty())
			throw new IOException("No hay imagenes a procesar en la carpeta " + nombreCarpeta);
		
		return nombresImagenes;
	}
	
	private List<ImagenDto> procesarYConvertirAMat(List<String> rutasImagenes, ImagenDto imagenDto) throws IOException {
		
		List<ImagenDto> listaImagenDto = new ArrayList<ImagenDto>();
		ImagenDto imagen = null;
		String nombreImagen = imagenDto.getNombreImagen();
		int contador = 0;
		
		for (String rutaImagen : rutasImagenes) {
			imagen = new ImagenDto();			
			imagen.setNombreImagen(nombreImagen + String.valueOf(contador));
			imagen.setGrupoImagen(imagenDto.getGrupoImagen());
			Mat matriz = Highgui.imread(rutaImagen, Highgui.CV_LOAD_IMAGE_COLOR);
			imagen.setMat(matriz);	
			listaImagenDto.add(imagen);
			contador = contador + 1;
		}
		
		return listaImagenDto;
	}	
}
package com.proyecto.processor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.proyecto.comun.constantes.ConstantesComun;
import com.proyecto.comun.opencv.OpenCVComunUtils;
import com.proyecto.constantes.Constantes;
import com.proyecto.dto.ImagenDto;
import com.proyecto.modelo.entidad.Imagen;

@Component
public class OpenCVImageProcessor implements ItemProcessor<List<ImagenDto>, List<Imagen>> {

	private static Logger log = LogManager.getLogger(OpenCVImageProcessor.class);
	
	static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
	
	@Override
	public List<Imagen> process(List<ImagenDto> listaImagenDto) throws Exception {
		List<Imagen> listaImagenes = new ArrayList<Imagen>();
		Imagen imagen = null;
		for (ImagenDto imagenDto : listaImagenDto) {
			imagen = new Imagen();
			imagen.setNombreImagen(imagenDto.getNombreImagen());
			imagen.setGrupoImagen(imagenDto.getGrupoImagen());
			Mat original = imagenDto.getMat();
			Mat imagenProcesada = detectarRoiImagen(original);
			if(imagenProcesada != null) {
				byte [] bytesImagen = OpenCVComunUtils.getBytesImagen(imagenProcesada);			
				imagen.setImagen(bytesImagen);
				listaImagenes.add(imagen);
			}
		}

		return listaImagenes;
	}
	
	private Mat detectarRoiImagen(Mat original) {

		if((original.rows() == Constantes.CERO_INT) || (original.cols() == Constantes.CERO_INT))
			return null;
		
		URL url = getClass().getClassLoader().getResource(Constantes.RUTA_RESOURCES + Constantes.HAARCASCADE_FRONTALFACE);
		CascadeClassifier cascadeClassifier =  new CascadeClassifier(url.getPath().substring(ConstantesComun.UNO_INT));

		cascadeClassifier.empty();
        MatOfRect faceDetections = new MatOfRect();           

        cascadeClassifier.detectMultiScale(original, faceDetections);
        log.debug("Detectadas " + faceDetections.toArray().length + " caras");
        
        Rect rectCrop	= null;
        Mat imageRoi = null;
        Mat copiaImageRoi = null;
        
        for (Rect rect : faceDetections.toArray()) {
        	
        	// Se pinta un rectangulo en las imagenes detectadas
        	Core.rectangle(original, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
        					new Scalar(0, 255, 0));
        	
        	// Dimensiones del ROI de la imagen a procesar
            rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
            
            // ROI de la imagen original
            imageRoi = new Mat(original, rectCrop);
            // Preprocesado: se hace una copia del frame y se prepara para el reconocimiento
            copiaImageRoi = preprocesar(imageRoi);
        }
        
		return copiaImageRoi;
	}

	private Mat preprocesar(Mat imagen) {
		
		if((imagen.rows() == 0) || (imagen.cols() == 0))
			return null;
		
		// Se redimensiona la roi de la copia para el reconocimiento
		Size dimensiones = new Size(322, 393);
		Imgproc.resize(imagen, imagen, dimensiones, 0, 0, Imgproc.INTER_LINEAR);
		
		// Se convierte la copia a blanco y negro
		Imgproc.cvtColor(imagen, imagen,  Imgproc.COLOR_RGB2GRAY);

		return imagen;
	}	

}

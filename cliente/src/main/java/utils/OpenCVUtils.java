package main.java.utils;

import java.io.ByteArrayInputStream;
import java.net.URL;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;
import com.proyecto.comun.constantes.ConstantesComun;

import javafx.scene.image.Image;

public class OpenCVUtils {
	
	public static CascadeClassifier getClasificador(String clasificador) {
		URL url = OpenCVUtils.class.getClassLoader().getResource(Constantes.RUTA_RESOURCES + "haarcascade_frontalface_alt.xml");
		return new CascadeClassifier(url.getPath().substring(ConstantesComun.UNO_INT));		
	}
	
	public static Image mat2Image(Mat frame) {
		
	    // Crea una matriz temporarl de bytes
	    MatOfByte buffer = new MatOfByte();
	    
	    // Codifica el frame en la matriz temporal
	    Highgui.imencode(".png", frame, buffer);
	    
	    // Convierte la matriz de bytes en un array de bytes y devuelve una Imagen de javaFX
	    return new Image(new ByteArrayInputStream(buffer.toArray()));
	}
	
	public static byte [] getBytesMatriz(Mat mat) {
        byte [] datos = new byte[(int) (mat.total() * mat.channels())];
        mat.get(0, 0, datos);
        return datos;
	}
	
}
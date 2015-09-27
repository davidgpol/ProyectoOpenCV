package main.java.utils;

import java.io.ByteArrayInputStream;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;

import javafx.scene.image.Image;

public class OpenCVUtils {
	
	public static Image mat2Image(Mat frame) {
		
	    // Crea una matriz temporarl de bytes
	    MatOfByte buffer = new MatOfByte();
	    
	    // Codifica el frame en la matriz temporal
	    Highgui.imencode(".png", frame, buffer);
	    
	    // Convierte la matriz de bytes en un array de bytes y devuelve una Imagen de javaFX
	    return new Image(new ByteArrayInputStream(buffer.toArray()));
	}
	
}
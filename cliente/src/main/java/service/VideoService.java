package main.java.service;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import org.springframework.stereotype.Service;

import javafx.scene.image.Image;
import main.java.rest.RestCliente;
import main.java.utils.OpenCVUtils;

@Service
public class VideoService {
	
	public Image getFrame(VideoCapture videoCapture) {
		
    	Image imagenProcesada = null;
    	Mat frame = new Mat();
    	Mat frameProcesado = null;
    	
    	try {
    		// read the current frame
    		videoCapture.read(frame);
    		
    		// if the frame is not empty, process it
    		if (!frame.empty()) {
    			
    			frameProcesado = RestCliente.postProcesarFrame(frame);
    		}
    			
    		// Si se proceso, convierte la matriz de OpenCV a una imagen de javaFX
    		imagenProcesada = (frameProcesado != null) ? OpenCVUtils.mat2Image(frameProcesado) : OpenCVUtils.mat2Image(frame);
    	} catch (Exception e) {
    		System.err.println("Fallo obteniendo el frame: " + e.getMessage());
    	}

    	return imagenProcesada;
	}
	
}
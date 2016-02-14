package main.java.service;

import java.util.Arrays;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;

import com.proyecto.comun.constantes.ConstantesComun;
import com.proyecto.comun.dto.MatrizVO;
import com.proyecto.comun.opencv.OpenCVComunUtils;

import javafx.scene.image.Image;
import main.java.rest.RestCliente;
import main.java.utils.OpenCVUtils;

@Service
public class VideoService {
	
	private Mat histAnterior = new Mat();
	private Mat histActual = new Mat();
	private MatrizVO frameProcesado = null;
	private boolean primerFrame = true;

	private double compararHistogramas(boolean primerFrame, Mat frame) {
		MatOfFloat ranges = new MatOfFloat(0f, 256f);
		MatOfInt histSize = new MatOfInt(25);
		double resultado = 0.0;
		Mat frameCopia = frame.clone();
		Imgproc.cvtColor(frameCopia, frameCopia, Imgproc.COLOR_RGB2GRAY);
		if(primerFrame) {		    			
			Imgproc.calcHist(Arrays.asList(frameCopia), new MatOfInt(0), new Mat(), histAnterior, histSize, ranges);
			Core.normalize(histAnterior, histAnterior, 0, 1, Core.NORM_MINMAX, -1, new Mat());		    			
		} else {
			Imgproc.calcHist(Arrays.asList(frameCopia), new MatOfInt(0), new Mat(), histActual, histSize, ranges);		    			
			Core.normalize(histActual, histActual, 0, 1, Core.NORM_MINMAX, -1, new Mat());
			resultado = Imgproc.compareHist(histActual, histAnterior, Imgproc.CV_COMP_CORREL);
			histAnterior = histActual.clone();
		}		
		return resultado;
	}
	
	public Image getFrame(VideoCapture videoCapture) {
		
    	Image imagenProcesada = null;
    	Mat frame = new Mat();
    	    	
    	double resultado = 0.0;
    	
    	try {
    		// read the current frame
    		videoCapture.read(frame);
    		
    		// if the frame is not empty, process it
    		if (!frame.empty()) {
    			
		    	// Comparacion de histogramas
				if(primerFrame) {
					resultado = compararHistogramas(primerFrame, frame);
					primerFrame = false;
				}
				else
					resultado = compararHistogramas(false, frame);    			
    			
	    		if(resultado < ConstantesComun.UMBRAL_RECONOCIMIENTO) {
	    			System.out.println("Por debajo del umbral ( " + resultado + " < " + ConstantesComun.UMBRAL_RECONOCIMIENTO + " ) enviando al servidor.");
	    			frameProcesado = RestCliente.postProcesarFrame(frame);
	    			imagenProcesada = OpenCVUtils.mat2Image(OpenCVComunUtils.matrizVOToMat(frameProcesado));
				} 
	    		else {
					System.out.println("Por encima del umbral ( " + resultado + " >= " + ConstantesComun.UMBRAL_RECONOCIMIENTO + " ) realizando tracking.");
					frame = OpenCVComunUtils.track(frameProcesado.getListaCoordenadas(), frame);
					imagenProcesada = OpenCVUtils.mat2Image(frame);
				}							    		
    		}    			
    		
    	} catch (Exception e) {
    		System.err.println("Fallo obteniendo el frame: " + e.getMessage());
    	}

    	return imagenProcesada;
	}
	
}
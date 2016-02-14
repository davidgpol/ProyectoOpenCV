package com.proyecto.web.opencv.service;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.proyecto.comun.dto.MatrizVO;
import com.proyecto.comun.opencv.OpenCVComunUtils;
import com.proyecto.web.configuracion.ContextoConfiguracionTest;
import com.proyecto.web.utils.Constantes;

import testUtils.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ContextoConfiguracionTest.class)
public class OpenCVDetectorServiceVideoTest {

	private static final String NOMBRE_VIDEO = "video.avi";
	
	@Autowired
	private OpenCVDetectorService openCVDetectorService;
	
	@Autowired
	private ProcesadorOpenCVService procesadorOpenCVService;	
	
	Mat histAnterior = new Mat();
	Mat histActual = new Mat();	
	
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	private void crearAwtPanel(JFrame jframe, JLabel vidpanel) {
		Dimension dimensiones = new Dimension(Utils.DIMENSIONES_VIDEO_FILAS, Utils.DIMENSIONES_VIDEO_COLUMNAS);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    jframe.setMinimumSize(dimensiones) ;
	    vidpanel.setMinimumSize(dimensiones); 
	    jframe.setContentPane(vidpanel);
	    jframe.setVisible(true);
	}
	
	private double compararHistogramas(boolean primerFrame, Mat frameCopia) {	
		MatOfFloat rangos = new MatOfFloat(Utils.RANGO_INICIO_TEST, Utils.RANGO_FIN_TEST);
		MatOfInt tamanoHistorial = new MatOfInt(Utils.TAMANO_HISTORIAL_TEST);
		double resultado = 0.0;
		if(primerFrame) {
			Imgproc.calcHist(Arrays.asList(frameCopia), new MatOfInt(Utils.CANALES_TEST), new Mat(), histAnterior, tamanoHistorial, rangos);
			Core.normalize(histAnterior, histAnterior, Utils.RANGO_MIN_NORMALIZAR_TEST,
					Utils.RANGO_MAX_NORMALIZAR_TEST, Core.NORM_MINMAX, Utils.TIPO_NORMALIZAR_TEST, new Mat());		    			
		} else {
			Imgproc.calcHist(Arrays.asList(frameCopia), new MatOfInt(Utils.CANALES_TEST), new Mat(), histActual, tamanoHistorial, rangos);		    			
			Core.normalize(histActual, histActual, Utils.RANGO_MIN_NORMALIZAR_TEST,
							Utils.RANGO_MAX_NORMALIZAR_TEST, Core.NORM_MINMAX, Utils.TIPO_NORMALIZAR_TEST, new Mat());
			resultado = Imgproc.compareHist(histActual, histAnterior, Imgproc.CV_COMP_CORREL);
			histAnterior = histActual.clone();
		}		
		return resultado;
	}	
	
	@Test
	public void detectarCarasVideoTest() throws InterruptedException, IOException {		
		JFrame jframe = new JFrame("Ejemplo OpenCV");
		JLabel vidpanel = new JLabel();
		crearAwtPanel(jframe, vidpanel);
		
		ImageIcon image = null;
		BufferedImage bufferedImage = null;
	    String nombreVideo = Utils.getRutaRecurso(Constantes.RUTA_VIDEOS, NOMBRE_VIDEO);

		Mat frame = new Mat();
		Mat frameCopia =  new Mat();
		MatrizVO imagenReconocida = null;
		
		boolean primerFrame = true;
		
		double resultado = 0.0;
		
		VideoCapture videoCapture = new VideoCapture(0);
		if(videoCapture.isOpened()) {
			procesadorOpenCVService.getImagenes();
		    while (videoCapture.read(frame)) {

				Imgproc.cvtColor(frame, frameCopia, Imgproc.COLOR_RGB2GRAY);
				
		    	// Comparacion de histogramas
				if(primerFrame) {
					resultado = compararHistogramas(primerFrame, frameCopia);
					primerFrame = false;
				}
				else
					resultado = compararHistogramas(false, frameCopia);
				
				System.out.println(resultado);
		    	// Deteccion y reconocimiento
				MatrizVO matrizVO = new MatrizVO(frame.rows(), frame.cols(), frame.type(), OpenCVComunUtils.getBytesMatriz(frame), null);
				
	    		if(resultado < Utils.UMBRAL_RECONOCIMIENTO_TEST) {
					imagenReconocida = openCVDetectorService.reconocer(matrizVO);
					bufferedImage = Utils.mat2AwtImage(OpenCVComunUtils.matrizVOToMat(imagenReconocida));
				} else {					
					frame= OpenCVComunUtils.track(imagenReconocida.getListaCoordenadas(), frame);
					bufferedImage = Utils.mat2AwtImage(frame);
				}
				
				// Resultado
	            image = new ImageIcon(bufferedImage);
	            vidpanel.setIcon(image);
	            vidpanel.repaint();
	            // 33 fps
	            Thread.sleep(33);
	
		    }
		}
	}

}
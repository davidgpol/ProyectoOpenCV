package com.proyecto.web.opencv.service;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.comun.dto.Coordenada;
import com.proyecto.comun.dto.MatrizVO;
import com.proyecto.comun.opencv.OpenCVComunUtils;
import com.proyecto.web.utils.Constantes;
import com.proyecto.web.utils.OpenCVWebUtils;

@Service
public class OpenCVDetectorService {

	@Autowired
	ProcesadorOpenCVService procesadorOpenCV;
	
	//TODO: HACER UN DETECTOR PARAMETRIZADO!!	
	private MatOfRect detectarCaras(Mat frame, CascadeClassifier clasificador) {
		
        CascadeClassifier faceDetector = clasificador;

        MatOfRect faceDetections = new MatOfRect();
        
        faceDetector.detectMultiScale(frame, faceDetections);

        System.out.println("Detectadas " + faceDetections.toArray().length + " caras");
        
        return faceDetections;
	}
	
	private Mat preprocesar(Mat imageRoi) {
		Mat copiaImageRoi = imageRoi.clone();
		
		// Se redimensiona la roi de la copia para el reconocimientos
		Size dimensiones = new Size(322, 393);
		Imgproc.resize(imageRoi, copiaImageRoi, dimensiones, 0, 0, Imgproc.INTER_LINEAR);
		
		// Se convierte la copia a blanco y negro
		Imgproc.cvtColor(copiaImageRoi, copiaImageRoi,  Imgproc.COLOR_RGB2GRAY);

		return copiaImageRoi;
	}
	
	private Coordenada postProcesar(Rect rect, Mat imageRoi, MatrizVO matrizVO, String nombre) {
		Coordenada coordenada = new Coordenada();
		coordenada.setCoordenadaX(rect.x);
		coordenada.setAncho(rect.width);
		coordenada.setCoordenadaY(rect.y);
		coordenada.setAlto(rect.height);
		
        if(nombre != null) {
        	coordenada.setNombre(nombre);
        	Core.putText(imageRoi, nombre, new Point(0, 50), Core.FONT_HERSHEY_SIMPLEX, 1, new Scalar(0, 255, 0), 4);
        }	
		return coordenada;
	}
	
	private MatrizVO getCaras(MatOfRect matOfRect, Mat frame, MatrizVO matrizVO) {
		Rect rectCrop	= null;
		Mat imageRoi = null;
		Mat copiaImageRoi = null;
		String nombre = null;
		Coordenada coordenada = null;
		List <Coordenada> listaCoordenadas = new ArrayList<Coordenada>();
        for (Rect rect : matOfRect.toArray()) {
        	
        	// Se pinta un rectangulo en las imagenes detectadas
        	Core.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
        					new Scalar(0, 255, 0));
        	
        	// Dimensiones del ROI de la imagen a procesar
            rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
            
            // ROI de la imagen original
            imageRoi = new Mat(frame, rectCrop);
            // Preprocesado: se hace una copia del frame y se prepara para el reconocimiento
            copiaImageRoi = preprocesar(imageRoi);
            // Se reconocen las caras de la imagen
            nombre = procesadorOpenCV.reconocerImagen(copiaImageRoi);
            // Postprocesado: Se anade informacion de la imagen reconocida para tracking en el cliente
            coordenada = postProcesar(rect, imageRoi, matrizVO, nombre);
            listaCoordenadas.add(coordenada);
        }
        // Se actualiza el frame con las caras detectadas y reconocidas
        matrizVO.setDatos(OpenCVComunUtils.getBytesMatriz(frame));
        matrizVO.setListaCoordenadas(listaCoordenadas);
        return matrizVO;
	}
	
	public MatrizVO reconocer(MatrizVO matrizVO) {
		
		Mat frame = OpenCVComunUtils.matrizVOToMat(matrizVO);
		
		// Detecta las caras en el frame
		MatOfRect frameProcesadas = detectarCaras(frame, OpenCVWebUtils.getClasificador(Constantes.HAARCASCADE_FRONTALFACE));

		if(frameProcesadas == null) {
			return null;
		}
		
		// Procesa las imagenes detectadas y las reconoce
		matrizVO = getCaras(frameProcesadas, frame, matrizVO);

		return matrizVO;
		
	}
}
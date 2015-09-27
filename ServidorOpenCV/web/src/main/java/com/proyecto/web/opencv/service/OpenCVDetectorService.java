package com.proyecto.web.opencv.service;

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

import com.proyecto.comun.dto.MatrizVO;
import com.proyecto.web.utils.Constantes;
import com.proyecto.web.utils.OpenCVUtils;


@Service
public class OpenCVDetectorService {

	@Autowired
	ProcesadorOpenCV procesadorOpenCV;
	
	private MatOfRect detectarCaras(Mat frame, CascadeClassifier clasificador) {
		
        CascadeClassifier faceDetector = clasificador;

        MatOfRect faceDetections = new MatOfRect();
        
        faceDetector.detectMultiScale(frame, faceDetections);

        System.out.println("Detectadas " + faceDetections.toArray().length + " caras");
        
        return faceDetections;
	}

	//TODO: HACER UN DETECTOR PARAMETRIZADO!!
	
	private Mat pintarCaras(Mat frame, MatOfRect matOfRect) {
        for (Rect rect : matOfRect.toArray()) {
        	
            Core.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
        }	
        return frame;
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
	
	private Mat getCaras(MatOfRect matOfRect, Mat frame) {
		Rect rectCrop	= null;
		
        for (Rect rect : matOfRect.toArray()) {
        	
        	// Dimensiones del ROI de la imagen a procesar
            rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
            
            // ROI de la imagen original
            Mat imageRoi = new Mat(frame, rectCrop);
            
//            Highgui.imwrite("C:\\Users\\David\\AppData\\Local\\Temp\\imagenReconocida.jpg", image_roi);
//            
//            Highgui.imwrite("C:\\Users\\David\\AppData\\Local\\Temp\\imagenReconocida2.jpg", image_roi);
            //TODO: RECONOCER LAS CARAS AQUI!!!
            // TODO: ETAPA DE PREPROCESADO!!!
            Mat copiaImageRoi = preprocesar(imageRoi);
            procesadorOpenCV.reconocerImagen(copiaImageRoi, imageRoi);
            Core.putText(imageRoi, "Reconocido!!", new Point(0, 50), Core.FONT_HERSHEY_SIMPLEX, 1, new Scalar(0, 255, 0), 4);
        }
        
        return frame;
	}
	
	public MatrizVO reconocer(MatrizVO matrizVO) {
		
		Mat frame = com.proyecto.comun.opencv.OpenCVUtils.matrizVOToMat(matrizVO);
		
		// Detecta las caras en el frame
		MatOfRect frameProcesadas = detectarCaras(frame, OpenCVUtils.getClasificador(Constantes.HAARCASCADE_FRONTALFACE));

		// Pinta rectangulos en las caras detectadas
		Mat framePintadas = pintarCaras(frame, frameProcesadas);

		if(framePintadas == null) {
			return null;
		}
		getCaras(frameProcesadas, framePintadas);
		// Transforma el frame procesado en un matrizVO
		MatrizVO matrizVOResultado = com.proyecto.comun.opencv.OpenCVUtils.matToMatrizVO(framePintadas);
		return matrizVOResultado;
		
	}
}
package main.java.service;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.stereotype.Service;

import com.proyecto.comun.dto.MatrizVO;

import main.java.utils.OpenCVUtils;

@Service
public class OpenCVService {

	public MatOfRect detectarCaras(Mat frame, CascadeClassifier clasificador) {
		
        CascadeClassifier faceDetector = clasificador;

        MatOfRect faceDetections = new MatOfRect();
        
        faceDetector.detectMultiScale(frame, faceDetections);

        System.out.println("Detectadas " + faceDetections.toArray().length + " caras");
        
        return faceDetections;
	}

	//TODO: HACER UN DETECTOR PARAMETRIZADO!!
	
	public Mat pintarCaras(Mat frame, MatOfRect matOfRect) {
        for (Rect rect : matOfRect.toArray()) {
        	
            Core.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }	
        return frame;
	}
	
	public List <MatrizVO> getCaras(MatOfRect matOfRect, Mat frame) {
		Rect rectCrop				= null;
		MatrizVO matrizVO			= null;
		List <MatrizVO> listaCaras	= new ArrayList <MatrizVO> ();
		
        for (Rect rect : matOfRect.toArray()) {        	
            rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
            Mat image_roi = new Mat(frame, rectCrop);
            
            Size dimensiones = new Size(322, 393);
            Imgproc.resize(image_roi, image_roi, dimensiones, 0, 0, Imgproc.INTER_LINEAR);
            Imgproc.cvtColor(image_roi, image_roi,  Imgproc.COLOR_RGB2GRAY);

            matrizVO = new MatrizVO(image_roi.rows(), image_roi.cols(),  CvType.CV_8UC1, OpenCVUtils.getBytesMatriz(image_roi));
            
            listaCaras.add(matrizVO);
        }
        
        return listaCaras;
	}	
}
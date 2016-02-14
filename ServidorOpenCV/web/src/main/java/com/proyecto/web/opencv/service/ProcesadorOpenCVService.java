package com.proyecto.web.opencv.service;

import static org.bytedeco.javacpp.opencv_core.CV_32SC1;

import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.bytedeco.javacpp.opencv_contrib.FaceRecognizer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.proyecto.comun.dto.MatrizVO;
import com.proyecto.comun.opencv.OpenCVComunUtils;
import com.proyecto.modelo.entidad.Imagen;
import com.proyecto.modelo.servicio.ImagenServicio;
import com.proyecto.web.utils.Constantes;

@Component
public class ProcesadorOpenCVService {
		
	@Autowired
	private ImagenServicio imagenServicio;
	
	@Resource(name = "faceRecognizer")
	private FaceRecognizer faceRecognizer;
	
	public ProcesadorOpenCVService() {};
	
	public ProcesadorOpenCVService(FaceRecognizer faceRecognizer) {
		this.faceRecognizer = faceRecognizer;
	}
	
	private Mat openCV2JavaCVMat(org.opencv.core.Mat matriz) {
        byte[] return_buff = new byte[(int) (matriz.total() * matriz.channels())];
        matriz.get(0, 0, return_buff);
        
		Mat javaCVMat = new Mat(matriz.rows(), matriz.cols(),matriz.type());
		javaCVMat.data().put(return_buff);
		
		return javaCVMat;
	}
	
	private Mat matrizVO2JavaCVMat(MatrizVO matrizVO) {
    	// Transformacion matrices OpenCV a JavaCV
		org.opencv.core.Mat mat = OpenCVComunUtils.matrizVOToMat(matrizVO);		
		return openCV2JavaCVMat(mat);
	} 
	
	private String getNombreCara(Double grupo) {
		Imagen imagen = imagenServicio.getByGrupo(grupo.longValue());
		return (imagen != null) ? imagen.getNombreImagen() : null;
	}
	
	public void getImagenes() throws IOException {
		
		// Obteber imagenes
		List <Imagen> listaImagenes = imagenServicio.cargarImagenes();
		
		int tamanoMatriz	= 0;
		List<Mat> listaMat	= new ArrayList<Mat>();
		MatrizVO matrizVO	= null;

		for(Imagen imagen: listaImagenes) {
			matrizVO = OpenCVComunUtils.byteImageToMatrizVO(imagen.getImagen());
			listaMat.add(matrizVO2JavaCVMat(matrizVO));
		}

		tamanoMatriz = listaMat.size();
		
		MatVector images = new MatVector(tamanoMatriz);

        Mat labels = new Mat(tamanoMatriz, Constantes.UNO_INT, CV_32SC1);
        @SuppressWarnings("deprecation")
		IntBuffer labelsBuf = labels.getIntBuffer();		
		
        for(int i = 0; i < tamanoMatriz; i++) {
        	images.put(i, listaMat.get(i));
        	labelsBuf.put(i, listaImagenes.get(i).getGrupoImagen().intValue());
        }		

        faceRecognizer.train(images, labels);
        
        System.out.println("Imagenes cargadas y entrenadas");
	}	
	
    public String reconocerImagen(org.opencv.core.Mat copiaImageRoi) {   	    	    	
        
		int [] labelArray = new int[10];
		double [] confidence = new double[1];
		
		Mat javaCVMat = openCV2JavaCVMat(copiaImageRoi);
        faceRecognizer.predict(javaCVMat, labelArray, confidence);

        System.out.println("Match found!! label: " + labelArray[0] + " confidence: " + confidence[0]);
        return (confidence[0] < 15000) ? getNombreCara((double) labelArray[0]) : null;
    }
}
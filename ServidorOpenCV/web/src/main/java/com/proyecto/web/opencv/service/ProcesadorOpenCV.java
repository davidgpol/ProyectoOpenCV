package com.proyecto.web.opencv.service;

import static org.bytedeco.javacpp.opencv_contrib.createEigenFaceRecognizer;
import static org.bytedeco.javacpp.opencv_core.CV_32SC1;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.bytedeco.javacpp.opencv_contrib.FaceRecognizer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.springframework.stereotype.Component;

import com.proyecto.comun.dto.MatrizVO;
import com.proyecto.comun.opencv.OpenCVUtils;
import com.proyecto.modelo.dao.ImagenDao;
import com.proyecto.modelo.entidad.Imagen;
import com.proyecto.web.utils.Constantes;

@Component
public class ProcesadorOpenCV {
		
	@Resource(name = "imagenDaoGenerico")
	private ImagenDao imagenDao;
	
	private FaceRecognizer faceRecognizer;
	
	private Mat openCV2JavaCVMat(org.opencv.core.Mat matriz) {
        byte[] return_buff = new byte[(int) (matriz.total() * matriz.channels())];
        matriz.get(0, 0, return_buff);
        
		Mat javaCVMat = new Mat(matriz.rows(), matriz.cols(),matriz.type());
		javaCVMat.data().put(return_buff);
		
		return javaCVMat;
	}
	
	private Mat matrizVO2JavaCVMat(MatrizVO matrizVO) {
    	// Transformacion matrices OpenCV a JavaCV
		org.opencv.core.Mat mat = OpenCVUtils.matrizVOToMat(matrizVO);
		Highgui.imwrite("C:\\Users\\David\\AppData\\Local\\Temp\\imagenReconocida.jpg", mat);				
		return openCV2JavaCVMat(mat);
	}
	
	private MatrizVO byteImageToMatrizVO(byte [] imagen) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(imagen);

		BufferedImage bi = ImageIO.read(bais);	
		
		WritableRaster raster = bi.getRaster();  
		DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();  
		byte[] data = dataBuffer.getData();	
		
		return new MatrizVO(bi.getHeight(), bi.getWidth(), CvType.CV_8U, data);
	} 
	
	private MatVector getImagenes() throws IOException {
		
		// Obteber imagenes
		List <Imagen> listaImagenes = imagenDao.getAll();
		
		int tamanoMatriz	= 0;
		List<Mat> listaMat	= new ArrayList<Mat>();
		MatrizVO matrizVO	= null;

		for(Imagen imagen: listaImagenes) {
			matrizVO = byteImageToMatrizVO(imagen.getImagen());
			listaMat.add(matrizVO2JavaCVMat(matrizVO));
		}

		tamanoMatriz = listaMat.size();
		
		MatVector images = new MatVector(tamanoMatriz);

        Mat labels = new Mat(tamanoMatriz, Constantes.UNO_INT, CV_32SC1);
        IntBuffer labelsBuf = labels.getIntBuffer();		
		
        for(int i = 0; i < tamanoMatriz; i++) {
        	images.put(i, listaMat.get(i));
        	labelsBuf.put(i,listaImagenes.get(i).getGrupoImagen().intValue());
        }       
		
        faceRecognizer = createEigenFaceRecognizer();

        faceRecognizer.train(images, labels);        
        
		return images;
	}
	
    public void reconocerImagen(org.opencv.core.Mat copiaImageRoi, org.opencv.core.Mat imageRoi ) {

		List <Imagen> listaImagenes = imagenDao.getAll();
		
		MatrizVO matrizVO = null;

		for(Imagen imagen: listaImagenes) {
			try {
				matrizVO = byteImageToMatrizVO(imagen.getImagen());
			} catch (IOException e) {
				e.printStackTrace();
			}					
			org.opencv.core.Mat matriz = OpenCVUtils.matrizVOToMat(matrizVO);
			
			Highgui.imwrite("C:\\Users\\David\\AppData\\Local\\Temp\\imagenReconocida.jpg", matriz);
		}    	
    	
    	MatVector images = null;
		try {
			images = getImagenes();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		int [] labelArray = new int[1];
		double [] confidence = new double[1];
		Mat javaCVMat = openCV2JavaCVMat(copiaImageRoi);
        faceRecognizer.predict(javaCVMat, labelArray, confidence);

        	System.out.println("Match found!! label: " + labelArray[0] + " confidence: " + confidence[0]);
        	Mat recognizedImage = images.get(labelArray[0]);
        	org.opencv.core.Mat matriz = new org.opencv.core.Mat(recognizedImage.address());
        	Core.putText(matriz, "Reconocido!!", new org.opencv.core.Point(50,100), Core.FONT_HERSHEY_SIMPLEX, 1, new Scalar(0, 255, 0), 4);
        	Highgui.imwrite("C:\\Users\\David\\AppData\\Local\\Temp\\imagenReconocida.jpg", matriz);
    }
}
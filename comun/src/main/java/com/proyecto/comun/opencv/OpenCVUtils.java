package com.proyecto.comun.opencv;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import com.proyecto.comun.dto.MatrizVO;

public class OpenCVUtils {
	
	public static MatrizVO matToMatrizVO(Mat mat) {
		return new MatrizVO(mat.rows(), mat.cols(), mat.type(), OpenCVUtils.getBytesMatriz(mat));
	}
	
	public static Mat matrizVOToMat(MatrizVO matrizVO) {
		Mat matriz = new Mat(matrizVO.getFilas(), matrizVO.getColumnas(), matrizVO.getTipo());
		matriz.put(0, 0, matrizVO.getDatos());
		return matriz;
	}
	
	public static byte [] getBytesMatriz(Mat mat) {
        byte [] datos = new byte[(int) (mat.total() * mat.channels())];
        mat.get(0, 0, datos);
        return datos;
	}
	
	public static MatrizVO byteImageToMatrizVO(byte [] imagen) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(imagen);

		BufferedImage bi = ImageIO.read(bais);	
		
		WritableRaster raster = bi.getRaster();  
		DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();  
		byte[] data = dataBuffer.getData();	
		
		return new MatrizVO(bi.getHeight(), bi.getWidth(), CvType.CV_8U, data);
	}	
	
}
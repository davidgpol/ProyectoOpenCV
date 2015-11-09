package com.proyecto.comun.opencv;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

import com.proyecto.comun.dto.Coordenada;
import com.proyecto.comun.dto.MatrizVO;

public class OpenCVComunUtils {
	
	public static MatrizVO matToMatrizVO(Mat mat) {
		return new MatrizVO(mat.rows(), mat.cols(), mat.type(), OpenCVComunUtils.getBytesMatriz(mat), null);
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
		
		return new MatrizVO(bi.getHeight(), bi.getWidth(), CvType.CV_8U, data, null);
	}
	
	public static Mat track(List<Coordenada> listaCoordenadas, Mat frame) {
		if(!listaCoordenadas.isEmpty()) {
			for(Coordenada coordenada: listaCoordenadas) {
	            Core.rectangle(frame, new Point(coordenada.getCoordenadaX(), coordenada.getCoordenadaY()),
	            				new Point(coordenada.getCoordenadaX() + coordenada.getAncho(),
	            						coordenada.getCoordenadaY() + coordenada.getAlto()),
	                    new Scalar(255, 255, 0));
	            if(coordenada.getNombre() != null)
		            Core.putText(frame, coordenada.getNombre(), new Point(coordenada.getCoordenadaX(), coordenada.getCoordenadaY() + 50),
		            				Core.FONT_HERSHEY_SIMPLEX, 1, new Scalar(255, 255, 0), 4);
			}
		}
		return frame;
	}
}
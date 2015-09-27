package com.proyecto.comun.opencv;

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
	
}
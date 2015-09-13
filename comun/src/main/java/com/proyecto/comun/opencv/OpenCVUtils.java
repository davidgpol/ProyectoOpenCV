package com.proyecto.comun.opencv;

import org.opencv.core.Mat;

public class OpenCVUtils {
	
	public static byte [] getBytesMatriz(Mat mat) {
        byte [] datos = new byte[(int) (mat.total() * mat.channels())];
        mat.get(0, 0, datos);
        return datos;
	}
	
}
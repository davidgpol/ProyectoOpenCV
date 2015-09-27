package com.proyecto.web.utils;

import java.net.URL;

import org.opencv.objdetect.CascadeClassifier;

import com.proyecto.comun.constantes.ConstantesComun;


public class OpenCVUtils {
	public static CascadeClassifier getClasificador(String clasificador) {
		URL url = OpenCVUtils.class.getClassLoader().getResource(Constantes.RUTA_RESOURCES + clasificador);
		return new CascadeClassifier(url.getPath().substring(ConstantesComun.UNO_INT));		
	}
}
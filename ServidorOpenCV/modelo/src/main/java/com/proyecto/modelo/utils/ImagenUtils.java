package com.proyecto.modelo.utils;

import org.apache.commons.codec.binary.Base64;

import com.proyecto.comun.constantes.ConstantesComun;

public class ImagenUtils {

	public static final String ORDER_BY_GRUPO_IMAGEN = "grupoImagen";
	
	public static String getBase64Imagen(byte [] imagen) {
		return Base64.encodeBase64String(imagen);		
	}
	
	public static String procesarNombreImagen(String nombreImagen) {
		String nombre	= nombreImagen.substring(ConstantesComun.CERO_INT, nombreImagen.length() - ConstantesComun.DIGITOS_NOMBRE_IMAGEN);
		int numero		= Integer.parseInt(nombreImagen.substring(nombreImagen.length() - ConstantesComun.DIGITOS_NOMBRE_IMAGEN));
		return nombre + new StringBuffer(numero + 1);
	}
	
}
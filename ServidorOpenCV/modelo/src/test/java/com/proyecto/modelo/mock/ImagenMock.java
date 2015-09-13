package com.proyecto.modelo.mock;

import com.proyecto.modelo.entidad.Imagen;

public class ImagenMock {

	private static String NOMBRE_IMAGEN_MOCK = "Imagen mock";
	
	public static Imagen getImagen() {
		Imagen imagen =  new Imagen();
		imagen.setGrupoImagen(10000L);
		imagen.setNombreImagen(NOMBRE_IMAGEN_MOCK);
		imagen.setImagen(new byte[10000]);
		return imagen;
	}
	
}

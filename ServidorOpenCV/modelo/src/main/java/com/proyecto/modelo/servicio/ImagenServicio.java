package com.proyecto.modelo.servicio;

import java.util.List;

import com.proyecto.modelo.dto.Formulario;
import com.proyecto.modelo.dto.ImagenDto;


public interface ImagenServicio {

	public List <ImagenDto> consultarImagenes(Formulario formulario);
	public int eliminarImagenes(Formulario formulario);
	public int modificarImagenes(Formulario formulario);
	public int anadirImagenes(Formulario formulario);
	
}
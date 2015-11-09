package com.proyecto.modelo.servicio;

import java.util.List;

import com.proyecto.modelo.dto.Formulario;
import com.proyecto.modelo.dto.ImagenDto;
import com.proyecto.modelo.entidad.Imagen;


public interface ImagenServicio {

	public List <ImagenDto> consultarImagenes(Formulario formulario);
	public int eliminarImagenes(Formulario formulario);
	public int modificarImagenes(Formulario formulario);
	public int anadirImagenes(Formulario formulario);
	public List <Imagen> cargarImagenes();
	public Imagen getByGrupo(long grupo);
	public boolean comprobarCache();
}
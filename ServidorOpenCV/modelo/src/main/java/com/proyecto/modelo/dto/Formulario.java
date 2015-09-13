package com.proyecto.modelo.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class Formulario {

	Long idImagen;
	
	private Long idGrupo;
	
	private String nombreImagen;
	
	private List <MultipartFile> imagenes;
	
	public Long getIdImagen() {
		return idImagen;
	}

	public void setIdImagen(Long idImagen) {
		this.idImagen = idImagen;
	}

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public List<MultipartFile> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<MultipartFile> imagenes) {
		this.imagenes = imagenes;
	}

	@Override
	public String toString() {
		return "Formulario [idImagen=" + idImagen + ", idGrupo=" + idGrupo + ", nombreImagen=" + nombreImagen
				+ ", imagenes=" + imagenes + "]";
	}
	
}
package com.proyecto.dto;

import org.opencv.core.Mat;

public class ImagenDto {

	private String nombreCarpeta;
	private Long grupoImagen;
	private String nombreImagen;
	private Mat mat;
	
	public ImagenDto() {}
	
	public ImagenDto(String nombreCarpeta, Long grupoImagen, String nombreImagen, Mat mat) {
		this.nombreCarpeta = nombreCarpeta;
		this.grupoImagen = grupoImagen;
		this.nombreImagen = nombreImagen;
		this.mat = mat;
	}

	public String getNombreCarpeta() {
		return nombreCarpeta;
	}

	public void setNombreCarpeta(String nombreCarpeta) {
		this.nombreCarpeta = nombreCarpeta;
	}

	public Long getGrupoImagen() {
		return grupoImagen;
	}

	public void setGrupoImagen(Long grupoImagen) {
		this.grupoImagen = grupoImagen;
	}

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public Mat getMat() {
		return mat;
	}

	public void setMat(Mat mat) {
		this.mat = mat;
	}

	@Override
	public String toString() {
		return "ImagenDto [nombreCarpeta=" + nombreCarpeta + ", grupoImagen=" + grupoImagen + ", nombreImagen="
				+ nombreImagen + ", mat=" + mat + "]";
	}
		
}
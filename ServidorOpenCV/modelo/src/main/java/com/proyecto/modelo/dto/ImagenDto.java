package com.proyecto.modelo.dto;

public class ImagenDto {

	private Long idImagen;
	
	private Long grupoImagen;
	
	private String nombreImagen;
	
	private String base64Imagen;

	public ImagenDto() {}
	
	public ImagenDto(Long idImagen, Long grupoImagen, String nombreImagen, String base64Imagen) {
		this.idImagen = idImagen;
		this.grupoImagen = grupoImagen;
		this.nombreImagen = nombreImagen;
		this.base64Imagen = base64Imagen;
	}

	public Long getIdImagen() {
		return idImagen;
	}

	public void setIdImagen(Long idImagen) {
		this.idImagen = idImagen;
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

	public String getBase64Imagen() {
		return base64Imagen;
	}

	public void setBase64Imagen(String base64Imagen) {
		this.base64Imagen = base64Imagen;
	}

	@Override
	public String toString() {
		return "ImagenDto [idImagen=" + idImagen + ", grupoImagen=" + grupoImagen + ", nombreImagen=" + nombreImagen
				+ ", base64Imagen=" + base64Imagen + "]";
	}
	
}
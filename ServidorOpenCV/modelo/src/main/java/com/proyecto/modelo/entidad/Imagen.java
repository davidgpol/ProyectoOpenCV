package com.proyecto.modelo.entidad;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "imagenes")
public class Imagen {

	@Id
	@Column(name = "IdImagen")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idImagen;
	
	@Column(name = "GrupoImagen")
	private Long grupoImagen;
	
	@Column(name = "NombreImagen")
	private String nombreImagen;
	
	@Lob
	@Column(name = "Imagen")
	private byte [] imagen;	
	
	public Imagen() {}

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

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((grupoImagen == null) ? 0 : grupoImagen.hashCode());
		result = prime * result
				+ ((idImagen == null) ? 0 : idImagen.hashCode());
		result = prime * result + Arrays.hashCode(imagen);
		result = prime * result
				+ ((nombreImagen == null) ? 0 : nombreImagen.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Imagen other = (Imagen) obj;
		if (grupoImagen == null) {
			if (other.grupoImagen != null)
				return false;
		} else if (!grupoImagen.equals(other.grupoImagen))
			return false;
		if (idImagen == null) {
			if (other.idImagen != null)
				return false;
		} else if (!idImagen.equals(other.idImagen))
			return false;
		if (!Arrays.equals(imagen, other.imagen))
			return false;
		if (nombreImagen == null) {
			if (other.nombreImagen != null)
				return false;
		} else if (!nombreImagen.equals(other.nombreImagen))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Imagen [idImagen=" + idImagen + ", grupoImagen=" + grupoImagen
				+ ", nombreImagen=" + nombreImagen + ", imagen="
				+ Arrays.toString(imagen) + "]";
	}
		
}

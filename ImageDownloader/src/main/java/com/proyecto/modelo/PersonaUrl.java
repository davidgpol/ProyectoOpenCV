package com.proyecto.modelo;

import java.util.List;

public class PersonaUrl {
	
	private String nombre;
	private List<String> urls;
	
	public PersonaUrl() {}

	public PersonaUrl(String nombre, List<String> urls) {
		this.nombre = nombre;
		this.urls = urls;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	@Override
	public String toString() {
		return "PersonaUrl [nombre=" + nombre + ", urls=" + urls + "]";
	}
	
}
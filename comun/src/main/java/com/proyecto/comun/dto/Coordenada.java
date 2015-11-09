package com.proyecto.comun.dto;

public class Coordenada {

	private int coordenadaX;
	private int ancho;
	private int coordenadaY;
	private int alto;
	private String nombre;
	
	public Coordenada() {}

	public Coordenada(int coordenadaX, int ancho, int coordenadaY, int alto, String nombre) {
		super();
		this.coordenadaX = coordenadaX;
		this.ancho = ancho;
		this.coordenadaY = coordenadaY;
		this.alto = alto;
		this.nombre = nombre;
	}

	public int getCoordenadaX() {
		return coordenadaX;
	}

	public void setCoordenadaX(int coordenadaX) {
		this.coordenadaX = coordenadaX;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getCoordenadaY() {
		return coordenadaY;
	}

	public void setCoordenadaY(int coordenadaY) {
		this.coordenadaY = coordenadaY;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Coordenada [coordenadaX=" + coordenadaX + ", ancho=" + ancho + ", coordenadaY=" + coordenadaY
				+ ", alto=" + alto + ", nombre=" + nombre + "]";
	}
	
}

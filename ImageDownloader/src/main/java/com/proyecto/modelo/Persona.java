package com.proyecto.modelo;

public class Persona {

	private String nombre;
	private int numeroImagenes;
		
	public Persona() {}
	
	public Persona(String nombre, int numeroImagenes) {
		this.nombre = nombre;
		this.numeroImagenes = numeroImagenes;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getNumeroImagenes() {
		return numeroImagenes;
	}
	public void setNumeroImagenes(int numeroImagenes) {
		this.numeroImagenes = numeroImagenes;
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", numeroImagenes=" + numeroImagenes + "]";
	}

}
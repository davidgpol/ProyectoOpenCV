package com.proyecto.comun.dto;

import java.util.Arrays;

public class MatrizVO {

	private int filas;
	private int columnas;
	private int tipo;
	private byte [] datos;
	
	public MatrizVO() {}
	
	public MatrizVO(int filas, int columnas, int tipo, byte[] datos) {
		this.filas = filas;
		this.columnas = columnas;
		this.tipo = tipo;
		this.datos = datos;
	}
	
	public int getFilas() {
		return filas;
	}
	public void setFilas(int filas) {
		this.filas = filas;
	}
	public int getColumnas() {
		return columnas;
	}
	public void setColumnas(int columnas) {
		this.columnas = columnas;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public byte[] getDatos() {
		return datos;
	}
	public void setDatos(byte[] datos) {
		this.datos = datos;
	}

	@Override
	public String toString() {
		return "MatrizVO [filas=" + filas + ", columnas=" + columnas + ", tipo=" + tipo + ", datos="
				+ Arrays.toString(datos) + "]";
	}
		
}
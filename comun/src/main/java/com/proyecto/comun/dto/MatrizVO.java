package com.proyecto.comun.dto;

import java.util.Arrays;
import java.util.List;

public class MatrizVO {

	private int filas;
	private int columnas;
	private int tipo;
	private byte [] datos;
	private List<Coordenada> listaCoordenadas;
	
	public MatrizVO() {}
	
	public MatrizVO(int filas, int columnas, int tipo, byte[] datos, List<Coordenada> listaCoordenadas) {
		super();
		this.filas = filas;
		this.columnas = columnas;
		this.tipo = tipo;
		this.datos = datos;
		this.listaCoordenadas = listaCoordenadas;
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

	public List<Coordenada> getListaCoordenadas() {
		return listaCoordenadas;
	}

	public void setListaCoordenadas(List<Coordenada> listaCoordenadas) {
		this.listaCoordenadas = listaCoordenadas;
	}

	@Override
	public String toString() {
		return "MatrizVO [filas=" + filas + ", columnas=" + columnas + ", tipo=" + tipo + ", datos="
				+ Arrays.toString(datos) + ", listaCoordenadas=" + listaCoordenadas + "]";
	}
		
}
package com.proyecto.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class Excel {
	private XSSFWorkbook excel;
	private XSSFSheet hojaCalculo;
	private XSSFRow fila;
	private XSSFCell celda;
	private int numeroFilas = 0;
	
	public Excel() {
		this.excel = new XSSFWorkbook();
	}
	
	public Excel(String nombre) {
		this.excel = new XSSFWorkbook();
		this.hojaCalculo = excel.createSheet(nombre);
	}
	
	public void setHojaCalculo(String nombre) {
		this.hojaCalculo = excel.createSheet(nombre);
	}
	
	public void anadirFila() {
		this.fila = hojaCalculo.createRow(numeroFilas);
		numeroFilas = numeroFilas + 1;
	}
	
	public void anadirFila(int numeroFila) {
		this.fila = hojaCalculo.createRow(numeroFila);
	}
	
	public void anadirCeldas(List<String> listaCeldas) {
		int numeroCeldas = 0;
		for(String valorCelda: listaCeldas) {
			this.celda = fila.createCell(numeroCeldas);
			this.celda.setCellValue(valorCelda);
			numeroCeldas = numeroCeldas + 1;
		}
		numeroCeldas = 0;
	}
	
	public void anadirCeldas(int posicionColumna, List<String> listaCeldas) {
		int numeroCeldas = 0;
		for(String valorCelda: listaCeldas) {
			this.celda = fila.createCell(posicionColumna);
			this.celda.setCellValue(valorCelda);
			numeroCeldas = numeroCeldas + 1;
		}
		numeroCeldas = 0;		
	}
	
	public void escribirExcel(Path path) throws IOException {
		Files.createFile(path);
		OutputStream os = Files.newOutputStream(path, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});
		excel.write(os);
		excel.close();
		os.close();
	}
}

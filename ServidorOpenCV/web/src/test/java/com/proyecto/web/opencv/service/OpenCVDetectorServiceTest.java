package com.proyecto.web.opencv.service;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.proyecto.comun.dto.MatrizVO;
import com.proyecto.comun.opencv.OpenCVComunUtils;
import com.proyecto.web.configuracion.ContextoConfiguracionTest;
import com.proyecto.web.utils.Constantes;

import testUtils.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ContextoConfiguracionTest.class)
public class OpenCVDetectorServiceTest {

	@Autowired
	private OpenCVDetectorService openCVDetectorService;
	
	@Autowired
	private ProcesadorOpenCVService procesadorOpenCVService;
	
	private static final String NOMBRE_IMAGEN = "evaDavid.jpg";
	
	@Test
	public void reconocerImagen() throws IOException {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		String rutaImagen = Utils.getRutaRecurso(Constantes.RUTA_IMAGES, NOMBRE_IMAGEN);
		Mat imagen = Highgui.imread(rutaImagen, Highgui.CV_LOAD_IMAGE_COLOR);		
		
		// Imagen cargada
//		Highgui.imwrite("C:\\Users\\David\\AppData\\Local\\Temp\\imagenReconocida.jpg",	 imagen);
		
		procesadorOpenCVService.getImagenes();
		
		// Imagen reconocida
		MatrizVO matrizVO = new MatrizVO(imagen.rows(), imagen.cols(), imagen.type(), OpenCVComunUtils.getBytesMatriz(imagen), null);
		MatrizVO imagenReconocida = openCVDetectorService.reconocer(matrizVO);
		
		Highgui.imwrite("C:\\Users\\David\\AppData\\Local\\Temp\\imagenReconocida.jpg", OpenCVComunUtils.matrizVOToMat(imagenReconocida));
		System.out.println("Imagen guardada en C:\\Users\\David\\AppData\\Local\\Temp\\imagenReconocida.jpg");
	}
}
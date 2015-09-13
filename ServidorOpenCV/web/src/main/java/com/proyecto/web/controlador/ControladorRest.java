package com.proyecto.web.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControladorRest {
	
//	@Autowired
//	private ProcesadorOpenCV procesadorOpenCV;
	
	@RequestMapping(value = "/estadoServidor",  method = RequestMethod.GET)
	public String getEstadoServidor() {
		//TODO: Comprobar bd OK e imagenes cargadas
		return HttpStatus.OK.toString();
	}
	
//	@RequestMapping(value = "/reconocerImagen",  method = RequestMethod.POST)
//	public List<MatrizVO> postFrame(@RequestBody MatrizVO [] frameArray) throws IOException {
//		System.out.println("dentro " + frameArray.length);		
//		List<MatrizVO> resultados = procesadorOpenCV.reconocerImagen(frameArray[0]);
//		
//		return resultados;
//	}	
	
}
package com.proyecto.web.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.comun.dto.MatrizVO;
import com.proyecto.modelo.servicio.ImagenServicio;
import com.proyecto.web.opencv.service.OpenCVDetectorService;

@RestController
public class ControladorRest {
	
	@Autowired
	private ImagenServicio imagenServicio;
	
	@Autowired
	private OpenCVDetectorService openCVDetectorService;
	
	// TODO: QUE DEVUELVA EL ENUMERADO
	@RequestMapping(value = "/estadoServidor",  method = RequestMethod.GET)
	public String getEstadoServidor() {
		if(imagenServicio.comprobarCache())
			return HttpStatus.OK.toString();
		else
			return HttpStatus.INTERNAL_SERVER_ERROR.toString();
	}
	
	@RequestMapping(value = "/reconocerImagen",  method = RequestMethod.POST)
	public MatrizVO postFrame(@RequestBody MatrizVO frame) {
		return openCVDetectorService.reconocer(frame);
	}	
	
}
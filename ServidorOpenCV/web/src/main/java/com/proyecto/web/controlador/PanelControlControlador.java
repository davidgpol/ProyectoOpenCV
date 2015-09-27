package com.proyecto.web.controlador;

import java.util.List;
import java.util.Map;

import org.opencv.highgui.VideoCapture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.proyecto.modelo.dto.Formulario;
import com.proyecto.modelo.dto.ImagenDto;
import com.proyecto.modelo.servicio.ImagenServicio;
import com.proyecto.web.utils.Constantes;
import com.proyecto.web.utils.Constantes.Operacion;
import com.proyecto.web.validacion.ValidarFormularioImagen;

@Controller
public class PanelControlControlador {
	
	@Autowired
	private ImagenServicio imagenServicio;
	
	@Autowired
	private ValidarFormularioImagen validarFormularioImagen;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Map <String, Object> modelo) {
		Formulario formulario = new Formulario();
		modelo.put("formulario", formulario);
		return "home";
	}
	
	@RequestMapping(value = "/consultarImagenes", method = RequestMethod.POST)
	public String consultarImagenes(@Validated @ModelAttribute("formulario") Formulario formulario, 
									BindingResult bindingResult, Map <String, Object> modelo) {
		
		validarFormularioImagen.validarFormulario(formulario, bindingResult, Operacion.CONSULTAR_IMAGEN);
		
		if(bindingResult.hasErrors()) {
			return "home";
		}
		
		List<ImagenDto> listaImagenesDto = imagenServicio.consultarImagenes(formulario);
					
		if(null == listaImagenesDto)
			modelo.put("numeroFilas", Constantes.CERO_INT);
		else {
			modelo.put("numeroFilas", listaImagenesDto.size());
			modelo.put("listaImagenes", listaImagenesDto);
		}
		
		return "home";
	}
	
	@RequestMapping(value = "/anadirImagenes", method = RequestMethod.POST)
	public String anadirImagenes(@Validated @ModelAttribute("formulario") Formulario formulario, 
									BindingResult bindingResult, Map <String, Object> modelo) {
		
		validarFormularioImagen.validarFormulario(formulario, bindingResult, Operacion.ANADIR_IMAGEN);
		
		if(bindingResult.hasErrors()) {			
			return "home";
		}		
		
		int numeroFilas = imagenServicio.anadirImagenes(formulario);
		
		modelo.put("numeroFilas", numeroFilas);
		
		if(0 == numeroFilas)
			return "error";
		
		return "home";
	}
	
	@RequestMapping(value = "/eliminarImagenes", method = RequestMethod.POST)
	public String eliminarImagenes(@Validated @ModelAttribute("formulario") Formulario formulario, 
									BindingResult bindingResult, Map <String, Object> modelo) {
		
		validarFormularioImagen.validarFormulario(formulario, bindingResult, Operacion.ELIMINAR_IMAGEN);
		
		if(bindingResult.hasErrors()) {
			return "home";
		}		
		
		int numeroFilas = imagenServicio.eliminarImagenes(formulario);
		
		modelo.put("numeroFilas", numeroFilas);
		
		if(0 == numeroFilas)
			return "error";
		
		return "home";
	}
	
}
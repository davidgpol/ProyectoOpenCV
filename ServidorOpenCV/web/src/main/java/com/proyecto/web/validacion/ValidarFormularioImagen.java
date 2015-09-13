package com.proyecto.web.validacion;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.proyecto.comun.constantes.ConstantesComun;
import com.proyecto.modelo.dto.Formulario;
import com.proyecto.web.utils.Constantes.Operacion;

@Component
public class ValidarFormularioImagen implements Validator {

	private Operacion operacion;
	
	@Override
	public boolean supports(Class<?> validarFormularioImagen) {
		return ValidarFormularioImagen.class.equals(validarFormularioImagen);
	}

	private void validarNombreImagen(Formulario datosFormulario, Errors errores) {
		String nombreImagen = null;
		
		if((null == datosFormulario.getNombreImagen()) || ("".equals(datosFormulario.getNombreImagen())))
			errores.rejectValue("nombreImagen", "validacion.formulario.imagen.nombreImagen");
		else {
			nombreImagen = datosFormulario.getNombreImagen();
			
			if(nombreImagen.length() <= 2)
				errores.rejectValue("imagenes", "validacion.formulario.imagen.nombreImagen.longitud");
			else {
				try {
					Integer.parseInt(nombreImagen.substring(nombreImagen.length() - ConstantesComun.DIGITOS_NOMBRE_IMAGEN));
				}catch(NumberFormatException nfe) {errores.rejectValue("nombreImagen", "validacion.formulario.imagen.nombreImagen.codigo");}
			}
		}		
	}
	
	public void validarConsultarImagen(Formulario datosFormulario, Errors errores) {
		
		
		if((null == datosFormulario.getIdImagen()) && (null == datosFormulario.getIdGrupo())
				&& ((null == datosFormulario.getNombreImagen()) || ("".equals(datosFormulario.getNombreImagen()))))
			errores.rejectValue("imagenes", "validacion.formulario.imagen.camposVacios");
		
		if((null != datosFormulario.getNombreImagen()) && (!"".equals(datosFormulario.getNombreImagen())))
				this.validarNombreImagen(datosFormulario, errores);
	}	
	
	public void validarAnadirImagen(Formulario datosFormulario, Errors errores) {
			
		ValidationUtils.rejectIfEmptyOrWhitespace(errores, "idGrupo", "validacion.formulario.imagen.idGrupo");

		this.validarNombreImagen(datosFormulario, errores);
		
		if("".equals(datosFormulario.getImagenes().get(0).getOriginalFilename()))
			errores.rejectValue("imagenes", "validacion.formulario.imagen.nombreImagen.imagenes");

	}

	public void validarEliminarImagen(Formulario datosFormulario, Errors errores) {
		
		
		if((null == datosFormulario.getIdImagen()) && (null == datosFormulario.getIdGrupo())
				&& ((null == datosFormulario.getNombreImagen()) || ("".equals(datosFormulario.getNombreImagen()))))
			errores.rejectValue("imagenes", "validacion.formulario.imagen.camposVacios");
	}	
	
	public void validarFormulario(Object formulario, Errors errores, Operacion operacion) {
		this.operacion = operacion;
		this.validate(formulario, errores);
	}
	
	@Override
	public void validate(Object formulario, Errors errores) {
		Formulario datosFormulario = (Formulario) formulario;
		switch(operacion) {
			case ANADIR_IMAGEN:
									validarAnadirImagen(datosFormulario, errores);
									break;
			case MODIFICAR_IMAGEN:
									break;
			case ELIMINAR_IMAGEN:
									validarEliminarImagen(datosFormulario, errores);
									break;
			case CONSULTAR_IMAGEN:
									validarConsultarImagen(datosFormulario, errores);
									break;
			default:
									break;
			
		}			
	}

}

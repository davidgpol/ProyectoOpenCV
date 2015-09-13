package com.proyecto.modelo.servicioImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.comun.constantes.ConstantesComun;
import com.proyecto.modelo.dao.ImagenDao;
import com.proyecto.modelo.dto.Formulario;
import com.proyecto.modelo.dto.ImagenDto;
import com.proyecto.modelo.entidad.Imagen;
import com.proyecto.modelo.servicio.ImagenServicio;
import com.proyecto.modelo.utils.ImagenUtils;

@Service
public class ImagenServicioImpl implements ImagenServicio {

	@Autowired
	ImagenDao imagenDaoGenerico;

	public List <ImagenDto> consultarImagenes(Formulario formulario) {
		List <Imagen> listaImagenes;
		List <ImagenDto> listaImagenesDto;
		if(null != formulario.getIdImagen())			
			listaImagenes = Arrays.asList(imagenDaoGenerico.getById(formulario.getIdImagen()));
		else if(null != formulario.getIdGrupo())
			listaImagenes = imagenDaoGenerico.getByGrupo(formulario.getIdGrupo());
		else listaImagenes = imagenDaoGenerico.getByNombre(formulario.getNombreImagen());
		
		ImagenDto imagenDto = null;
		listaImagenesDto = new ArrayList<ImagenDto>();
		
		if(listaImagenes.get(0) == null)
			return null;
		
		for(Imagen imagen: listaImagenes) {
			imagenDto = new ImagenDto();
			imagenDto.setIdImagen(imagen.getIdImagen());
			imagenDto.setGrupoImagen(imagen.getGrupoImagen());
			imagenDto.setNombreImagen(imagen.getNombreImagen());
			imagenDto.setBase64Imagen(ImagenUtils.getBase64Imagen(imagen.getImagen()));
			listaImagenesDto.add(imagenDto);
		}
		
		return listaImagenesDto;
	}
	
	public int eliminarImagenes(Formulario formulario) {
		if(null != formulario.getIdImagen())
			imagenDaoGenerico.delete(formulario.getIdImagen());
		else if(null != formulario.getIdGrupo())
			return imagenDaoGenerico.deleteByGrupo(formulario.getIdGrupo());
		else return imagenDaoGenerico.deleteByNombre(formulario.getNombreImagen());
		
		return 0; //TODO: QUITAR ESTE RETURN AL ARREGLAR EL DELETE BY ID
	}
	
	public int modificarImagenes(Formulario formulario) {
		return 0;
	}
	
	public int anadirImagenes(Formulario formulario) {
		int contador		= 0;		
		Imagen imagen		= null;
		String nombreImagen	= null;
		
		for(MultipartFile multipartFile: formulario.getImagenes()) {
			
			imagen = new Imagen();
			try {
				imagen.setImagen(multipartFile.getBytes());
			} catch (IOException ioe) {System.out.println(ioe.getLocalizedMessage());}		
								
			imagen.setGrupoImagen(formulario.getIdGrupo());

			if(contador > ConstantesComun.CERO_INT) {
				nombreImagen = ImagenUtils.procesarNombreImagen(formulario.getNombreImagen());
				imagen.setNombreImagen(nombreImagen);
			} else imagen.setNombreImagen(formulario.getNombreImagen());
											
			imagenDaoGenerico.create(imagen);											
			
			contador = contador + 1;
		}
		
		return contador;
	}
}
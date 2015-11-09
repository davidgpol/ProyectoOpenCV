package com.proyecto.modelo.servicioImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
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
	ImagenDao imagenDao;

	public List <ImagenDto> consultarImagenes(Formulario formulario) {
		List <Imagen> listaImagenes;
		List <ImagenDto> listaImagenesDto;
		if(null != formulario.getIdImagen())			
			listaImagenes = Arrays.asList(imagenDao.getById(formulario.getIdImagen()));
		else if(null != formulario.getIdGrupo())
			listaImagenes = imagenDao.getByGrupo(formulario.getIdGrupo());
		else listaImagenes = imagenDao.getByNombre(formulario.getNombreImagen());
		
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
			imagenDao.delete(formulario.getIdImagen());
		else if(null != formulario.getIdGrupo())
			return imagenDao.deleteByGrupo(formulario.getIdGrupo());
		else return imagenDao.deleteByNombre(formulario.getNombreImagen());
		
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
											
			imagenDao.create(imagen);											
			
			contador = contador + 1;
		}
		
		return contador;
	}
	
	public List<Imagen> cargarImagenes() {
		return imagenDao.getAllOrder(ImagenUtils.ORDER_BY_GRUPO_IMAGEN);
	}
	
	public Imagen getByGrupo(long grupo) {
		List <Imagen> lista = imagenDao.getByGrupo(grupo);
		return ((lista != null) && (!lista.isEmpty())) ? lista.get(0) : null;
	}
	
	public boolean comprobarCache() {
		Imagen imagen = null;
		SessionFactory sessionFactory = imagenDao.getSessionFactory();
		Statistics statistics = sessionFactory.getStatistics();
		
		imagen = imagenDao.getPimeraEntrada();
		imagenDao.eliminarEntidadCache(imagen);
		imagen = imagenDao.getPimeraEntrada();
		
		return (statistics.getSecondLevelCacheHitCount() > 0) ? true : false;
	}
}
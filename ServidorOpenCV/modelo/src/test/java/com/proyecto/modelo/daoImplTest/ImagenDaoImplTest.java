package com.proyecto.modelo.daoImplTest;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.modelo.configuracion.ConfiguracionHibernate;
import com.proyecto.modelo.dao.ImagenDao;
import com.proyecto.modelo.entidad.Imagen;
import com.proyecto.modelo.mock.ImagenMock;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(classes = ConfiguracionHibernate.class)
public class ImagenDaoImplTest {
	
	@Autowired
	private ImagenDao imagenDao;
	private List<Imagen> lista;
	
	@Test
	public void getAll() {
		System.out.println("getAllImages:");
		this.lista = imagenDao.getAll();
		printImages(lista);
	}
	
	@Test
	public void getById() {
		System.out.println("getById:");
		Imagen imagen = imagenDao.getById(15L);
		printImages(Arrays.asList(imagen));
	}
	
	@Test
	public void getByGrupo() {
		System.out.println("getByGrupo:");
		this.lista = imagenDao.getByGrupo(0L);
		printImages(lista);
	}
	
	@Test
	public void getByNombre() {
		System.out.println("getByNombre:");
		this.lista = imagenDao.getByNombre("david");
		printImages(lista);
	}
	
	@Test
	// Quitar para que no haga rollback
	@Transactional
	public void create() {
		System.out.println("create:");
		imagenDao.create(ImagenMock.getImagen());
	}
	
//	@Test
//	public void update() {
//		System.out.println("update:");
//		Imagen imagen = ImagenMock.getImagen();
//		imagen.setNombreImagen("Imagen mock modificada");
//		imagenDao.update(imagen);
//	}	
	
	@Test
	public void delete() {
		System.out.println("delete:");
		Imagen imagen = ImagenMock.getImagen();
		imagenDao.delete(imagen.getIdImagen());
	}
	
//	@Test
//	public void deleteByGrupo() {
//		System.out.println("deleteByGrupo:");
//		Imagen imagen = ImagenMock.getImagen();
//		imagenDao.deleteByGrupo(imagen.getGrupoImagen());
//	}	
//	
//	@Test
//	public void deleteByNombre() {
//		System.out.println("deleteByNombre:");
//		Imagen imagen = ImagenMock.getImagen();
//		imagenDao.deleteByNombre(imagen.getNombreImagen());
//	}	
	
	private void printImages(List<Imagen> lista) {
		for(int i = 0; i < lista.size(); i++)
			System.out.println(lista.get(i));
	}
}
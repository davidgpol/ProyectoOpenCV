package com.proyecto.modelo.daoImplTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.proyecto.modelo.configuracion.ConfiguracionHibernate;
import com.proyecto.modelo.dao.ImagenDao;
import com.proyecto.modelo.entidad.Imagen;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfiguracionHibernate.class)
public class ImagenDaoImplCacheTest {

	@Autowired
	private ImagenDao imagenDao;
	private Imagen imagen;
	private List<Imagen> lista;
	
	private static int REPETICIONES = 2;
	
	@Test
	public void getAllCacheTest() {
		System.out.println("getAllCacheTest");
		for(int i = 0; i < REPETICIONES; i++) {
			lista = imagenDao.getAll();
			printImages(lista);
		}		
	}
	
	@Test
	public void getByIdCacheTest() {
		System.out.println("getByIdCacheTest");
		for(int i = 0; i < REPETICIONES; i++) {
			this.imagen = imagenDao.getById(15L);
			System.out.println(imagen);
		}
	}

	@Test
	public void getByGrupoCacheTest() {
		System.out.println("getByGrupoCacheTest");
		for(int i = 0; i < REPETICIONES; i++) {
			this.lista = imagenDao.getByGrupo(0L);
			printImages(lista);
		}
	}	
	
	@Test
	public void getByNombreCacheTest() {
		System.out.println("getByNombreCacheTest");
		for(int i = 0; i < REPETICIONES; i++) {
			this.lista = imagenDao.getByNombre("david");
			printImages(lista);
		}
	}	
	
	private void printImages(List<Imagen> lista) {
		for(int i = 0; i < lista.size(); i++)
			System.out.println(lista.get(i));
	}	
}
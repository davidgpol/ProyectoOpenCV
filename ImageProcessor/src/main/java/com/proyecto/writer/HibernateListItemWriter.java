package com.proyecto.writer;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.batch.item.ItemWriter;

import com.proyecto.modelo.entidad.Imagen;

public class HibernateListItemWriter implements ItemWriter<Imagen> {

	private static Logger log = LogManager.getLogger(HibernateListItemWriter.class);
	
	private SessionFactory sessionFactory;
	
	public HibernateListItemWriter(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void write(List<? extends Imagen> listaImagenesRaw) throws Exception {

		if(listaImagenesRaw.isEmpty())
			throw new Exception("Lista de imagenes vacia en " + this.getClass());
		
		for(int i = 0; i < listaImagenesRaw.size(); i++) {
			List<Imagen> listaImagenes = (List<Imagen>) listaImagenesRaw.get(i);
	
			Session currentSession = sessionFactory.getCurrentSession();
			
			for (Imagen imagen : listaImagenes) {
				currentSession.save(imagen);
				log.debug("Insertada la imagen " + imagen.getNombreImagen());
			}
			
			sessionFactory.getCurrentSession().flush();
			sessionFactory.getCurrentSession().clear();
		}
	}

}

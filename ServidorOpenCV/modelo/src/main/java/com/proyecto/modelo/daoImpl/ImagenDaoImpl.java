package com.proyecto.modelo.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.modelo.dao.ImagenDao;
import com.proyecto.modelo.entidad.Imagen;

@Transactional
public class ImagenDaoImpl implements ImagenDao {

	private SessionFactory sessionFactory;

	private Session session;
	
	public ImagenDaoImpl() {}

	public ImagenDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session setSession() {
		return (null != this.sessionFactory) ? sessionFactory.getCurrentSession() : null; 
	}
	
	public List<Imagen> getAll() {
		this.session = setSession();
		Query query = session.createQuery("from Imagen").setCacheable(true);
		List <Imagen> listaImagenes = (List <Imagen>) query.list();
		return listaImagenes;
	}

	public Imagen getById(Long id) {
		this.session = setSession();
		return (Imagen) session.get(Imagen.class, id);
	}

	public List<Imagen> getByGrupo(Long idGrupo) {
		this.session = setSession();
		Criteria criteria = session.createCriteria(Imagen.class).setCacheable(true);
		criteria.add(Restrictions.eq("grupoImagen", idGrupo));
		return (List<Imagen>) criteria.list();
	}	
	
	@Override
	public List<Imagen> getByNombre(String nombre) {
		this.session = setSession();
		Criteria criteria = session.createCriteria(Imagen.class).setCacheable(true);
		criteria.add(Restrictions.ilike("nombreImagen", nombre, MatchMode.ANYWHERE));
		return (List<Imagen>) criteria.list();
	}

	// TODO: CAMBIAR ESTO POR MANEJAR LA EXCEPCION!!! SI TIENE EL MISMO ID GRUPO Y EL MISMO NOMBRE
	public int create(Imagen imagen) {
		this.session = setSession();		
		return new Long((long) session.save(imagen)).intValue();
	}

	// TODO: CAMBIAR ESTO POR MANEJAR LA EXCEPCION!!! SI TIENE EL MISMO ID GRUPO Y EL MISMO NOMBRE
	public void delete(Long idImagen) {
		this.session = setSession();
		Imagen imagen = new Imagen();
		imagen.setIdImagen(idImagen);		
		session.delete(imagen);	
	}

	// TODO: CAMBIAR ESTO POR MANEJAR LA EXCEPCION!!! SI TIENE EL MISMO ID GRUPO Y EL MISMO NOMBRE
	public int update(Imagen imagen) {
		this.session = setSession();
		session.update(imagen);
		return 0;
	}

	@Override
	public int deleteByGrupo(Long idGrupo) {
		this.session = setSession();
		String hql = "delete from Imagen where GrupoImagen= :grupoImagen";
		return session.createQuery(hql).setLong("grupoImagen", idGrupo).executeUpdate();		
	}

	@Override
	public int deleteByNombre(String nombre) {
		this.session = setSession();
		String hql = "delete from Imagen where NombreImagen like :nombreImagen";
		return session.createQuery(hql).setString("nombreImagen", '%'+ nombre + '%').executeUpdate();	
		
	}
	
}
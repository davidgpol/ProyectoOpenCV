package com.proyecto.modelo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.proyecto.modelo.entidad.Imagen;

public interface ImagenDao extends DaoGenerico<Imagen, Long> {

	public List<Imagen> getByGrupo(Long idGrupo);
	public List<Imagen> getByNombre(String nombre);
	public int deleteByGrupo(Long idGrupo);
	public int deleteByNombre(String nombre);
	public Imagen getPimeraEntrada();
	public SessionFactory getSessionFactory();
	void eliminarEntidadCache(Imagen imagen);
	public Session getSession();
	
}
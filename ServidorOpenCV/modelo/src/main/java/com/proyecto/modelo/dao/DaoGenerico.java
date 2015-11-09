package com.proyecto.modelo.dao;

import java.io.Serializable;
import java.util.List;

import com.proyecto.modelo.entidad.Imagen;

public interface DaoGenerico <T, Id extends Serializable>{

	public List <T> getAll();
	public T getById(Id id);
	public int create(T entidad);
	public void delete(Long entidad);
	public int update(T entidad);
	public List<Imagen> getAllOrder(String order);
	
}
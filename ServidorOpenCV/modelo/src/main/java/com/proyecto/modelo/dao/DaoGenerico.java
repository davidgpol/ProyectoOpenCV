package com.proyecto.modelo.dao;

import java.io.Serializable;
import java.util.List;

public interface DaoGenerico <T, Id extends Serializable>{

	public List <T> getAll();
	public T getById(Id id);
	public int create(T entidad);
	public void delete(Long entidad);
	public int update(T entidad);
	
}
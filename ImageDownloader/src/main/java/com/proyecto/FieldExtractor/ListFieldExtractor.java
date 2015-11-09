package com.proyecto.FieldExtractor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.file.transform.FieldExtractor;

public class ListFieldExtractor<T> implements FieldExtractor<T> {

	@Override
	public Object[] extract(T item) {
		List<Object> values = new ArrayList<Object>();
		
		String itemString = (String) item;
		String [] listaItem = itemString.split(",");

		for(String elemento: listaItem) {
			values.add(elemento);
		}
		return values.toArray();
	}

}
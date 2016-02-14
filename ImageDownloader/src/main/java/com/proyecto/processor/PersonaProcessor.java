package com.proyecto.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.proyecto.constantes.Constantes;
import com.proyecto.modelo.Persona;
import com.proyecto.modelo.PersonaUrl;

@Component
public class PersonaProcessor implements ItemProcessor<Persona, PersonaUrl> {
	
	private static Logger log = LogManager.getLogger(PersonaProcessor.class);
	
	@Override
	public PersonaUrl process(Persona persona) throws Exception {
		
		String nombrePersona = persona.getNombre();
		int numeroImagenes = persona.getNumeroImagenes();
		int numeroGrupos = numeroImagenes / Constantes.NUMERO_IMAGENES_INT;
		int resto = Math.floorMod(numeroImagenes, Constantes.NUMERO_IMAGENES_INT);
		
		if((numeroGrupos == 0) && (resto == 0))
			throw new Exception("El numero de imagenes a descargar para " + nombrePersona + " debe ser mayor que cero");
		
		List<String> urls = new ArrayList<String>();
		Integer incremento = 1;
		
		for(int i = 0; i < numeroGrupos; i++) {
			urls.addAll(procesar(nombrePersona, Constantes.NUMERO_IMAGENES_BASE + Constantes.NUMERO_IMAGENES_INT,
										Constantes.START + incremento)); 
			incremento = incremento + 8;
		}
		
		if(resto > 0) {			
			urls.addAll(procesar(nombrePersona, Constantes.NUMERO_IMAGENES_BASE + String.valueOf(resto), Constantes.START + incremento));
		}
		
		// Filtra las URLS repetidas
		urls = urls.parallelStream().distinct().collect(Collectors.toList());
		return new PersonaUrl(nombrePersona, urls);
	}

	private List<String> procesar(String nombrePersona, String numeroImagenes, String siguientes) {
        URL url = obtenerConexion(nombrePersona, numeroImagenes, siguientes); 
		return getDatos(url);
	}	
	
	private URL obtenerConexion(String nombrePersona, String numeroImagenes, String siguientes) {
        URL url = null;
		try {
			url = new URL(Constantes.BASE_URL + URLEncoder.encode(nombrePersona, Constantes.FORMATO_CODIFICACION_CARACTERES)
							+ numeroImagenes + siguientes);			
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	private List<String> getDatos(URL url) {
		String linea = null;
		StringBuilder datos = new StringBuilder();
		URLConnection connection = null;
		try {
			connection = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while((linea = reader.readLine()) != null) {
				datos.append(linea);
			}
			return getUrls(datos.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private List<String> getUrls(String string) {
		JSONObject json = new JSONObject(string);
		JSONArray results = json.getJSONArray(Constantes.RESULTS);
		String url = null;
		List<String> listaUrls = new ArrayList<String>();
		
		for (int i = 0; i < results.length(); i++) {
			url = results.getJSONObject(i).getString(Constantes.URL);
			listaUrls.add(url);
		}

		return listaUrls;
	}	
}
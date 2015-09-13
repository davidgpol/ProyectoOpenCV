package main.java.rest;

import java.util.List;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.proyecto.comun.constantes.ConstantesComun;
import com.proyecto.comun.dto.MatrizVO;

public class RestCliente {
	
	public static String getStadoServidor() {
		RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(ConstantesComun.SERVIDOR_URI + ConstantesComun.ESTADO_SERVIDOR, String.class);
    }
	
	public static List<MatrizVO> postCaras(List<MatrizVO> listaCaras) {
		List<MatrizVO> resultados = null;
		
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        try {
        	resultados = restTemplate.postForObject(ConstantesComun.SERVIDOR_URI + ConstantesComun.RECONOCER_IMAGEN, 
        												listaCaras.toArray(new MatrizVO [0]), List.class);
        	
        	System.out.println(resultados.toArray().toString());
        	return resultados;
        }
        catch (HttpClientErrorException e) {
            System.out.println("error:  " + e.getResponseBodyAsString());
        }
        catch(Exception e) {
            System.out.println("Error generico");
        }        
        return resultados;
    }
 
}
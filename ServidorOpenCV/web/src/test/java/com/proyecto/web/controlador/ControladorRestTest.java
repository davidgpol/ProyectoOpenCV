package com.proyecto.web.controlador;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.proyecto.comun.constantes.ConstantesComun;
import com.proyecto.web.configuracion.ContextoConfiguracion;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ContextoConfiguracion.class)
public class ControladorRestTest {
	
	@Autowired
	ControladorRest controladorRest;
	
	@Test
	public void getEstadoServidor() {
		System.out.println("getEstadoServidor");
		
		RestTemplate restTemplate = new RestTemplate();

//		String c = controladorRest.getEstadoServidor();
//		System.out.println("Estado: " + c);
		String estado = restTemplate.getForObject(ConstantesComun.SERVIDOR_URI + ConstantesComun.ESTADO_SERVIDOR, String.class);
		System.out.println(estado);
	}
	
}
package com.proyecto.web.controlador;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.proyecto.web.configuracion.ContextoConfiguracionTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ContextoConfiguracionTest.class)
public class ControladorRestTest {
	
	@Autowired
	ControladorRest controladorRest;
	
	@Test
	public void getEstadoServidor() {
		System.out.println("getEstadoServidor");
		
//		RestTemplate restTemplate = new RestTemplate();

		String estado = controladorRest.getEstadoServidor();
		System.out.println("Estado: " + estado);
//		String estado = restTemplate.getForObject(ConstantesComun.SERVIDOR_URI + ConstantesComun.ESTADO_SERVIDOR, String.class);
		System.out.println(estado);
	}
	
}
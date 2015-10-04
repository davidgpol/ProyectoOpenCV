package com.proyecto.web.controlador;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.proyecto.web.configuracion.ContextoConfiguracionTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ContextoConfiguracionTest.class)
public class PanelControlControladorTest {

	@Autowired
	private PanelControlControlador panelControlControlador;
	
	@Test
	public void cargarCacheTest() {
		for(int i = 0; i < 5; i++)
			panelControlControlador.cargarCache(null);
	}
	
}
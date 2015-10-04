package com.proyecto.web.configuracion;

import static org.bytedeco.javacpp.opencv_contrib.createEigenFaceRecognizer;

import org.bytedeco.javacpp.opencv_contrib.FaceRecognizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.proyecto.modelo.configuracion.ConfiguracionHibernate;

@Configuration
@Import(ConfiguracionHibernate.class)
@ComponentScan(basePackages = { "com.proyecto.web.opencv.service", "com.proyecto.web.controlador", "com.proyecto.web.validacion"})
public class ContextoConfiguracionTest extends WebMvcConfigurerAdapter {	

	@Bean(name = "faceRecognizer")
	public FaceRecognizer getFaceRecognizer() {
		FaceRecognizer faceRecognizer = createEigenFaceRecognizer();
		return faceRecognizer;
	}
	
}
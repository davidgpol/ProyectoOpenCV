package com.proyecto.web.configuracion;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.proyecto.modelo.configuracion.ConfiguracionHibernate;

@Configuration
@Import(ConfiguracionHibernate.class)
@ComponentScan(basePackages = { "com.proyecto.web.opencv.service"})
public class ContextoConfiguracionTest extends WebMvcConfigurerAdapter {	

}
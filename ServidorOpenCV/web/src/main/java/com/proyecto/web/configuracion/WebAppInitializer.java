package com.proyecto.web.configuracion;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext) throws ServletException {
		
		AnnotationConfigWebApplicationContext acwac = new AnnotationConfigWebApplicationContext();
		
		acwac.register(ContextoConfiguracion.class);  
		acwac.setServletContext(servletContext);
		
		Dynamic dynamic = servletContext.addServlet("dispatcher", new DispatcherServlet(acwac));  
		dynamic.addMapping("/");  
		dynamic.setLoadOnStartup(1);
		
		dynamic.setMultipartConfig(new MultipartConfigElement(System.getProperty("java.io.tmpdir"), 1024*1024*5, 1024*1024*5*5, 1024*1024));		
	}
	
}
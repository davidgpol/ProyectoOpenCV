package com.proyecto.web.configuracion;

import static org.bytedeco.javacpp.opencv_contrib.createEigenFaceRecognizer;

import org.bytedeco.javacpp.opencv_contrib.FaceRecognizer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.proyecto.modelo.configuracion.ConfiguracionHibernate;

@Configuration
@EnableWebMvc
@Import(ConfiguracionHibernate.class)
@ComponentScan(basePackages = {"com.proyecto.modelo.servicioImpl", "com.proyecto.web.controlador", 
								"com.proyecto.web.validacion", "com.proyecto.web.opencv.service",
								"com.proyecto.web.opencv"})
public class ContextoConfiguracion extends WebMvcConfigurerAdapter {	
	
	@Bean
	public UrlBasedViewResolver setupViewResolver() {  
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();  
		resolver.setPrefix("/WEB-INF/jsp/");  
		resolver.setSuffix(".jsp");  
		resolver.setViewClass(JstlView.class);  
		return resolver;  
    }

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
	      .addResourceHandler("/resources/**")
	      .addResourceLocations("/resources/", "classpath:/css/", "classpath:/js/")
	      .setCachePeriod(3600)
	      .resourceChain(true)
	      .addResolver(new PathResourceResolver());   
    }		

	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}	
	
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource bean = new ResourceBundleMessageSource();
	    bean.setBasename("messages");
	    bean.setDefaultEncoding("UTF-8");
	    return bean;
	}

	@Bean(name = "faceRecognizer")
	public FaceRecognizer getFaceRecognizer() {
		FaceRecognizer faceRecognizer = createEigenFaceRecognizer();
		return faceRecognizer;
	}	
	
	/*
	@Bean(name = "usuario")	
	public Usuario getUsuario() {
		Usuario usuario = new Usuario();
		usuario.setNombre("Nombre");
		usuario.setApellido1("Apellido1");
		usuario.setApellido2("Apellido2");
		usuario.setNick("Nick");
		usuario.setId(1);
		usuario.setContrasena("contrase�a");
		
		return usuario;
	}
	*/

}
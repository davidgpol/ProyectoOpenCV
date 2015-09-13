package com.proyecto.modelo.configuracion;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyecto.modelo.dao.ImagenDao;
import com.proyecto.modelo.daoImpl.ImagenDaoImpl;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.proyecto.modelo.servicioImpl"})
public class ConfiguracionHibernate {
	@Bean(name = "dataSource")
	public DataSource getDataSource() {
	    BasicDataSource dataSource = new BasicDataSource();
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    dataSource.setUrl("jdbc:mysql://localhost:3306/tablas_hibernate");
	    dataSource.setUsername("root");
	    dataSource.setPassword("51059999");
	 
	    return dataSource;
	}
	
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {
	 
	    LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
	    
	    hibernateSessionParameters(sessionBuilder);
	 
	    return sessionBuilder.buildSessionFactory();
	}	
	
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		
	    HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
	 
	    return transactionManager;
	}
	
	@Bean(name = "imagenDaoGenerico")
	public ImagenDao getImagenDaoGenericoImpl(SessionFactory sessionFactory) {
	    return new ImagenDaoImpl(sessionFactory);
	}	
	
	private void hibernateSessionParameters(LocalSessionFactoryBuilder sessionBuilder) {
		sessionBuilder.setProperty("hibernate.show_sql", "true");
		sessionBuilder.setProperty("hibernate.format_sql", "true");	
	    sessionBuilder.setProperty("hibernate.hbm2ddl.auto", "validate");	    
	    sessionBuilder.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");	    
	    
	    sessionBuilder.scanPackages("com.proyecto.modelo.entidad");		
	}
}
package com.proyecto.configuracion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.HibernateItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.proyecto.dto.ImagenDto;
import com.proyecto.modelo.configuracion.ConfiguracionHibernate;
import com.proyecto.modelo.entidad.Imagen;
import com.proyecto.writer.HibernateListItemWriter;

@Component
@Configuration
@EnableBatchProcessing
@Import(ConfiguracionHibernate.class)
@ComponentScan(basePackages = {"com.proyecto.listeners", "com.proyecto.processor"})
public class Configuracion {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
	
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;
    
    @Autowired
    private ItemProcessor<ImagenDto, List<ImagenDto>> imagenProcessor;
    
    @Autowired
    private ItemProcessor<List<ImagenDto>, List<Imagen>> openCVImageProcessor;    
    
	// Definicion del reader, processor y writer
	@Bean
	public ItemReader<ImagenDto> reader() {
		FlatFileItemReader<ImagenDto> reader = new FlatFileItemReader<ImagenDto>();
		reader.setResource(new ClassPathResource("datos.csv"));
		reader.setLineMapper(new DefaultLineMapper<ImagenDto>() {{
			setLineTokenizer(new DelimitedLineTokenizer() {{
				setDelimiter(";");
				setNames(new String[] {"nombreCarpeta", "grupoImagen", "nombreImagen"});
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<ImagenDto>() {{
				setTargetType(ImagenDto.class);
			}});
			
		}});
		return reader;
	}
	
	@Bean
	public CompositeItemProcessor compositeProcesor() {
		CompositeItemProcessor compositeItemProcessor =  new CompositeItemProcessor();
		List listaProcessors = new ArrayList();
		listaProcessors.add(imagenProcessor);
		listaProcessors.add(openCVImageProcessor);		
		compositeItemProcessor.setDelegates(listaProcessors);
		return compositeItemProcessor;
	}	
	
	@Bean
	public HibernateListItemWriter writer() {
		HibernateListItemWriter hibernateListItemWriter = new HibernateListItemWriter(sessionFactory);
		return hibernateListItemWriter;
	}
	
//	@Bean
//	public HibernateItemWriter<Imagen> writer() throws IOException {
//		HibernateItemWriter<Imagen> hibernateItemWriter = new HibernateItemWriter<Imagen>();
//		hibernateItemWriter.setSessionFactory(sessionFactory);
//		return hibernateItemWriter;
//	}
	
	// Definicion del Job
	@Bean
	public Job imageProcessorJob(JobExecutionListener jobExecutionListener) throws IOException {
		return jobBuilderFactory.get("imageProcessorJob")
				.incrementer(new RunIdIncrementer())
				.listener(jobExecutionListener)
				.flow(step1( reader(), writer(), compositeProcesor()))
				.end()
				.build();
	}

	@Bean
	public ResourcelessTransactionManager transactionManager() {
	    return new ResourcelessTransactionManager();
	}

	@Bean
	public JobRepository jobRepository(ResourcelessTransactionManager transactionManager) throws Exception {
	    MapJobRepositoryFactoryBean mapJobRepositoryFactoryBean = new MapJobRepositoryFactoryBean(transactionManager);
	    mapJobRepositoryFactoryBean.setTransactionManager(transactionManager);
	    return mapJobRepositoryFactoryBean.getObject();
	}

	@Bean
	public SimpleJobLauncher jobLauncher(JobRepository jobRepository) {
	    SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
	    simpleJobLauncher.setJobRepository(jobRepository);
	    return simpleJobLauncher;
	}
	
	// Definicion de los pasos a ejecutar en el Job
	@Bean
	public Step step1(ItemReader<ImagenDto> reader, HibernateListItemWriter hibernateListItemWriter, CompositeItemProcessor compositeProcessor) {
		return stepBuilderFactory.get("step1")
				.<ImagenDto, Imagen> chunk(10)
				.reader(reader)
				.processor(compositeProcessor)
				.writer(hibernateListItemWriter)
				.build();
	}

}
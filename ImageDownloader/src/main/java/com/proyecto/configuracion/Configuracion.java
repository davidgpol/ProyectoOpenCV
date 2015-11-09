package com.proyecto.configuracion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.proyecto.modelo.Persona;
import com.proyecto.modelo.PersonaUrl;
import com.proyecto.writer.ImagenWriter;
import com.proyecto.writer.UrlWriter;

@Component
@Configuration
@EnableBatchProcessing
@EnableAspectJAutoProxy(proxyTargetClass=true) 
@ComponentScan(basePackages = {"com.proyecto.listeners", "com.proyecto.excel", "com.proyecto.aspecto",
								"com.proyecto.processor", "com.proyecto.writer"})
public class Configuracion {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
	
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    private ItemProcessor<Persona, PersonaUrl> personaProcessor;
    
    @Autowired
    private ImagenWriter imagenWriter;
    
    @Autowired
    private UrlWriter urlWriter;
    
	// Definicion del reader, processor y writer
	@Bean
	public ItemReader<Persona> reader() {
		FlatFileItemReader<Persona> reader = new FlatFileItemReader<Persona>();
		reader.setResource(new ClassPathResource("nombres.csv"));
		reader.setLineMapper(new DefaultLineMapper<Persona>() {{
			setLineTokenizer(new DelimitedLineTokenizer() {{
				setDelimiter(";");
				setNames(new String[] {"nombre", "numeroImagenes"});
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<Persona>() {{
				setTargetType(Persona.class);
			}});
			
		}});
		return reader;
	}
	
	@Bean
	public CompositeItemWriter<PersonaUrl> compositeWriter() throws IOException {
		CompositeItemWriter<PersonaUrl> composite = new CompositeItemWriter<PersonaUrl>();
		List<ItemWriter<? super PersonaUrl>> delegates = new ArrayList<ItemWriter<? super PersonaUrl>>();
		delegates.add(imagenWriter);
		delegates.add(urlWriter);			
		composite.setDelegates(delegates);
		return composite;
	}
	
	// Definicion del Job
	@Bean
	public Job imageDownloaderJob(JobExecutionListener jobExecutionListener) throws IOException {
		return jobBuilderFactory.get("imageDownloaderJob")
				.incrementer(new RunIdIncrementer())
				.listener(jobExecutionListener)
				.flow(step1( reader(), compositeWriter(), personaProcessor))
				.end()
				.build();
	}

	// Definicion de los pasos a ejecutar en el Job
	@Bean
	public Step step1(ItemReader<Persona> reader, ItemWriter<PersonaUrl> writer, ItemProcessor<Persona, PersonaUrl> processor) {
		return stepBuilderFactory.get("step1")
				.<Persona, PersonaUrl> chunk(10)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
	}

}
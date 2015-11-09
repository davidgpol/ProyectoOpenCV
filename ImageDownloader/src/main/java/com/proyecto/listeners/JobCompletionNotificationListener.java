package com.proyecto.listeners;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
	
	private static Logger log = LogManager.getLogger(JobCompletionNotificationListener.class);
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		batchExecution(jobExecution);
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		batchExecution(jobExecution);
	}

	private void batchExecution(JobExecution jobExecution) {
		long jobID = jobExecution.getJobInstance().getId();
		String nombre = jobExecution.getJobInstance().getJobName();
		switch (jobExecution.getStatus()) {
		case STARTING:			
			log.info("Job (" + nombre + " - " + jobID + ") empezando a ejecutarse");
			break;
		case STARTED:			
			log.info("Job (" + nombre + " - " + jobID + ") ejecutandose");
			break;			
		case COMPLETED:
			log.info("Job (" + nombre + " - " + jobID + ") completado con exito");
			break;
		case FAILED:
			log.info("Job (" + nombre + " - " + jobID + ") ha terminado en un estado de error " + jobExecution.getAllFailureExceptions());
		default:
			break;
		}		
	}
}
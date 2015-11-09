package com.proyecto.aspecto;

import java.time.Duration;
import java.time.Instant;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TiempoAspecto {
	
	private static Logger log = LogManager.getLogger(TiempoAspecto.class);

//	@Before("execution(* com.proyectos.servicio.Servicio.servicio1(..))")
//	public void aspectoAntes(JoinPoint joinPoint) {
//		log.info("Antes de ejecutar el metodo: " + joinPoint.getSignature().getName());
//	}
//	
//	@After("execution(* com.proyectos.servicio.Servicio.servicio1(..))")
//	public void aspectoDespues(JoinPoint joinPoint) {
//		log.info("Despues de ejecutar el metodo: " + joinPoint.getSignature().getName());
//	}
	
	@Around("execution(* *.process(..)) || execution(* *.write(..))")
	public Object aspectoAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Instant inicio = Instant.now();
		Object resultado = proceedingJoinPoint.proceed();
		Instant fin = Instant.now();
		Duration duration = Duration.between(inicio, fin);
		log.info("Operacion del metodo (" + proceedingJoinPoint.getSignature() + ") realizada en: " + duration.getSeconds() + " segundo(s)");
		return resultado;
	}
	
}

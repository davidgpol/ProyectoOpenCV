package main.java.configuracion;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"main.java.service", "main.java.timer"})
public class ContextoSpring {}
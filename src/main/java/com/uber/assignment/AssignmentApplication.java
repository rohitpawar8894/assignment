package com.uber.assignment;

import java.io.InputStream;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

/* *
 *  Assessment Application
 * */


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class AssignmentApplication {
	
	public static ConfigurableApplicationContext applicationContext;
	
	public static void main(String[] args) {
		applicationContext = SpringApplication.run(AssignmentApplication.class, args);
		System.out.println("Hello World!");
		
		/* Configure Log4j  for Application */
		InputStream fontInputStream = AssignmentApplication.class.getResourceAsStream("/log4j.properties");
		PropertyConfigurator.configure(fontInputStream);
	}

}

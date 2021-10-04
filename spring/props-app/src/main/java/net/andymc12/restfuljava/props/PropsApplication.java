package net.andymc12.restfuljava.props;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class PropsApplication {

	public static void main(String[] args) {
		//SpringApplication.run(PropsApplication.class, args);
		SpringApplicationBuilder app = new SpringApplicationBuilder(PropsApplication.class)
		    .web(WebApplicationType.SERVLET);
		app.build().addListeners(new ApplicationPidFileWriter(System.getProperty("user.home") + "/tmp/shutdown.pid"));
		app.run();
	}
}
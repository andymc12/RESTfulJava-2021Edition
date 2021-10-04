package net.andymc12.restfuljava.content;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class PersonApplication {

	public static void main(String[] args) {
		SpringApplicationBuilder app = new SpringApplicationBuilder(PersonApplication.class)
		    .web(WebApplicationType.SERVLET);
		app.build().addListeners(new ApplicationPidFileWriter(System.getProperty("user.home") + "/tmp/shutdown.pid"));
		app.run();
	}
}
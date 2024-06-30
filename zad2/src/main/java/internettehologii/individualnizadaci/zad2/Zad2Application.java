package internettehologii.individualnizadaci.zad2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class Zad2Application {
	public static void main(String[] args) {
		SpringApplication.run(Zad2Application.class, args);
	}

}

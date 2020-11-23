package cn.com.partical.development.system.developmentservice;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SuppressWarnings("ALL")
@SpringBootApplication
@EnableSwagger2Doc
@EnableAsync
public class DevelopmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevelopmentServiceApplication.class, args);
	}

}

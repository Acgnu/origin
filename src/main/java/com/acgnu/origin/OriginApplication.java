package com.acgnu.origin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//@SpringBootApplication
//public class DemoApplication extends SpringBootServletInitializer {
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(DemoApplication.class);
//	}
//
//	public static void main(String[] args) {
//		SpringApplication.run(DemoApplication.class, args);
//	}
//}
@MapperScan("com.origin.mapper")
//@ComponentScan("com.origin")
@SpringBootApplication
public class OriginApplication {

    public static void main(String[] args) {
        SpringApplication.run(OriginApplication.class, args);
    }

}

package com.snow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages= {"com.snow.sys.mapper","com.snow.bus.mapper"})
public class SpringbootBsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBsApplication.class, args);
	}

}

package zq.bim;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "zq.bim.dao")
public class ZqBoxApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZqBoxApplication.class, args);
	}

}

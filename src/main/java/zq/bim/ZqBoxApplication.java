package zq.bim;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling   //开启定时服务
@SpringBootApplication
@MapperScan(basePackages = "zq.bim.dao")
public class ZqBoxApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ZqBoxApplication.class);
        app.addListeners(new ApplicationPidFileWriter());
        app.run(args);
	}

}

package sijibao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * web启动器
 * @author Tao.yu
 */
@SpringBootApplication
public class WebOaApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WebOaApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebOaApplication.class);
	}
}

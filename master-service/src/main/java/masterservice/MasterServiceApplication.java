package masterservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableScheduling
@ComponentScan("masterservice")
public class MasterServiceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MasterServiceApplication.class).run(args);
    }
}


package importerservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class ImporterServiceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ImporterServiceApplication.class).run(args);
    }

}
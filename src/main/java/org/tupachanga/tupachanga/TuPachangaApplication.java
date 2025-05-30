package org.tupachanga.tupachanga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class TuPachangaApplication {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

        System.setProperty("EMAIL_USERNAME", dotenv.get("EMAIL_USERNAME"));
        System.setProperty("EMAIL_PASSWORD", dotenv.get("EMAIL_PASSWORD"));

        SpringApplication.run(TuPachangaApplication.class, args);
    }

}

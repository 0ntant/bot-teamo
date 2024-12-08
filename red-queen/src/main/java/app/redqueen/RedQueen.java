package app.redqueen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class RedQueen
{
    public static void main(String[] args)
    {
        SpringApplication.run(RedQueen.class,args);
    }
}
package com.vcs.bogdan.spring.resttest;

import com.vcs.bogdan.service.db.ConnectionServiceImpl;
import com.vcs.bogdan.service.db.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.vcs.bogdan.service.db.*"})
public class RestTestApp {

    public static void main(String[] args) {

        UserService service = new UserService();
        service.start();





        SpringApplication.run(RestTestApp.class, args);



    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {


//            System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//            String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName : beanNames) {
//                System.out.println(beanName);
//            }

        };
    }



}



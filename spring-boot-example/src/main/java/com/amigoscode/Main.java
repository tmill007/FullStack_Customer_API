package com.amigoscode;

import com.amigoscode.Customer.Customer;
import com.amigoscode.Customer.CustomerRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository){
        return args -> {
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();


            Customer customer = new Customer(firstName + " " + lastName,
                                                firstName + "." + lastName + "@email.com",
                                                faker.number().numberBetween(20, 75));

            customerRepository.save(customer);
        };
    }
}
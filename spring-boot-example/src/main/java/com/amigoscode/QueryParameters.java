/*package com.amigoscode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class QueryParameters {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/greet2")
    public GreetResponse greet(@RequestParam(value = "name", required = false)  String name){
        String response = name == null || name.isBlank() ? "Hello" : "Hello " + name;
        return new GreetResponse(response,
                List.of("Java", "Javascript", "SQL", "Python"),
                new Person("Tony Miller", 43, 2_000_000_000)
        );
    }

    record Person(String name, int age, double savings){}

    record GreetResponse(String greeting, List<String> programmingLanguages, Person person){}



}
*/
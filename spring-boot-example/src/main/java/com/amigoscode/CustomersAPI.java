/*package com.amigoscode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
@RestController
public class CustomersAPI {
    private static List<Customer> customers;

    //mock DB
    static {
        customers = new ArrayList<>();
        Customer tony = new Customer(1, "Tony", "tmill@gmail.com", 43);
        customers.add(tony);
        Customer ronique = new Customer(2, "Ronique", "rrob@yahoo.com", 41);
        customers.add(ronique);
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    //endpoint 1
    @GetMapping("api/v1/customers")
    public List<Customer> getCustomers(){
        return customers;
    }

    //endpoint 2 using path parameters
    @GetMapping("api/v1/customers/{customerId}")
    public Customer getCustomers(@PathVariable("customerId") Integer customerId){
        return customers.stream()
                        .filter(customer -> customer.id.equals(customerId))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("customer with ID [%s] not found".formatted(customerId)));
    }

    //Customer Model
    public static class Customer{
        private Integer id;
        private String name;
        private String email;
        private int age;
        public Customer() {
        }

        public Customer(int id, String name, String email, int age) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.age = age;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Customer{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", age=" + age +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Customer customer = (Customer) o;
            return id == customer.id && age == customer.age && Objects.equals(name, customer.name) && Objects.equals(email, customer.email);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, email, age);
        }
    }



}
*/
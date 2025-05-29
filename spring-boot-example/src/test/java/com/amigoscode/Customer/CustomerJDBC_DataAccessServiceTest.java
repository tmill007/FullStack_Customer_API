package com.amigoscode.Customer;

import com.amigoscode.AbstractTestContainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerJDBC_DataAccessServiceTest extends AbstractTestContainers {

    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();
    private CustomerJDBC_DataAccessService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBC_DataAccessService(
                getJDBCTemplate(),
                customerRowMapper
        );
    }

    @Test
    void getAllCustomers() {
        //GIVEN
        var name = faker.name().fullName();
        var email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(name, email, faker.number().numberBetween(18, 80));
        underTest.insertCustomer(customer);

        //WHEN
        List<Customer> actual = underTest.getAllCustomers();

        //THEN
        assertThat(actual).isNotEmpty();
    }

    @Test
    void getCustomerById() {
        //GIVEN
        var name = faker.name().fullName();
        var email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(name, email, faker.number().numberBetween(18, 80));
        underTest.insertCustomer(customer);

      int Id = underTest.getAllCustomers().stream()
              .filter(c -> c.getEmail().equals(email))
              .map(Customer::getId)
              .findFirst()
              .orElseThrow();

        //WHEN
        Optional<Customer> actual = underTest.getCustomerById(Id);

        //THEN
        assertThat(actual).isPresent().hasValueSatisfying(c ->{
            assertThat(c.getId()).isEqualTo(Id);
            assertThat(c.getName()).isEqualTo(name);
            assertThat(c.getEmail()).isEqualTo(email);
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void willReturnEmptyWhenSelectCustomerById() {
        //GIVEN
        int Id = -1;
        //WHEN
        var actual = underTest.getCustomerById(Id);
        //THEN
        assertThat(actual).isEmpty();
    }

    @Test
    void insertCustomer() {
        //GIVEN
        String name = faker.name().fullName();
        String email = faker.internet().safeEmailAddress() +"-"+ UUID.randomUUID();
        Customer customer = new Customer(name, email,faker.number().numberBetween(18, 80));

        //WHEN
        underTest.insertCustomer(customer);


        //THEN
        Optional<Customer> actual = underTest.getAllCustomers().stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst();

        assertThat(actual).isPresent().hasValueSatisfying(c ->{
            assertThat(c.getName()).isEqualTo(name);
            assertThat(c.getEmail()).isEqualTo(email);
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void existsCustomerByEmail() {
        //GIVEN
        String name = faker.name().fullName();
        String email = faker.internet().safeEmailAddress() +"-"+ UUID.randomUUID();
        Customer customer = new Customer(name, email,faker.number().numberBetween(18, 80));
        underTest.insertCustomer(customer);

        //WHEN
        boolean actual = underTest.existsCustomerByEmail(email);

        //THEN
        assertThat(actual).as("Check if customer exists by email").isTrue();
    }

    @Test  //TODO: Test JDBC existsCustomerByEmailReturnsFalseWhenNotExists()
    void existsCustomerByEmailReturnsFalseWhenNotExists() {
        //GIVEN
        //WHEN

        //THEN
    }

    @Test
    void existsCustomerByID() {
        //GIVEN
        String name = faker.name().fullName();
        String email = faker.internet().safeEmailAddress() +"-"+ UUID.randomUUID();
        Customer customer = new Customer(name, email,faker.number().numberBetween(18, 80));
        underTest.insertCustomer(customer);

        int Id = underTest.getAllCustomers().stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //WHEN
        boolean actual = underTest.existsCustomerByID(Id);
        //THEN
        assertThat(actual).as("Check if customer exists by ID").isTrue();
    }

    @Test
    void deleteCustomerById() {
        //GIVEN
        String name = faker.name().fullName();
        String email = faker.internet().safeEmailAddress() +"-"+ UUID.randomUUID();
        Customer customer = new Customer(name, email,faker.number().numberBetween(18, 80));
        underTest.insertCustomer(customer);

        int Id = underTest.getAllCustomers().stream()
                .filter(c-> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        //WHEN
        underTest.deleteCustomerById(Id);

        //THEN
        Optional<Customer> actual = underTest.getCustomerById(Id);
        assertThat(actual).isEmpty();
    }

    @Test
    void updateCustomer() {
        //GIVEN - original customer inserted
        String name = faker.name().fullName();
        String email = faker.internet().safeEmailAddress() +"-"+ UUID.randomUUID();
        Customer customer = new Customer(name, email,faker.number().numberBetween(18, 80));
        underTest.insertCustomer(customer);

        int Id = underTest.getAllCustomers().stream()
                .filter(c-> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        //WHEN - update is performed
        var updatedName = "updateName";
        var updatedAge = 25;
        var updatedEmail = "updateEmail"+ "-" + UUID.randomUUID() + "@email.com";
        Customer updatedCustomer = new Customer(Id, updatedName, updatedEmail, updatedAge);

        underTest.updateCustomer(updatedCustomer);
        var actual = underTest.getCustomerById(Id);

        //THEN - verify customer data is updated
        assertThat(actual).isPresent().hasValueSatisfying( c-> {
            assertThat(c.getId()).isEqualTo(Id);
            assertThat(c.getName()).isEqualTo(updatedName);
            assertThat(c.getEmail()).isEqualTo(updatedEmail);
            assertThat(c.getAge()).isEqualTo(updatedAge);
                }
        );
    }

    @Test
    void updateCustomerName_onlyUpdatesName() {
        //GIVEN
        String name = faker.name().fullName();
        String email = faker.internet().safeEmailAddress() +"-"+ UUID.randomUUID();
        int age = faker.number().numberBetween(18, 80);
        Customer customer = new Customer(name, email, age);
        underTest.insertCustomer(customer);

        int Id = underTest.getAllCustomers().stream()
                .filter(c-> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        //WHEN - update is performed

        var updatedName = "updateName";  //TODO: Refactor and pass only ID, Updated Name to undertest
        Customer updatedCustomer = new Customer(Id, updatedName, customer.getEmail(), customer.getAge());
        //WHEN
        underTest.updateCustomer(updatedCustomer);

        //THEN

        var actual = underTest.getCustomerById(Id);
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(Id);
            assertThat(c.getName()).isEqualTo(updatedName);
            assertThat(c.getEmail()).isEqualTo(email);
            assertThat(c.getAge()).isEqualTo(age);
        });
    }

    @Test
    void updateCustomerEmail_onlyUpdatesEmail() {
        //GIVEN
        String name = faker.name().fullName();
        String email = faker.internet().safeEmailAddress() +"-"+ UUID.randomUUID();
        int age = faker.number().numberBetween(18, 80);
        Customer customer = new Customer(name, email, age);
        underTest.insertCustomer(customer);

        int Id = underTest.getAllCustomers().stream()
                .filter(c-> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        //WHEN - update is performed

        var updatedEmail = "updateEmail"+ "-" + UUID.randomUUID() + "@email.com";
        Customer updatedCustomer = new Customer(Id, customer.getName(), updatedEmail, customer.getAge());
        //WHEN
        underTest.updateCustomer(updatedCustomer);

        //THEN
        var actual = underTest.getCustomerById(Id);
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(Id);
            assertThat(c.getName()).isEqualTo(name);
            assertThat(c.getEmail()).isEqualTo(updatedEmail);
            assertThat(c.getAge()).isEqualTo(age);
        });
    }

    @Test
    void updateCustomerAge_onlyUpdatesAge() {
        //GIVEN
        //WHEN

        //THEN
    }
}
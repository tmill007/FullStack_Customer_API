package com.amigoscode.Customer;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock private CustomerRepository customerRepository;
    Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllCustomers() {

        //WHEN
        underTest.getAllCustomers();

        //THEN
        Mockito.verify(customerRepository).findAll();
    }

    @Test
    void getCustomerById() {
        //GIVEN
        int id = 1;
        //WHEN
        underTest.getCustomerById(id);
        //THEN
        Mockito.verify(customerRepository).findById(id);
    }

    @Test
    void insertCustomer() {
        //GIVEN
        Customer customer = new Customer(
                faker.name().fullName(),
                faker.internet().safeEmailAddress(),
                faker.number().numberBetween(15, 80)
        );

        //WHEN
        underTest.insertCustomer(customer);

        //THEN
        Mockito.verify(customerRepository).save(customer);
    }

    @Test
    void existsCustomerByEmail() {
        //GIVEN
        String email = faker.internet().safeEmailAddress();
        //WHEN
        underTest.existsCustomerByEmail(email);
        //THEN
        Mockito.verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void existsCustomerByID() {
        //GIVEN
        int id = 2;
        //WHEN
        underTest.existsCustomerByID(id);
        //THEN
        Mockito.verify(customerRepository).existsCustomerById(id);
    }

    @Test
    void deleteCustomerById() {
        //GIVEN
        int id = 2;
        //WHEN
        underTest.deleteCustomerById(id);
        //THEN
        Mockito.verify(customerRepository).deleteById(id);
    }

    @Test
    void updateCustomer() {
        //GIVEN
        Customer updatedCustomer = new Customer(
                faker.name().fullName(),
                faker.internet().safeEmailAddress(),
                faker.number().numberBetween(15, 80)
        );
        //WHEN
        underTest.updateCustomer(updatedCustomer);
        //THEN
        Mockito.verify(customerRepository).save(updatedCustomer);
    }
}
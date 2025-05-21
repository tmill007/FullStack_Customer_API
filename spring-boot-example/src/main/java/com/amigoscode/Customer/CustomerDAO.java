package com.amigoscode.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(Integer Id);
    void insertCustomer(Customer customer);
    boolean existsCustomerByEmail(String email);
    boolean existsCustomerByID(Integer Id);
    void deleteCustomerById(Integer Id);
    void updateCustomer(Customer updatedCustomer);
}

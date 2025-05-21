package com.amigoscode.Customer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class CustomerJPADataAccessService implements CustomerDAO{
    private final CustomerRepository customerRepository;

    public CustomerJPADataAccessService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(Integer Id) {

        return customerRepository.findById(Id);
    }

    @Override
    public void insertCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public boolean existsCustomerByEmail(String email) {
        return customerRepository.existsCustomerByEmail(email);
    }

    @Override
    public boolean existsCustomerByID(Integer Id) {
        return customerRepository.existsCustomerById(Id);
    }

    @Override
    public void deleteCustomerById(Integer Id) {
        customerRepository.deleteById(Id);
    }

    @Override
    public void updateCustomer(Customer updatedCustomer) {
        customerRepository.save(updatedCustomer);
    }

}

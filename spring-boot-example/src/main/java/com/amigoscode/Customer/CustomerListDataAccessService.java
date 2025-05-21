package com.amigoscode.Customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerListDataAccessService implements CustomerDAO{
    private static final List<Customer> customers;

    //mock DB
    static {
        customers = new ArrayList<>();
        Customer tony = new Customer(1, "Tony", "tmill@gmail.com", 43);
        customers.add(tony);
        Customer ronique = new Customer(2, "Ronique", "rrob@yahoo.com", 41);
        customers.add(ronique);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> getCustomerById(Integer Id) {
        return customers.stream()
                        .filter(customer -> customer.getId().equals(Id))
                        .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsCustomerByEmail(String email) {
        return customers.stream()
                .anyMatch(customer -> customer.getEmail().equals(email));
    }

    @Override
    public boolean existsCustomerByID(Integer Id) {
       return customers.stream()
               .anyMatch((customer -> customer.getId().equals(Id)));
    }

    @Override
    public void deleteCustomerById(Integer Id) {
        customers.stream()
                .filter(customer -> customer.getId().equals(Id))
                .findFirst()
                .ifPresent(customers::remove);
    }

    @Override
    public void updateCustomer(Customer updatedCustomer) {
        //Find the customer and remove
        customers.removeIf(customer -> customer.getId().equals(updatedCustomer.getId()));
        //add customer with updates
        customers.add(updatedCustomer);
    }
}

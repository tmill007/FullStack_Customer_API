package com.amigoscode.Customer;

import com.amigoscode.Exception.RequestValidationException;
import com.amigoscode.Exception.ResourceNotFoundException;
import com.amigoscode.Exception.duplicateResourceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerService(@Qualifier("jdbc") CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public List<Customer> getAllCustomers(){
        return customerDAO.getAllCustomers();
    }

    public Customer getCustomerById(Integer Id){
        return customerDAO.getCustomerById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with ID [%s] not found".formatted(Id)));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        //check if email already registered
        if(customerDAO.existsCustomerByEmail(customerRegistrationRequest.email())){
            throw new duplicateResourceException("email already taken");
        }
        //add customer
        customerDAO.insertCustomer(
                new Customer(
                        customerRegistrationRequest.name(),
                        customerRegistrationRequest.email(),
                        customerRegistrationRequest.age()
                )
        );
    }

    public void deleteCustomerById(Integer Id){
        //If ID is not valid throw error
        if(!customerDAO.existsCustomerByID(Id)){
            throw new ResourceNotFoundException("Customer with ID [%s] not found".formatted(Id));
        }
        //delete
        customerDAO.deleteCustomerById(Id);
    }

    public void updateCustomer(Integer Id, CustomerUpdateRequest updateRequest){
          Customer customer = getCustomerById(Id);

          var changes = false;
          if(updateRequest.name() != null && !updateRequest.name().equals(customer.getName())){
              customer.setName(updateRequest.name());
              changes = true;
          }
          if(updateRequest.age() != null && !updateRequest.age().equals(customer.getAge())){
              customer.setAge(updateRequest.age());
              changes = true;
          }
          if(updateRequest.email() != null && !updateRequest.email().equals(customer.getEmail())){
              if(customerDAO.existsCustomerByEmail(updateRequest.email())){
                  throw new duplicateResourceException("email already taken");
              }
              customer.setEmail(updateRequest.email());
              changes = true;
          }

          if (!changes){
              throw new RequestValidationException("No Data Changes Found");
          }
          customerDAO.updateCustomer(customer);
    }
}

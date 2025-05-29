package com.amigoscode.Customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBC_DataAccessService implements CustomerDAO{
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBC_DataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> getAllCustomers() {
        String sql = """ 
                SELECT id, name, email, age FROM customer
                """;

        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> getCustomerById(Integer Id) {
        String sql = """
                    SELECT id, name, email, age FROM customer
                    WHERE id = ?
                """;

        return jdbcTemplate.query(sql, customerRowMapper, Id)
                .stream()
                .findFirst(); //find first returns an optional<T> if no result found

        //Another approach
            //        try {
            //            Customer customer = jdbcTemplate.queryForObject(sql, customerRowMapper,Id); // Throws an exception
            //            return Optional.of(customer);
            //        } catch (EmptyResultDataAccessException e) {
            //            return Optional.empty();
            //     }
    }

    @Override
    public void insertCustomer(Customer customer) {
        String sql = """
                INSERT INTO customer (name, email, age)
                VALUES (?, ?, ?)
                """;

        int result = jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getAge());
        System.out.println(result);
    }

    @Override
    public boolean existsCustomerByEmail(String email) {
        String sql = """
                SELECT EXISTS(
                    SELECT 1 FROM customer
                    WHERE email = ?
                )
                """;

        return jdbcTemplate.queryForObject(sql, Boolean.class, email);

    }

    @Override
    public boolean existsCustomerByID(Integer Id) {
        String sql = """
                SELECT EXISTS(
                    SELECT 1 FROM customer
                    WHERE id = ?)
                """;

        return jdbcTemplate.queryForObject(sql, Boolean.class, Id);
    }

    @Override
    public void deleteCustomerById(Integer Id) {
        String sql = """
                DELETE FROM customer
                WHERE id = ?
                """;

        jdbcTemplate.update(sql,Id);
    }

    @Override
    public void updateCustomer(Customer updatedCustomer) {
        String sql = """
                UPDATE customer
                SET name = ?, email = ?, age = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, updatedCustomer.getName(),
                                        updatedCustomer.getEmail(),
                                        updatedCustomer.getAge(),
                                        updatedCustomer.getId());
    }
}

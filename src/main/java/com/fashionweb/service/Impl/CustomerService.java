package com.fashionweb.service.Impl;

import com.fashionweb.Entity.Customer;
import com.fashionweb.repository.ICustomerRepository;
import com.fashionweb.service.ICustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public <S extends Customer> S save(S customer) {
        if (customer.getCustId() == null) {
            return customerRepository.save(customer);
        } else {
            Optional<Customer> customerOptional = customerRepository.findById(customer.getCustId());
            if (customerOptional.isPresent()) {
                return customerRepository.save(customer);
            } else {
                throw new EntityNotFoundException("Customer with ID " + customer.getCustId() + " does not exist.");
            }
        }
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long custId) {
        return customerRepository.findById(custId);
    }

    @Override
    public void deleteById(Long custId) {
        customerRepository.deleteById(custId);
    }

    @Override
    public List<Customer> findByCustNameContaining(String custName) {
        return customerRepository.findByCustNameContaining(custName);
    }

    @Override
    public Optional<Customer> findByCustName(String custName) {
        return customerRepository.findByCustName(custName);
    }
}

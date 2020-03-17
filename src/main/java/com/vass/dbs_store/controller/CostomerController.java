package com.vass.dbs_store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vass.dbs_store.exception.ResourceNotFoundException;
import com.vass.dbs_store.model.*;
import com.vass.dbs_store.repository.CustomerRepository;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.OPTIONS})
@RequestMapping("/api/v1")
public class CostomerController {
	
	@Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customer")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Long customerId)
        throws ResourceNotFoundException {
    	Customer customer = customerRepository.findById(customerId)
          .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));
        return ResponseEntity.ok().body(customer);
    }
    
    @PostMapping("/customer")
    public Customer createEmployee(@Valid @RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<Customer> updateEmployee(@PathVariable(value = "id") Long customerId,
         @Valid @RequestBody Customer customerDetails) throws ResourceNotFoundException {
    	Customer customer = customerRepository.findById(customerId)
        .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));
    	
    	customer.setStoreId(customerDetails.getStoreId());
    	customer.setFirstName(customerDetails.getFirstName());
    	customer.setLastName(customerDetails.getLastName());
    	customer.setEmail(customerDetails.getEmail());
    	customer.setAddressId(customerDetails.getAddressId());
    	customer.setActivebool(customerDetails.getActivebool());
    	customer.setCreateDate(customerDetails.getCreateDate());
    	customer.setLastUpdate(customerDetails.getLastUpdate());
    	customer.setActive(customerDetails.getActive());	
    	
        final Customer updatedCustomer = customerRepository.save(customer);
        return ResponseEntity.ok(updatedCustomer);
    }
    
    
    @DeleteMapping("/customer/{id}")
    public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") Long customerId)
         throws ResourceNotFoundException {
    	Customer customer = customerRepository.findById(customerId)
       .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));

    	customerRepository.delete(customer);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    
}

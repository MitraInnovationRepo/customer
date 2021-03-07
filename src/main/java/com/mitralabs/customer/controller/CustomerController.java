package com.mitralabs.customer.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mitralabs.customer.dto.CustomerDTO;
import com.mitralabs.customer.entity.Customer;
import com.mitralabs.customer.repository.CustomerRepository;

@RestController
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("/customer/{id}")
	public ResponseEntity<CustomerDTO> getCustomer(@PathVariable int id) {

		Optional<Customer> c = customerRepository.findById(id);

		if (c.isPresent()) {
			CustomerDTO cdto = new CustomerDTO(c.get().getFirstName(), c.get().getLastName(), c.get().getAddress(),
					c.get().getEmail());
			return new ResponseEntity<CustomerDTO>(cdto, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/customer")
	public ResponseEntity<String> saveCustomer(@RequestBody CustomerDTO customerDTO) {

		Customer c = new Customer();
		c.setFirstName(customerDTO.getFirstName());
		c.setLastName(customerDTO.getLastName());
		c.setAddress(customerDTO.getAddress());
		c.setEmail(customerDTO.getEmail());

		customerRepository.save(c);
		return new ResponseEntity<String>("Customer saved", HttpStatus.OK);
	}
}

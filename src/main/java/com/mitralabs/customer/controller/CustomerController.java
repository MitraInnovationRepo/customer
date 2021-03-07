package com.mitralabs.customer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

	@GetMapping("/greeting")
	public String getCustomer() {
		return "Hello World";
	}
}

package com.mitralabs.customer.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

	@GetMapping("/")
	public String getCustomer() throws UnknownHostException {
		
		InetAddress inetAddress = InetAddress.getLocalHost();		
		return "Customer:" + inetAddress.getHostAddress() + ":" + inetAddress.getHostName();
	}
}

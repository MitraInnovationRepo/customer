package com.mitralabs.customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mitralabs.customer.command.CustomerCommand;
import com.mitralabs.customer.command.CustomerCreatedCommand;
import com.mitralabs.customer.dto.CustomerDTO;
import com.mitralabs.customer.event.CustomerCreatedEvent;

import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;
import lombok.Getter;


public class CustomerAggregate extends ReflectiveMutableCommandProcessingAggregate<CustomerAggregate, CustomerCommand> {
	/**
	 * Customer entity
	 */
	@Getter
	private CustomerDTO customer;

	@Getter
	private Map<String, String> accounts = new HashMap();

	public List<Event> process(CustomerCreatedCommand cmd) {
		return EventUtil.events(
				new CustomerCreatedEvent(cmd.getFirstName(), cmd.getLastName(), cmd.getAddress(), cmd.getEmail()));
	}

	public void apply(CustomerCreatedEvent event) {
		customer = new CustomerDTO();
		customer.setFirstName(event.getFirstName());
		customer.setLastName(event.getLastName());
		customer.setAddress(event.getAddress());
		customer.setEmail(event.getEmail());
	}
}

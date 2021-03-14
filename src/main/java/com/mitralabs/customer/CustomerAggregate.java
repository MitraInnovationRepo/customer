package com.mitralabs.customer;

import java.util.List;

import com.mitralabs.customer.command.AccountAddedCommand;
import com.mitralabs.customer.command.CustomerCommand;
import com.mitralabs.customer.command.CustomerCreatedCommand;
import com.mitralabs.customer.dto.AccountDTO;
import com.mitralabs.customer.dto.CustomerDTO;
import com.mitralabs.customer.event.AccountAddedEvent;
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

	public List<Event> process(CustomerCreatedCommand cmd) {
		return EventUtil.events(new CustomerCreatedEvent(cmd.getFirstName(), cmd.getLastName(), cmd.getAddress(),
				cmd.getEmail(), "SAVINGS"));
	}

	public List<Event> process(AccountAddedCommand cmd) {
		return EventUtil.events(new AccountAddedEvent(cmd.getAccountId(), cmd.getAccountType(), cmd.getCreatedAt()));
	}

	public void apply(CustomerCreatedEvent event) {
		customer = new CustomerDTO();
		customer.setFirstName(event.getFirstName());
		customer.setLastName(event.getLastName());
		customer.setAddress(event.getAddress());
		customer.setEmail(event.getEmail());
	}

	public void apply(AccountAddedEvent event) {
		AccountDTO account = new AccountDTO();
		account.setAccountId(event.getAccountId());
		account.setAccountType(event.getAccountType());
		account.setCreatedAt(event.getCreatedAt());

		customer.getAccounts().put(event.getAccountId(), account);
		customer.setStatus("ACTIVE");
	}
}

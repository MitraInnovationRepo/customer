package com.mitralabs.customer.eventhandler;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitralabs.account.event.AccountCreatedEvent;
import com.mitralabs.customer.service.CustomerService;

import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import lombok.extern.slf4j.Slf4j;

@EventSubscriber(id = "customerEventHandler")
@Slf4j
public class CustomerEventHandler {

	@Autowired
	private CustomerService customerService;

	@EventHandlerMethod
	public void accountCreatedEvent(DispatchedEvent<AccountCreatedEvent> event) {
		log.info("AccountCreatedEvent recived", event.getEntityId());

		AccountCreatedEvent aEvent = event.getEvent();

		try {
			customerService.addAccount(aEvent);
		} catch (Exception e) {

			e.printStackTrace();
			log.error("Error creating account", e.getMessage());
		}
	}
}

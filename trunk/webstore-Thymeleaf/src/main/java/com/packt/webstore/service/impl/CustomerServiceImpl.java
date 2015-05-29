package com.packt.webstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packt.webstore.domain.Customer;
import com.packt.webstore.domain.repository.IcustomerRepository;
import com.packt.webstore.service.IcustomerService;

@Service
public class CustomerServiceImpl implements IcustomerService {
    @Autowired
	private IcustomerRepository customerReprository;
	
	public List<Customer> getAllCustomers() {
		return customerReprository.getAllCustomers();
	}

}

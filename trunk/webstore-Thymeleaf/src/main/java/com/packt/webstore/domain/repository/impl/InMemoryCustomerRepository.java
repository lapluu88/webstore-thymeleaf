package com.packt.webstore.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.packt.webstore.domain.Customer;
import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.IcustomerRepository;

@Repository
public class InMemoryCustomerRepository implements IcustomerRepository{
	private List<Customer> listOfCustomers = new ArrayList<Customer>();
	
	public InMemoryCustomerRepository(){
		Customer johnDoe= new Customer("1234","John Doe","9053951234");
		listOfCustomers.add(johnDoe);
		Customer janeDoe= new Customer("1235","Mary Doe","6473248956");
				
		listOfCustomers.add(janeDoe);
		Customer SteveJobs= new Customer("1236","Steve Jobs","6479238756");
		listOfCustomers.add(SteveJobs);
		Customer XinFlix= new Customer("1237","Xin Flix","6042347689");
		listOfCustomers.add(XinFlix);
	}
	public List<Customer> getAllCustomers() {	
		return listOfCustomers;
	}

}

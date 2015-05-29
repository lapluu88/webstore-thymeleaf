package com.packt.webstore.service;

import com.packt.webstore.domain.Order;

public interface IorderService {
	void processOrder(String  productId, long count);
	Long saveOrder(Order order);
}

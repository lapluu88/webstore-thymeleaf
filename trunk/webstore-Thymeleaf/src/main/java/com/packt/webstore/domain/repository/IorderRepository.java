package com.packt.webstore.domain.repository;

import com.packt.webstore.domain.Order;

public interface IorderRepository {
	Long saveOrder(Order order);
}

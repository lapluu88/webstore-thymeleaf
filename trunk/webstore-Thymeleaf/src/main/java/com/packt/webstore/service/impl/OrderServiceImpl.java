package com.packt.webstore.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packt.webstore.domain.Order;
import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.IorderRepository;
import com.packt.webstore.domain.repository.IproductRepository;
import com.packt.webstore.service.IcartService;
import com.packt.webstore.service.IorderService;

@Service
public class OrderServiceImpl implements IorderService{

  @Autowired
  private IproductRepository productRepository;
  @Autowired
  private IorderRepository orderRepository;
  
  @Autowired
  private IcartService cartService;
  
  public void processOrder(String productId, long quantity) {
    Product productById = productRepository.getProductById(productId);
    
    if(productById.getUnitsInStock() < quantity){
      throw new IllegalArgumentException("Out of Stock. Available Units in stock"+ productById.getUnitsInStock());
    }
    
    productById.setUnitsInStock(productById.getUnitsInStock() - quantity);
  }

  public Long saveOrder(Order order) {
	  Long orderId = orderRepository.saveOrder(order);
	  cartService.delete(order.getCart().getCartId());
	  return orderId;
	}

}

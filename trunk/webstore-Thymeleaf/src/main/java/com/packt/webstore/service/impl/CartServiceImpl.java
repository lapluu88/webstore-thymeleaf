package com.packt.webstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.packt.webstore.domain.Cart;
import com.packt.webstore.domain.repository.IcartRepository;
import com.packt.webstore.exception.InvalidCartException;
import com.packt.webstore.service.IcartService;

@Service
public class CartServiceImpl implements IcartService{
  
  @Autowired
  private IcartRepository cartRepository;

  public Cart create(Cart cart) {
    return cartRepository.create(cart);
  }

  public Cart read(String cartId) {
    return cartRepository.read(cartId);
  }

  public void update(String cartId, Cart cart) {
    cartRepository.update(cartId, cart);
  }

  public void delete(String cartId) {
    cartRepository.delete(cartId);
    
  }
  public Cart validate(String cartId) {
	    Cart cart = cartRepository.read(cartId);
	    if(cart==null || cart.getCartItems().size()==0) {
	      throw new InvalidCartException(cartId);
	    }
	    
	    return cart;
  }
}

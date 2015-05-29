package com.packt.webstore.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.IproductRepository;
import com.packt.webstore.service.IproductService;

@Service
public class ProductServiceImpl implements IproductService{
	@Autowired
	private IproductRepository productRepository;
	
	public List <Product> getAllProducts(){
		return productRepository.getAllProducts();
	}
	public List<Product> getProductsByCategory(String category) {
		  return productRepository.getProductsByCategory(category);
	}
	public Set<Product> getProductsByFilter(
			Map<String, List<String>> filterParams) {
		return productRepository.getProductsByFilter(filterParams);
	}
	public Product getProductById(String id) {
		
		return productRepository.getProductById(id);
	}
	public List<Product> getProductsByManufacturer(String manufacturer) {
		
		return productRepository.getProductsByManufacturer(manufacturer);
	}
	public Set<Product> getProductsByPriceFilter(
			Map<String, List<String>> filterParams) {
		
		return productRepository.getProductsByPriceFilter(filterParams);
	}
	public void addProduct(Product product) {
		productRepository.addProduct(product);
	}
}

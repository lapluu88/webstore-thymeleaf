package com.packt.webstore.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.packt.webstore.domain.Product;

public interface IproductService {
	List <Product> getAllProducts();
	List<Product> getProductsByCategory(String category);
	Set<Product> getProductsByFilter(Map<String, List<String>> filterParams);
	Set <Product> getProductsByPriceFilter(Map<String, List<String>> filterParams);
	Product getProductById(String id);
	List <Product> getProductsByManufacturer(String manufacturer);
	void addProduct(Product product);
	
}

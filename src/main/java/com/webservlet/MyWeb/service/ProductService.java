package com.webservlet.MyWeb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webservlet.MyWeb.model.Product;
import com.webservlet.MyWeb.repository.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	ProductRepo repo;
	
//	List<Product> products = new ArrayList<>(Arrays.asList(
//				new Product(101, "Iphone", 5000),
//				new Product(102, "SmartWatch", 2500),
//				new Product(103, "TvSmart", 7000),
//				new Product(104, "Camera", 7500),
//				new Product(105, "Laptop", 5500)
//			));

	public List<Product> getProducts(){
		return repo.findAll();
	}

	public Product getProductById(int prodId) {
		return repo.findById(prodId).orElse(null);
	}
	
	public void addProduct(Product prod) {
		repo.save(prod);
	}

	public void updateProduct(Product prod) {
		repo.save(prod);
	}

	public void deleteProduct(int prodId) {
		repo.deleteById(prodId);
	}
}

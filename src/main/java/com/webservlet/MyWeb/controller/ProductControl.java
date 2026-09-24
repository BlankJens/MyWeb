package com.webservlet.MyWeb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.webservlet.MyWeb.model.Product;
import com.webservlet.MyWeb.service.ProductService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class ProductControl {

	@Autowired
	ProductService service;
	
	@GetMapping("/api/products")
	public ResponseEntity<List<Product>> getProducts() {
		return new ResponseEntity<>(service.getProducts(), HttpStatus.OK);
	}
	
	@GetMapping("/api/products/{prodId}")
	public ResponseEntity<Product> getProductById(@PathVariable int prodId) {
		Product product = service.getProductById(prodId);
		
		if (product != null) {
			return new ResponseEntity<>(product, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/api/products")
	public void addProduct(@RequestBody Product prod) {
		System.out.println(prod);
		service.addProduct(prod);
	}
	
	@PutMapping("/api/products")
	public void updateProduct(@RequestBody Product prod) {
		service.updateProduct(prod);
	}
	
	@DeleteMapping("/api/products/{prodId}")
	public void deleteProduct(@PathVariable int prodId) {
		service.deleteProduct(prodId);
	}
}

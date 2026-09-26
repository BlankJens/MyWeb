package com.webservlet.MyWeb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.webservlet.MyWeb.model.Product;
import com.webservlet.MyWeb.service.CloudinaryService;
import com.webservlet.MyWeb.service.ProductService;

@RestController
public class ProductControl {

	@Autowired
	ProductService service;

	@Autowired
	private CloudinaryService cloudinaryService;
	
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
	
	@PostMapping(value = "/api/products", consumes = {"multipart/form-data"})
	public ResponseEntity<String> addProduct(
			@ModelAttribute Product prod, 
			@RequestParam(value = "image", required = false) MultipartFile imageFile) {
		
		try {
			System.out.println("Tentando cadastrar o produto: " + prod.getProdName());
			
			if (imageFile != null && !imageFile.isEmpty()) {
				System.out.println("Arquivo de imagem recebido. Iniciando upload para o Cloudinary...");
				String url = cloudinaryService.uploadImage(imageFile);
				prod.setImageUrl(url); 
			} else {
				System.out.println("Nenhuma imagem enviada. Aplicando imagem substituta padrão.");
				prod.setImageUrl("https://placeholders.dev");
			}
			
			service.addProduct(prod);
			return new ResponseEntity<>("Produto criado com sucesso!", HttpStatus.CREATED);
		} catch (Exception e) {
			System.err.println("CRÍTICO: Falha no processo de cadastro do produto!");
			e.printStackTrace(); 
			return new ResponseEntity<>("Erro interno: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
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

package com.sparta.springcore.controller;

// TODO: Optimize Import

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.springcore.dto.ProductMypriceRequestDto;
import com.sparta.springcore.dto.ProductRequestDto;
import com.sparta.springcore.model.Product;
import com.sparta.springcore.security.UserDetailsImpl;
import com.sparta.springcore.service.ProductService;

@RestController // JSON으로 데이터를 주고받음을 선언합니다.
public class ProductController {
	// 멤버 변수 선언
	private final ProductService productService;

	// 생성자: ProductController() 가 생성될 때 호출됨
	@Autowired
	public ProductController(ProductService productService) {
		// 멤버 변수 생성
		this.productService = productService;
	}

	// // 등록된 전체 상품 목록 조회
	// @GetMapping("/api/products")
	// public List<Product> getProducts() {
	// 	List<Product> products = productService.getProducts();
	// 	// 응답 보내기
	// 	return products;
	// }

	// 로그인한 회원이 등록한 상품들 조회
	@GetMapping("/api/products")
	public List<Product> getProducts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Long userId = userDetails.getUser().getId();
		return productService.getProducts(userId);
	}

	// (관리자용) 등록된 모든 상품 목록 조회
	@GetMapping("/api/admin/products")
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	// 신규 상품 등록
	@PostMapping("/api/products")
	public Product createProduct(@RequestBody ProductRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) throws
		SQLException {
		// 로그인 되어 있는 ID
		Long userId = userDetails.getUser().getId();

		Product product = productService.createProduct(requestDto, userId);
		// 응답 보내기
		return product;
	}

	// 설정 가격 변경
	@PutMapping("/api/products/{id}")
	public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto) {
		Product product = productService.updateProduct(id, requestDto);
		return product.getId();
	}
}
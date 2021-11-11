package com.sparta.springcore.service;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sparta.springcore.dto.ProductMypriceRequestDto;
import com.sparta.springcore.dto.ProductRequestDto;
import com.sparta.springcore.model.Product;
import com.sparta.springcore.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {
	// 멤버 변수 선언
	private final ProductRepository productRepository;
	private static final int MIN_PRICE = 100;

	public Page<Product> getProducts(Long userId, int page, int size, String sortBy, boolean isAsc) {
		Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
		Sort sort = Sort.by(direction, sortBy);
		Pageable pageable = PageRequest.of(page, size, sort);

		return productRepository.findAllByUserId(userId, pageable);
	}

	@Transactional // JPA 는 트랜잭션 안에서 데이터 변경해야함.
	public Product createProduct(ProductRequestDto requestDto, Long userId) {
		// 요청받은 DTO 로 DB에 저장할 객체 만들기
		Product product = new Product(requestDto, userId);
		productRepository.save(product);
		return product;
	}

	@Transactional // JPA 는 트랜잭션 안에서 데이터 변경해야함.
	public Product updateProduct(Long id, ProductMypriceRequestDto requestDto) {
		Product product = productRepository.findById(id).orElseThrow(
			() -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
		);

		// 변경될 관심 가격이 유효한지 확인합니다.
		int myPrice = requestDto.getMyprice();
		if (myPrice < MIN_PRICE) {
			throw new IllegalArgumentException("유효하지 않은 관심 가격입니다. 최소 " + MIN_PRICE + " 원 이상으로 설정해 주세요.");
		}

		product.updateMyPrice(myPrice);
		return product;
	}

	// 모든 상품 조회 (관리자용)
	public Page<Product> getAllProducts(int page, int size, String sortBy, boolean isAsc) {
		Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
		Sort sort = Sort.by(direction, sortBy);
		Pageable pageable = PageRequest.of(page, size, sort);

		return productRepository.findAll(pageable);
	}
}
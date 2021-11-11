package com.sparta.springcore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.springcore.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Page<Product> findAllByUserId(Long userId, Pageable pageable);
}
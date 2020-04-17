package com.pccth.edlicense.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pccth.edlicense.model.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
	
	ProductType findOneById(Long id);
	Optional<ProductType> findByName(String name);
}

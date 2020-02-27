package com.pccth.edlicense.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pccth.edlicense.model.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
	
	ProductType findOneById(Long id);
}

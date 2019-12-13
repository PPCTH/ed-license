package com.pccth.edlicense.repository;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccth.edlicense.model.Bussiness;

public interface BussinessRepository extends JpaRepository<Bussiness, Long> {

	Page<Bussiness> findByOwnerId(Long ownerId, Pageable pageable );
	Optional<Bussiness> findByIdAndOwnerId(Long id, Long ownerId);
}

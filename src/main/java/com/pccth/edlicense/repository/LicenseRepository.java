package com.pccth.edlicense.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccth.edlicense.model.Bussiness;
import com.pccth.edlicense.model.License;

public interface LicenseRepository extends JpaRepository<License, Long> {

	Optional<License> findByBussinessIdAndProductTypeName(Long busId, String procName);
	Page<License> findByLicenseId(String name, Pageable pageable);
	
}

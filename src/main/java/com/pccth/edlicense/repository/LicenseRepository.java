package com.pccth.edlicense.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pccth.edlicense.model.Bussiness;
import com.pccth.edlicense.model.License;

public interface LicenseRepository extends JpaRepository<License, Long> {

	Page<Bussiness> findByLicenseId(String name, Pageable pageable);
}

package com.pccth.edlicense.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pccth.edlicense.model.Bussiness;
import com.pccth.edlicense.model.License;
import com.pccth.edlicense.model.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

	
}

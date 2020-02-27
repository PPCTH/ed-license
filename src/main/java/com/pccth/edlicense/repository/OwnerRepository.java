package com.pccth.edlicense.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pccth.edlicense.model.Bussiness;
import com.pccth.edlicense.model.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

	@Query(value="select * from Owner o where o.license_id like %:keyword%",
			nativeQuery=true)
	Page<Owner> findOwnerByKeyWordLicenseId(@Param("keyword") String keyword, Pageable pageable);
	
	@Query(value="select * from Owner o where o.name like %:keyword%",
			nativeQuery=true)
	Page<Owner> findOwnerByKeyWordName(@Param("keyword") String keyword, Pageable pageable);
	
	Owner findByLicenseId(String id);
}

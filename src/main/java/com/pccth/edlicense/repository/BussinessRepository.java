package com.pccth.edlicense.repository;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pccth.edlicense.model.Bussiness;
import com.pccth.edlicense.model.Owner;

@Repository
public interface BussinessRepository extends JpaRepository<Bussiness, Long> {

	Page<Bussiness> findByOwnerId(Long ownerId, Pageable pageable );
	Optional<Bussiness> findByIdAndOwnerId(Long id, Long ownerId);
	
	@Query(value="select * from Bussiness b where b.name like %:keyword% or b.bussiness_license_id like %:keyword%",
			nativeQuery=true)
	Page<Bussiness> findBussinessByKeyWord(@Param("keyword") String keyword, Pageable pageable);
	
}

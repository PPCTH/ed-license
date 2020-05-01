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
	
	Optional<Bussiness> findByOwnerId(Long ownerId);
	Optional<Bussiness> findByNameAndOwnerId(String name, Long ownerId);
	Page<Bussiness> findByOwnerLicenseId(String ownerId, Pageable pageable);
	
	Page<Bussiness> findByOwnerNameContaining(String name, Pageable pageable);
	Page<Bussiness> findByAddressProvinceAndAddressDistrictAndAddressSubDistrict(String p, String a, String d, Pageable pageable);
	
	Optional<Bussiness> findBussinessByLicenseLicenseId(String id);
	
	
	
	@Query(value="select * from Bussiness b where b.name like %:keyword% or b.bussiness_license_id like %:keyword%",
			nativeQuery=true)
	Page<Bussiness> findBussinessByKeyWord(@Param("keyword") String keyword, Pageable pageable);
	
	@Query(value="select * from Bussiness b where b.name like %:keyword% order by owner_id, id",
			nativeQuery=true)
	Page<Bussiness> findBussinessByKeyWordSort(@Param("keyword") String keyword, Pageable pageable);
	
}

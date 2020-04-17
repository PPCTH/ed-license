package com.pccth.edlicense.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pccth.edlicense.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {


	Optional<Address> findBussinessByAddressAndSubDistrictAndDistrictAndProvinceAndPostCode(
			String address, String subDistrict, String district, String province, String postCode);
}

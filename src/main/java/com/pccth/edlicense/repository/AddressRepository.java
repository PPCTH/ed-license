package com.pccth.edlicense.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pccth.edlicense.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}

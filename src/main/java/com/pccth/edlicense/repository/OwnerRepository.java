package com.pccth.edlicense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pccth.edlicense.model.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

}

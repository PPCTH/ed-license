package com.pccth.edlicense.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;


public interface BussinessService {
	
	List<Map> findOwnerCardBussinessByKeyWordSort(@Param("keyword") String keyword, Pageable pageable);
	
}

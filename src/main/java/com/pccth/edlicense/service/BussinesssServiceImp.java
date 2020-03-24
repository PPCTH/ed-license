package com.pccth.edlicense.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pccth.edlicense.model.Bussiness;
import com.pccth.edlicense.repository.BussinessRepository;


@Service
public class BussinesssServiceImp implements BussinessService {

	@Autowired
	BussinessRepository bussRepo;
	
	@Override
	public List<Map> findOwnerCardBussinessByKeyWordSort(String keyword, Pageable pageable) {
		Page<Bussiness> pageBussiness = bussRepo.findBussinessByKeyWordSort(keyword, pageable);
		HashMap<String, List<Map>> owner = new HashMap<String, List<Map>>();
		List<Map> tempList = pageBussiness.stream().map(temp -> {
			return new HashMap() {{
				put("owner_id", temp.getOwner().getId());
				put("owner_name", temp.getOwner().getName());
				put("bussiness_id", temp.getId());
				put("bussiness_name", temp.getName());
//				put("bussiness_status", temp.getStatus());
			}};
			
		}).collect(Collectors.toList());
		  
		for(Map temp: tempList) {
			if(!owner.containsKey(temp.get("owner_name"))) {
				
			}
		}
		
		
		return null;
	}
	

}

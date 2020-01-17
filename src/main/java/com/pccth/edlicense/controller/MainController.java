package com.pccth.edlicense.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pccth.edlicense.data.SearchId;
import com.pccth.edlicense.model.Bussiness;
import com.pccth.edlicense.model.Owner;
import com.pccth.edlicense.repository.BussinessRepository;
import com.pccth.edlicense.repository.OwnerRepository;


@Controller
public class MainController {

	@Autowired
	OwnerRepository ownerRepo;
	@Autowired
	BussinessRepository bussRepo;
	
	@GetMapping("")
	public String index(final Model model) {
		
		
		model.addAttribute("title", "Welcome to ED License Trace");
		model.addAttribute("MAPAPIKEY","GuVsoKRAt7o)xA1LSWniH)DlvEGcxykYY1r8jEYYFzXhEOaKXnEkIWVJYbkvnB1PXIwvsn3qO0JvTnbu0LSYoO0=====2");
		return "index/index";
	}
	
	@PostMapping("/search/{searchParam}")
	@ResponseBody
	public ResponseEntity<?> searchBussiness(@PathVariable String searchParam, @RequestParam(name = "page", defaultValue  = "0") Integer pageParam) {
		
		Gson gson = new Gson();
		Map res = new HashMap<>();
		PageRequest page = PageRequest.of(pageParam, Integer.MAX_VALUE); //Integer.MAX_VALUE
		
		Page<Bussiness> pageBussiness = bussRepo.findBussinessByKeyWord(searchParam, page);
		
		List<List> bussinessList = pageBussiness.stream().map(temp ->{
			return new ArrayList<>(
					Arrays.asList(
							temp.getOwner().getName(), 
							temp.getName(),
							temp.getStatus(),
							temp.getId()
							));
		}).collect(Collectors.toList());
		
		res.put("recordsTotal", pageBussiness.getTotalElements());
		res.put("data", bussinessList);
		return ResponseEntity.status(HttpStatus.OK)
		        .body(gson.toJson(res));
	}
	
	@GetMapping("/search/bussiness_detail/{id}")
	@ResponseBody
	public ResponseEntity<?> searchBussinessDetail(@PathVariable Long id){
		Bussiness bussiness = bussRepo.getOne(id);
		Gson gson = new Gson();
		
		
		System.out.println("GET BUSSINESS: "+ bussiness);
		
		return ResponseEntity.status(HttpStatus.OK)
		        .body(gson.toJson(bussiness.getDetail()));
	}
}

package com.pccth.edlicense.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pccth.edlicense.data.SearchId;


@Controller
public class MainController {

	@GetMapping("")
	public String index(final Model model) {
		
		model.addAttribute("title", "Welcome to ED License Trace");
		return "index";
	}
	
	@PostMapping("/search")
	@ResponseBody
	public String search(@RequestBody SearchId searchId) {
		System.out.println("A : " + searchId.getId() );
		return "THIS POST ID: " + searchId.getId();
	}
}

package com.pccth.edlicense.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> search(@RequestBody SearchId searchId) {
		return ResponseEntity.status(HttpStatus.OK)
		        .body("");
	}
}

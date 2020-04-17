package com.pccth.edlicense.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.pccth.edlicense.model.Address;
import com.pccth.edlicense.model.Bussiness;
import com.pccth.edlicense.model.License;
import com.pccth.edlicense.model.Owner;
import com.pccth.edlicense.model.ProductType;
import com.pccth.edlicense.repository.AddressRepository;
import com.pccth.edlicense.repository.BussinessRepository;
import com.pccth.edlicense.repository.LicenseRepository;
import com.pccth.edlicense.repository.OwnerRepository;
import com.pccth.edlicense.repository.ProductTypeRepository;


@Controller
public class MainController {
	
	
	@Autowired 
	BussinessRepository bussRepo;
	@Autowired 
	OwnerRepository ownerRepo;
	@Autowired
	ProductTypeRepository procTypeRepo;
	@Autowired
	AddressRepository addrRepo;
	@Autowired
	LicenseRepository lcRepo;
	
	@GetMapping("")
	public String searchIndex(final Model model) {
		
		model.addAttribute("title", "Welcome to ED License Trace");
		model.addAttribute("MAPAPIKEY","GuVsoKRAt7o)xA1LSWniH)DlvEGcxykYY1r8jEYYFzXhEOaKXnEkIWVJYbkvnB1PXIwvsn3qO0JvTnbu0LSYoO0=====2");
		model.addAttribute("inputPlaceHolder", "ค้นหาโดย ชื่อ หรือ เลขประจำตัวประชาชน ของผู้ประกอบการ");
		return "index/index";
	}
	
	@GetMapping("/import")
	public String importIndex(final Model model) {
		
		model.addAttribute("title", "Welcome to ED License Trace");
		return "import/import";
	}
	
	@PostMapping("/search/{searchParam}")
	@ResponseBody
	public ResponseEntity<?> searchBussiness(@PathVariable String searchParam, @RequestParam(name = "page", defaultValue  = "0") Integer pageParam) {

		PageRequest page = PageRequest.of(pageParam, Integer.MAX_VALUE); //Integer.MAX_VALUE
		
		Page<Bussiness> pageBussiness = bussRepo.findByOwnerNameContaining((String) searchParam, page);
		
		if(pageBussiness.isEmpty())
			pageBussiness = bussRepo.findByOwnerLicenseId(searchParam, page);
		
	
		List<Bussiness> listBussiness = pageBussiness.getContent();
		
		HashMap<String, Map<String, Serializable>> ownerList = new HashMap<String, Map<String, Serializable>>(); 
		
		for(Bussiness temp: listBussiness) {
			List<License>licenseOfList = temp.getLicense();
			HashMap<String, Serializable> owner = new HashMap<String, Serializable>(); 
			List<Map<String, String>> bussList = new ArrayList<Map<String, String>>();
			
			for(License license: licenseOfList) {
				HashMap<String, String> bussiness = new HashMap<String, String>();
				
				owner.put("owner_name",license.getBussiness().getOwner().getName());
				owner.put("owner_id", license.getBussiness().getOwner().getLicenseId());
				
				bussiness.put("bussiness_id", license.getLicenseId());
				bussiness.put("bussiness_name", license.getBussiness().getName());
				bussiness.put("bussiness_start_license", license.getStartLicenseDate().toLocaleString());
				bussiness.put("bussiness_end_license", license.getEndLicenseDate().toLocaleString());
				bussiness.put("bussiness_isAviable", license.isAvaiable().toString());
				bussiness.put("bussiness_status",license.isExpired());
				bussiness.put("bussiness_type",license.getProductType().getName());
				bussiness.put("address", license.getBussiness().getAddress().toString());
				bussList.add(bussiness);
				
				owner.put("bussiness_list", (Serializable) bussList);
				ownerList.put(license.getBussiness().getOwner().getLicenseId(), owner);
			}
			
		}
		return ResponseEntity.status(HttpStatus.OK).body(new TreeMap(ownerList));
	}

	@GetMapping("/search/bussiness_detail/{id}")
	@ResponseBody
	public ResponseEntity<?> searchBussinessDetail(@PathVariable Long id){
		Bussiness bussiness = bussRepo.getOne(id);
		Gson gson = new Gson();
		
		return ResponseEntity.status(HttpStatus.OK)
		        .body(gson.toJson(bussiness.getDetail()));
	}
	
	
  @PostMapping("/import/bussiness")
  @ResponseBody public ResponseEntity<?>
  importBussinessData(@RequestParam("file") MultipartFile uploadFile){
	  
	  List<String> newBussListLog = new ArrayList<String>();
	  if(uploadFile.isEmpty()) 
		  return ResponseEntity.status(HttpStatus.OK).body("Please select a file!");
	  
	  XSSFWorkbook workbook;
	  try { 
		  workbook = new XSSFWorkbook(uploadFile.getInputStream()); 
		  XSSFSheet worksheet = workbook.getSheetAt(0); 
		  for(int i = 2; i < worksheet.getPhysicalNumberOfRows() ;i++) { //Start at row 2
			  XSSFRow row = worksheet.getRow(i); 

			  String ownerLCID = row.getCell(0).getRawValue(); //Owner License ID
			  String ownerName = row.getCell(1).getStringCellValue(); //Owner Name
			  Owner owner = ownerRepo.findByLicenseId(ownerLCID).orElseGet(() -> {
				  Owner temp = new Owner();
				  temp.setLicenseId(ownerLCID);
				  temp.setName(ownerName);
				  ownerRepo.save(temp); //New Owner Added
				  return temp;
			  });
			  
			  String col4 = row.getCell(4).getStringCellValue(); //Address
			  String col5 = row.getCell(5).getStringCellValue(); //Sub-District
			  String col6 = row.getCell(6).getStringCellValue(); //District
			  String col7 = row.getCell(7).getStringCellValue(); //Province
			  String col8 = String.valueOf((int) row.getCell(8).getNumericCellValue()); //Postcode
			  
			  Address address = addrRepo.findBussinessByAddressAndSubDistrictAndDistrictAndProvinceAndPostCode(
					  			col4, col5, col6, col7, col8).orElseGet(() ->{
					  				Address temp = new Address();
					  				temp.setAddress(col4);
					  				temp.setSubDistrict(col5);
					  				temp.setDistrict(col6);
					  				temp.setProvince(col7);
					  				temp.setPostCode(col8);
					  				addrRepo.save(temp);
					  				return temp;
					  			});

			  String busLCID = row.getCell(2).getRawValue(); //Bussiness License ID
			  String busName = row.getCell(3).getStringCellValue(); //Bussiness Name
			  String productTypeName = row.getCell(9).getStringCellValue(); //Product Type
			  Date startLCDate = row.getCell(10).getDateCellValue(); //Start License Date
			  Date endLCDate = row.getCell(11).getDateCellValue(); //End License Date
			  
			  Bussiness buss = bussRepo.findByNameAndOwnerId(busName, owner.getId()).orElseGet(() ->{
				  Bussiness temp = new Bussiness();
				  temp.setName(busName);
				  temp.setAddress(address);
				  temp.setOwner(owner);
				  bussRepo.save(temp); //Save New Bussiness
				  return temp;
			  });
			  			  
			  ProductType type = procTypeRepo.findByName(productTypeName).orElseGet(() -> {
				  ProductType temp = new ProductType();
				  temp.setName(productTypeName);
				  procTypeRepo.save(temp);
				  return temp;
			  });
			  
			  License license = lcRepo.findByBussinessIdAndProductTypeName(buss.getId(), type.getName()).orElseGet(() ->{
				
				  License temp = new License();
				  temp.setLicenseId(busLCID);
				  temp.setProductType(type);
				  temp.setStartLicenseDate(startLCDate);
				  temp.setEndLicenseDate(endLCDate);
				  temp.setBussiness(buss);
				  lcRepo.save(temp); //Save New Bussiness License
				  newBussListLog.add("Save new Bussiness License: \r\n" + temp);
				  return temp;
			  });
			  
			  
		  }
	  
	  } catch (IOException e) {
		  System.out.println(e);
	  }
	  
	  return ResponseEntity.status(HttpStatus.CREATED) .body(newBussListLog); 
  }
	 
	
}

package com.pccth.edlicense.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.pccth.edlicense.model.Address;
import com.pccth.edlicense.model.Bussiness;
import com.pccth.edlicense.model.Owner;
import com.pccth.edlicense.model.ProductType;
import com.pccth.edlicense.repository.AddressRepository;
import com.pccth.edlicense.repository.BussinessRepository;
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
	
	@GetMapping("")
	public String searchIndex(final Model model) {
		
		model.addAttribute("title", "Welcome to ED License Trace");
		model.addAttribute("MAPAPIKEY","GuVsoKRAt7o)xA1LSWniH)DlvEGcxykYY1r8jEYYFzXhEOaKXnEkIWVJYbkvnB1PXIwvsn3qO0JvTnbu0LSYoO0=====2");
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
		
		Page<Bussiness> pageBussiness = bussRepo.findBussinessByKeyWordSort(searchParam, page);
		HashMap<String, List<Map>> owner = new HashMap<String, List<Map>>();
		
		List<Bussiness> listBussiness = pageBussiness.getContent();
		/*
		 * for( Bussiness temp: listBussiness) { String ownerName =
		 * temp.getOwner().getName(); Map<String, String> map = new HashMap<String,
		 * String>() {{ put("bussiness_id",temp.getId().toString());
		 * put("bussiness_name", temp.getName()); put("bussiness_status",
		 * temp.getStatus()); put("bussiness_isAviable",temp.isAvaiable().toString());
		 * }}; if(!owner.containsKey(ownerName)) { List<Map> list = new
		 * ArrayList<Map>(){{ add(map); }}; owner.put(ownerName, list); }else {
		 * owner.get(ownerName).add(map); } }
		 */
			
		return ResponseEntity.status(HttpStatus.OK).body(new TreeMap(owner));
	}
	
	@GetMapping("/search/bussiness_detail/{id}")
	@ResponseBody
	public ResponseEntity<?> searchBussinessDetail(@PathVariable Long id){
		Bussiness bussiness = bussRepo.getOne(id);
		Gson gson = new Gson();
		
		return ResponseEntity.status(HttpStatus.OK)
		        .body(gson.toJson(bussiness.getDetail()));
	}
	
	/*
	 * @PostMapping("/import/bussiness")
	 * 
	 * @ResponseBody public ResponseEntity<?>
	 * importBussinessData(@RequestParam("file") MultipartFile uploadFile){
	 * List<String> newBussListLog = new ArrayList<String>();
	 * 
	 * if(uploadFile.isEmpty()) return ResponseEntity.status(HttpStatus.OK)
	 * .body("Please select a file!");
	 * 
	 * 
	 * //List<Test> tempStudentList = new ArrayList<Test>(); XSSFWorkbook workbook;
	 * try { workbook = new XSSFWorkbook(uploadFile.getInputStream()); XSSFSheet
	 * worksheet = workbook.getSheetAt(0); for(int
	 * i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) { XSSFRow row =
	 * worksheet.getRow(i); String ownerLCID = row.getCell(0).getRawValue(); //Owner
	 * LCID Owner owner = ownerRepo.findByLicenseId(ownerLCID);
	 * System.out.println("A"); Bussiness newBuss = new Bussiness();
	 * newBuss.setOwner(owner);
	 * newBuss.setBussinessLicenseId(row.getCell(1).getRawValue()); //Bussiness LCID
	 * newBuss.setName(row.getCell(2).getStringCellValue()); //Bussiness Name
	 * System.out.println("B");
	 * 
	 * Address newAddr = new Address();
	 * newAddr.setName(row.getCell(3).getStringCellValue()); //Address
	 * newBuss.setAddress(newAddr); System.out.println("C");
	 * 
	 * ProductType type =
	 * procTypeRepo.findOneById(Long.valueOf(row.getCell(4).getRawValue()));
	 * //Product Type newBuss.setProductType(type); System.out.println("D");
	 * 
	 * 
	 * System.out.println("cell 5 " + row.getCell(5).getDateCellValue());
	 * newBuss.setStartLicenseDate(row.getCell(5).getDateCellValue()); //Start
	 * Bussinees LC Date System.out.println("E"); System.out.println("cell 6 " +
	 * row.getCell(6).getDateCellValue());
	 * newBuss.setEndLicenseDate(row.getCell(6).getDateCellValue()); //End Bussiness
	 * LC Date System.out.println("F");
	 * 
	 * //Save All New addrRepo.save(newAddr); bussRepo.save(newBuss);
	 * newBussListLog.add(newBuss.toString()); }
	 * 
	 * } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * 
	 * return ResponseEntity.status(HttpStatus.CREATED) .body(newBussListLog); }
	 */
	
}
 
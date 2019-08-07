package com.shopping.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.app.entity.Invoice;
import com.shopping.app.repository.AccountRepository;
import com.shopping.app.service.InvoiceService;
import com.shopping.app.utils.AuthHelper;
import com.shopping.app.utils.DateConverter;

@RestController
@CrossOrigin
@RequestMapping("/api/invoice")
public class InvoiceController {

	@Autowired
	private InvoiceService service;
	
	@Autowired
	private DateConverter dateConverter;
	
	@Autowired
	private AuthHelper auth;
	
	@Autowired
	private AccountRepository repo;

	@PostMapping("invoices")
	public ResponseEntity<Invoice> create(@RequestBody Invoice invoice) {
		invoice.setAccount(repo.findByEmail(auth.getLoginUserName()));
		service.create(invoice);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(invoice);
	}
	
	@GetMapping("invoices")
	public List<Invoice> findAll(){
		return service.findAll();
	}
	
	@GetMapping("{id}")
	public Invoice findOne(@PathVariable("id") long id) {
		return service.findById(id);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable("id") long id){
		service.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK)
				.body(String.format("Invoice id %d was deleted.", id));
	}
	
	@GetMapping("search")
	public List<Invoice> search(@RequestParam("username") String username, @RequestParam("startDate") String from,
			@RequestParam("endDate") String to){
		String name = null;
		if(auth.isUserInRole("ROLE_ADMIN") || (null !=username && !username.isEmpty()))
			name = username;
		else 
			name = repo.findByEmail(auth.getLoginUserName()).getName();
		
		return service.search(name, dateConverter.stringToLocalDate(from), dateConverter.stringToLocalDate(to));
	}
}

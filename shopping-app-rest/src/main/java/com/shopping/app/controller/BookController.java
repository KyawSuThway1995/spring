package com.shopping.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.app.entity.Book;
import com.shopping.app.service.BookService;

@RestController
@CrossOrigin
@RequestMapping("/api/book")
public class BookController {

	@Autowired
	private BookService service;

	@PostMapping("books")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Book> create(@RequestBody Book book) {
		service.create(book);
		return ResponseEntity.status(HttpStatus.CREATED).body(book);
	}

	@GetMapping("books")
	public List<Book> findAll() {
		return service.findAll();
	}

	@GetMapping("{id}")
	public Book findOne(@PathVariable("id") long id) {
		return service.findById(id);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable("id") long id){
		service.deleteById(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(String.format("Book id %d was deleted.", id));
	}
}
